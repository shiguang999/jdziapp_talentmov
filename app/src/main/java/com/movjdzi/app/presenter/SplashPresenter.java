package com.movjdzi.app.presenter;

import android.widget.Toast;

import com.google.gson.Gson;
import com.media.playerlib.widget.GlobalDATA;
import com.movjdzi.app.App;
import com.movjdzi.app.http.ApiService;
import com.movjdzi.app.http.BaseApi;
import com.media.playerlib.model.AdConfigDto;
import com.movjdzi.app.model.dto.TypeListDto;
import com.movjdzi.app.model.vo.VideoTypeVo;
import com.movjdzi.app.presenter.iview.ITypeView;

/**
 * @author huangyong
 * createTime 2019-09-14
 */
public class SplashPresenter {
    private ITypeView iTypeView;

    public SplashPresenter(ITypeView iTypeView) {
        this.iTypeView = iTypeView;
    }

    public void getTypeList() {
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getTypeList(), new BaseApi.IResponseListener<TypeListDto>() {
                    @Override
                    public void onSuccess(TypeListDto data) {
                        if (data != null && data.getData() != null && data.getData().size() > 0) {
                            iTypeView.loadDone(VideoTypeVo.from(data));
                        } else {
                            Toast.makeText(App.getContext(), "数据为空", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFail() {
                        iTypeView.loadError();
                    }
                }
        );



    }

    public void getad() {
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getAd(), new BaseApi.IResponseListener<AdConfigDto>() {
                    @Override
                    public void onSuccess(AdConfigDto data) {
                        if (data != null) {
                            String dataJson = new Gson().toJson(data.getData());
                            GlobalDATA.AD_INFO = dataJson;
                            iTypeView.loadAdDone();
                        } else {
                            GlobalDATA.AD_INFO = "";
                        }

                    }

                    @Override
                    public void onFail() {
                    }
                }
        );
    }
}
