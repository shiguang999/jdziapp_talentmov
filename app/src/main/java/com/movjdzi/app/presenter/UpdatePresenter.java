package com.movjdzi.app.presenter;

import com.azhon.appupdate.utils.ApkUtil;
import com.movjdzi.app.App;
import com.movjdzi.app.http.ApiService;
import com.movjdzi.app.http.BaseApi;
import com.movjdzi.app.model.dto.UpdateDto;
import com.movjdzi.app.presenter.iview.IUpdate;
import com.movjdzi.app.util.ToastUtil;

/**
 * @author huangyong
 * createTime 2019-10-09
 */
public class UpdatePresenter {

    private IUpdate iUpdate;

    public UpdatePresenter(IUpdate iUpdate) {
        this.iUpdate = iUpdate;
    }

    public void getUpdate(){
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getUpdate(), new BaseApi.IResponseListener<UpdateDto>() {
                    @Override
                    public void onSuccess(UpdateDto data) {

                        //对版本进行判断，是否显示升级对话框
                        if (data.getData().getVersionCode() > ApkUtil.getVersionCode(App.getContext())) {
                            if (iUpdate!=null){
                                iUpdate.loadDone(data);
                            }
                        } else {
                            ToastUtil.showMessage("当前已是最新版本");
                        }



                    }

                    @Override
                    public void onFail() {
                    }
                }
        );
    }
}
