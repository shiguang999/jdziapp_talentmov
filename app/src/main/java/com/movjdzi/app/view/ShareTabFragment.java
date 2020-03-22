package com.movjdzi.app.view;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lxj.xpopup.XPopup;
import com.movjdzi.app.R;
import com.movjdzi.app.adapter.vip.VipHeaderSection;
import com.movjdzi.app.adapter.vip.VipHeaderSectionViewBinder;
import com.movjdzi.app.adapter.vip.VipShareItemSectionn;
import com.movjdzi.app.adapter.vip.VipShareItemSectionnViewBinder;
import com.movjdzi.app.presenter.CoinPresenter;
import com.movjdzi.app.presenter.iview.ICoin;
import com.movjdzi.app.util.ToastUtil;
import com.movjdzi.app.util.UserUtil;
import com.movjdzi.app.view.dialog.BottomShareView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import kale.sharelogin.content.ShareContent;
import kale.sharelogin.content.ShareContentPic;
import kale.sharelogin.content.ShareContentText;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * @author huangyong
 * createTime 2019-09-11
 */
public class ShareTabFragment extends Fragment {


    @BindView(R.id.home_rv)
    RecyclerView homeRv;
    Unbinder unbinder;


    private MultiTypeAdapter multiTypeAdapter;

    private void refreshCoin() {
        String userToken = UserUtil.getUserToken(getContext());
        if (!TextUtils.isEmpty(userToken)) {
            //TODO 本地获取不请求

            new CoinPresenter(iCoin).getCoin(userToken);
        }else {
            vipHeaderSection.setCoinNum("登录后可查看");
            multiTypeAdapter.notifyDataSetChanged();
        }

    }

    private VipHeaderSection vipHeaderSection;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_share_fragment_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }



    private void initView() {
        multiTypeAdapter = new MultiTypeAdapter();
        multiTypeAdapter.register(VipHeaderSection.class, new VipHeaderSectionViewBinder());
        multiTypeAdapter.register(VipShareItemSectionn.class, new VipShareItemSectionnViewBinder());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        homeRv.setLayoutManager(layoutManager);


        ArrayList<Object> items = new ArrayList<>();


        vipHeaderSection = new VipHeaderSection(listener, "23");

        VipShareItemSectionn sectionn = new VipShareItemSectionn("分享下载二维码", R.drawable.share_code_drawable, "每日分享下载二维码","+50金币", position -> {
            final Bitmap thumbBmp = ((BitmapDrawable) getResources().getDrawable(R.drawable.share)).getBitmap();
            ShareContent mShareContent = new ShareContentPic(thumbBmp);
            showShareView(mShareContent);
        });
        VipShareItemSectionn sectionn2 = new VipShareItemSectionn("分享下载链接", R.drawable.share_link_drawable, "每日分享下载链接","+50金币", position -> {
            ShareContent mShareContent = new ShareContentText("http://ys.jdzi.vip/app/download/jdzi.apk");
            showShareView(mShareContent);
        });
        VipShareItemSectionn sectionn3 = new VipShareItemSectionn("分享影片到微信", R.drawable.share_weichat_drawable, "每日分享影片到微信或朋友圈","+50金币", position -> {
            if (switchListener != null) {
                switchListener.switchToHome();
            }
        });
        VipShareItemSectionn sectionn4 = new VipShareItemSectionn("分享影片到qq", R.drawable.share_qq_drawable, "每日分享影片到qq或qq空间","+50金币", new VipShareItemSectionnViewBinder.VipShareClickListener() {
            @Override
            public void clickShare(int position) {
                if (switchListener != null) {
                    switchListener.switchToHome();
                }
            }
        });
        VipShareItemSectionn sectionn5 = new VipShareItemSectionn("分享影片到微博", R.drawable.share_weibo_drawable, "每日分享影片到新浪微博","+50金币", new VipShareItemSectionnViewBinder.VipShareClickListener() {
            @Override
            public void clickShare(int position) {
                if (switchListener != null) {
                    switchListener.switchToHome();
                }
            }
        });
        items.add(vipHeaderSection);
        items.add(sectionn);    //二维码
        items.add(sectionn2);   //链接
        /*items.add(sectionn3);   //微信
        items.add(sectionn4);   //qq
        items.add(sectionn5); //微博*/
        multiTypeAdapter.setItems(items);
        homeRv.setAdapter(multiTypeAdapter);


        refreshCoin();
    }

    VipHeaderSectionViewBinder.OnVipClickListener listener = new VipHeaderSectionViewBinder.OnVipClickListener() {
        @Override
        public void toCenter() {

            if (UserUtil.isLogin()){
                CoinShopActivity.start(getContext());
            }else {
                ToastUtil.showMessage("请先登录");
                LoginActivity.start(getContext());
            }
        }

        @Override
        public void toShop() {

            if (UserUtil.isLogin()){
                CoinShopActivity.start(getContext());
            }else {
                ToastUtil.showMessage("请先登录");
                LoginActivity.start(getContext());
            }

        }

        @Override
        public void initDataListener(VipHeaderSectionViewBinder.OnDataChangeListener onDataChangeListener) {
            dataChangeListener = onDataChangeListener;
        }
    };
    private VipHeaderSectionViewBinder.OnDataChangeListener dataChangeListener;


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private OnSwitchListener switchListener;

    public void setOnSwitchListener(OnSwitchListener onSwitchListener) {
        this.switchListener = onSwitchListener;
    }


    public void showShareView(ShareContent content) {
        if (getActivity() == null) {
            return;
        }
        new XPopup.Builder(getContext()).asCustom(new BottomShareView(getActivity(), content)).show();
    }

    ICoin iCoin = new ICoin() {
        @Override
        public void updateCoin(String coin) {
            if (vipHeaderSection != null && multiTypeAdapter != null) {
                if (dataChangeListener!=null){
                    dataChangeListener.onChange(coin);
                }
            }
        }

        @Override
        public void loadError() {

        }

        @Override
        public void loadEmpty() {

        }
    };

    public void refreshData() {
        refreshCoin();
    }

}
