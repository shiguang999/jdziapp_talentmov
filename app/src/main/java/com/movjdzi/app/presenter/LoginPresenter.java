package com.movjdzi.app.presenter;

import com.movjdzi.app.http.ApiService;
import com.movjdzi.app.http.BaseApi;
import com.movjdzi.app.model.dto.LoginDto;
import com.movjdzi.app.presenter.iview.IUserView;
import com.movjdzi.app.util.Md5Utils;

/**
 * @author huangyong
 * createTime 2019-09-17
 */
public class LoginPresenter {
    private IUserView iLoginView;

    public LoginPresenter(IUserView iLoginView) {
        this.iLoginView = iLoginView;
    }


    /**
     * 登录
     * @param name
     * @param pass
     */
    public void login(String name,String pass){

        String md5Pass = Md5Utils.stringToMD5(pass);
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .login(name,md5Pass), new BaseApi.IResponseListener<LoginDto>() {
                    @Override
                    public void onSuccess(LoginDto data) {
                        iLoginView.loadDone(data);
                    }

                    @Override
                    public void onFail() {
                        iLoginView.loadError();
                    }
                }
        );
    }
}
