package com.media.playerlib.cover;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.kk.taurus.playerbase.event.OnPlayerEventListener;
import com.kk.taurus.playerbase.player.IPlayer;
import com.kk.taurus.playerbase.player.OnTimerUpdateListener;
import com.kk.taurus.playerbase.receiver.BaseCover;
import com.kk.taurus.playerbase.receiver.PlayerStateGetter;
import com.media.playerlib.R;
import com.media.playerlib.manager.AdManager;
import com.media.playerlib.manager.RxCountDown;
import com.media.playerlib.model.AdConfigDto;
import com.media.playerlib.model.DataInter;
import com.media.playerlib.widget.GlobalDATA;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.Subject;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.view.View.inflate;


/**
 * Created by Taurus on 2018/4/15.
 */

public class AdCover extends BaseCover implements OnTimerUpdateListener {

    private TextView timeCut;
    private FrameLayout adContent;
    private ViewGroup viewRoot;
    private String adLinkUrl;
    private String adImgUrl;

    public AdCover(Context context) {
        super(context);
    }

    @Override
    protected void onCoverAttachedToWindow() {
        super.onCoverAttachedToWindow();
        showTimeCaculate();



    }

    private void loadAD() {
        //requestPause(null);
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(getContext()).load(adImgUrl).into(imageView);
        imageView.setLayoutParams(adContent.getLayoutParams());
        adContent.addView(imageView);
    }

    @SuppressLint("CheckResult")
    private void showTimeCaculate() {
        RxCountDown.countdown(5)
                .doOnSubscribe(disposable -> {
                    timeCut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //跳过播放广告
                            disposable.dispose();
                            setCoverVisibility(GONE);
                            notifyReceiverEvent(DataInter.Event.EVENT_CODE_REQUEST_START, null);
                            requestResume(null);
                        }
                    });
                })
                .subscribe(integer -> timeCut.setText("跳过 " + integer + "秒"), throwable -> {

                }, () -> {
                    setCoverVisibility(GONE);
                    notifyReceiverEvent(DataInter.Event.EVENT_CODE_REQUEST_START, null);
                    requestResume(null);
                });
    }

    @Override
    protected void onCoverDetachedToWindow() {
        super.onCoverDetachedToWindow();
    }


    @Override
    public void onPlayerEvent(int eventCode, Bundle bundle) {
        switch (eventCode) {
            case OnPlayerEventListener.PLAYER_EVENT_ON_BUFFERING_START:
            case OnPlayerEventListener.PLAYER_EVENT_ON_DATA_SOURCE_SET:
            case OnPlayerEventListener.PLAYER_EVENT_ON_PROVIDER_DATA_START:
            case OnPlayerEventListener.PLAYER_EVENT_ON_SEEK_TO:
                break;

            case OnPlayerEventListener.PLAYER_EVENT_ON_VIDEO_RENDER_START:
            case OnPlayerEventListener.PLAYER_EVENT_ON_BUFFERING_END:
            case OnPlayerEventListener.PLAYER_EVENT_ON_STOP:
            case OnPlayerEventListener.PLAYER_EVENT_ON_PROVIDER_DATA_ERROR:
            case OnPlayerEventListener.PLAYER_EVENT_ON_SEEK_COMPLETE:
                break;
        }
    }

    @Override
    public void onErrorEvent(int eventCode, Bundle bundle) {
        setLoadingState(false);
    }

    /**
     * 加载 播放器广告
     * @param eventCode
     * @param bundle
     */
    @Override
    public void onReceiverEvent(int eventCode, Bundle bundle) {
        if (eventCode == DataInter.Event.KEY_SHOW_AD) {

            if (TextUtils.isEmpty(GlobalDATA.AD_INFO)){
                return;
            }
            AdConfigDto.DataBean dataBean = new Gson().fromJson(GlobalDATA.AD_INFO,AdConfigDto.DataBean.class);
            if (dataBean==null||dataBean.getAd_player()==null){
                return;
            }
            adImgUrl = dataBean.getAd_player().getImg();
            adLinkUrl =dataBean.getAd_player().getLink();
            if (TextUtils.isEmpty(adImgUrl)){
                return;
            }
            if (!TextUtils.isEmpty(adLinkUrl)){
                adContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse(adLinkUrl);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getContext().startActivity(intent);
                    }
                });
            }

            loadAD();
            setCoverVisibility(VISIBLE);
            showTimeCaculate();
            requestPause(null);
        }
    }

    private void setLoadingState(final boolean show) {

    }

    @Override
    public View onCreateCoverView(Context context) {
        return View.inflate(context, R.layout.layout_ad_cover, null);
    }

    @Override
    public int getCoverLevel() {
        return levelHigh(20);
    }

    @Override
    public void onReceiverBind() {
        super.onReceiverBind();

        View view = getView();
        adContent = view.findViewById(R.id.web_ad);
        timeCut = view.findViewById(R.id.time_cut);
        viewRoot = view.findViewById(R.id.ad_conntennt);
    }

    @Override
    public void onTimerUpdate(int curr, int duration, int bufferPercentage) {
//
    }

}
