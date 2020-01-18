package com.movjdzi.app.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.movjdzi.app.http.ApiService;
import com.movjdzi.app.http.BaseApi;
import com.movjdzi.app.model.dto.IconDto;
import com.movjdzi.app.util.ToastUtil;
import com.movjdzi.app.util.UserUtil;

/**
 * @author huangyong
 * createTime 2019-10-10
 */
public class IconViewModel extends ViewModel {

    private MutableLiveData<IconDto> loginResult = new MutableLiveData<>();


    public LiveData<IconDto> getIconResult() {
        return loginResult;
    }

    public void changeIcon(int index) {
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .changeIcon(UserUtil.getUserId(), String.valueOf(index)), new BaseApi.IResponseListener<IconDto>() {
                    @Override
                    public void onSuccess(IconDto data) {
                        if (loginResult != null) {
                            loginResult.postValue(data);
                        }
                    }

                    @Override
                    public void onFail() {
                        ToastUtil.showMessage("更新头像失败");
                    }
                }
        );
    }
}
