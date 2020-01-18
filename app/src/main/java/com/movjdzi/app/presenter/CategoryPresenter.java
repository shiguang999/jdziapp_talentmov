package com.movjdzi.app.presenter;

import com.movjdzi.app.http.ApiService;
import com.movjdzi.app.http.BaseApi;
import com.movjdzi.app.model.dto.VideoListDto;
import com.movjdzi.app.model.vo.CommonVideoVo;
import com.movjdzi.app.presenter.iview.IHomeView;

/**
 * @author huangyong
 * createTime 2019-09-14
 * 分类筛选页面
 * query
 */
public class CategoryPresenter {

    private IHomeView iHomeView;

    public CategoryPresenter(IHomeView iHomeView) {
        this.iHomeView = iHomeView;
    }

    public void getCategory(int typeId,String area,int year,int page,int limit) {
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getCategory(typeId,area,year,page,limit), new BaseApi.IResponseListener<VideoListDto>() {
                    @Override
                    public void onSuccess(VideoListDto data) {
                        iHomeView.loadDone(CommonVideoVo.from(data));
                    }

                    @Override
                    public void onFail() {
                        iHomeView.loadError();
                    }
                }
        );
    }

    public void getCategoryMore(int typeId,String area,int year,int page,int limit) {
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getCategory(typeId,area,year,page,limit), new BaseApi.IResponseListener<VideoListDto>() {
                    @Override
                    public void onSuccess(VideoListDto data) {
                        iHomeView.loadMore(CommonVideoVo.from(data));
                    }

                    @Override
                    public void onFail() {
                        iHomeView.loadError();
                    }
                }
        );
    }
}
