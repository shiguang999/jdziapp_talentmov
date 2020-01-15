package com.media.playerlib.cover;

import android.content.Context;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import com.kk.taurus.playerbase.player.OnTimerUpdateListener;
import com.kk.taurus.playerbase.receiver.BaseCover;
import com.kk.taurus.playerbase.touch.OnTouchGestureListener;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.ConfirmPopupView;
import com.lxj.xpopup.interfaces.OnCancelListener;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.media.playerlib.PlayApp;
import com.media.playerlib.R;
import com.media.playerlib.model.DataInter;

/**
 * @author huangyong
 * createTime 2019-09-29
 */
public class AuthCover extends BaseCover implements OnTimerUpdateListener, OnTouchGestureListener {


    private int authCode;
    private ConfirmPopupView authoTip;
    private TextView buy;

    public AuthCover(Context context) {
        super(context);

    }

    @Override
    protected void onCoverAttachedToWindow() {
        super.onCoverAttachedToWindow();
        authCode = getGroupValue().getInt(DataInter.Key.AUTH_CODE);
        authoTip = new XPopup.Builder(getContext()).asConfirm("提示！", "会员可观看全集，现在去兑换会员？", new OnConfirmListener() {
            @Override
            public void onConfirm() {
                notifyReceiverEvent(DataInter.Event.EVENT_CODE_TO_GET_VIP, null);
            }
        }, new OnCancelListener() {
            @Override
            public void onCancel() {
                requestReset(null);
                notifyReceiverEvent(DataInter.Event.EVENT_CODE_TO_EXIT, null);
            }
        });
    }

    @Override
    protected View onCreateCoverView(Context context) {
        return View.inflate(context, R.layout.layout_auth_cover, null);
    }

    @Override
    public void onReceiverBind() {
        super.onReceiverBind();
        buy = getView().findViewById(R.id.tv_buy);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyReceiverEvent(DataInter.Event.EVENT_CODE_TO_GET_VIP, null);
            }
        });
    }

    @Override
    public void onPlayerEvent(int eventCode, Bundle bundle) {

    }

    @Override
    public void onErrorEvent(int eventCode, Bundle bundle) {

    }

    @Override
    public void onReceiverEvent(int eventCode, Bundle bundle) {
        if (eventCode == DataInter.Event.RESTART_PLAY) {
            authCode = getGroupValue().getInt(DataInter.Key.AUTH_CODE);
            setCoverVisibility(View.GONE);
            requestResume(null);
        }
    }

    @Override
    public int getCoverLevel() {
        return levelMedium(13);
    }


    @Override
    public void onTimerUpdate(int curr, int duration, int bufferPercentage) {


        if (curr >= 10 * 60 * 1000) {
            if (authCode < PlayApp.AUTH_ALL) {
                //试看，时间大于10分钟就停止播放弹对话框,curr毫秒值
                requestPause(null);
                setCoverVisibility(View.VISIBLE);
                Log.e("getcurrenttime",curr+"--"+authCode);
            } else {
                //完整观看，不处理
                setCoverVisibility(View.GONE);
            }

        } else {
            setCoverVisibility(View.GONE);
        }


    }

    @Override
    public void onSingleTapUp(MotionEvent event) {

    }

    @Override
    public void onDoubleTap(MotionEvent event) {

    }

    @Override
    public void onDown(MotionEvent event) {

    }

    @Override
    public void onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

    }

    @Override
    public void onEndGesture() {

    }
}
