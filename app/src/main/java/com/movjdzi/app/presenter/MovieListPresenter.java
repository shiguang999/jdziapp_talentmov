package com.movjdzi.app.presenter;

import com.movjdzi.app.http.ApiService;
import com.movjdzi.app.http.BaseApi;
import com.movjdzi.app.model.dto.VideoListDto;
import com.movjdzi.app.model.vo.CommonVideoVo;
import com.movjdzi.app.presenter.iview.IListView;

/**
 * @author huangyong
 * createTime 2019-09-14
 */
public class MovieListPresenter {

    private IListView iListView;

    public MovieListPresenter(IListView iListView) {
        this.iListView = iListView;
    }


    public void loadListByType(int typeId, int index, int limit) {
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getTypeMovList(typeId, index, limit), new BaseApi.IResponseListener<VideoListDto>() {
                    @Override
                    public void onSuccess(VideoListDto data) {
                        iListView.loadDone(CommonVideoVo.from(data));
                    }

                    @Override
                    public void onFail() {
                        iListView.loadError();
                    }
                }
        );
    }

    public void loadListMoreByType(int typeId, int index,int limit) {
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getTypeMovList(typeId, index, limit), new BaseApi.IResponseListener<VideoListDto>() {
                    @Override
                    public void onSuccess(VideoListDto data) {
                        iListView.loadMore(CommonVideoVo.from(data));
                    }

                    @Override
                    public void onFail() {
                        iListView.loadError();
                    }
                }
        );
    }
}
