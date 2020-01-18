package com.movjdzi.app.presenter;

import com.movjdzi.app.http.ApiService;
import com.movjdzi.app.http.BaseApi;

/**
 * @author huangyong
 * createTime 2019-09-22
 */
public class DiggPresenter {

    public void diggComment(int commentId){
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .diggComment(commentId), new BaseApi.IResponseListener<Object>() {
                    @Override
                    public void onSuccess(Object data) {
                    }

                    @Override
                    public void onFail() {
                    }
                }
        );
    }

    public void cancelDigg(int commentId) {
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .cancelDiggComment(commentId), new BaseApi.IResponseListener<Object>() {
                    @Override
                    public void onSuccess(Object data) {
                    }

                    @Override
                    public void onFail() {
                    }
                }
        );
    }
}
