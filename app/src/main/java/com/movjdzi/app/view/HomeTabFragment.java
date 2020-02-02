package com.movjdzi.app.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.media.playerlib.model.AdConfigDto;
import com.media.playerlib.widget.GlobalDATA;
import com.movjdzi.app.R;
import com.movjdzi.app.adapter.BannerEntity;
import com.movjdzi.app.adapter.BannerEntityViewBinder;
import com.movjdzi.app.adapter.CategoryViewBinder;
import com.movjdzi.app.adapter.FooterViewViewBinder;
import com.movjdzi.app.adapter.HomeAdEntity;
import com.movjdzi.app.adapter.HomeAdEntityViewBinder;
import com.movjdzi.app.adapter.ItemVideosViewBinder;
import com.movjdzi.app.model.Category;
import com.movjdzi.app.model.FooterView;
import com.movjdzi.app.model.dto.HomeLevelDto;
import com.movjdzi.app.model.dto.VideoListDto;
import com.movjdzi.app.model.vo.CommonVideoVo;
import com.movjdzi.app.presenter.HomePresenter;
import com.movjdzi.app.presenter.iview.IHomeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.bakumon.statuslayoutmanager.library.DefaultOnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * @author huangyong
 * createTime 2019-09-11
 */
public class HomeTabFragment extends Fragment implements IHomeView {


    @BindView(R.id.home_rv)
    RecyclerView homeRv;
    Unbinder unbinder;
    private MultiTypeAdapter adapter;
    @VisibleForTesting
    List<Object> items;
    private StatusLayoutManager statusLayoutManager;
    private HomePresenter homePresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {


        statusLayoutManager = new StatusLayoutManager.Builder(homeRv)
                .setDefaultLayoutsBackgroundColor(Color.WHITE)
                .setDefaultEmptyClickViewTextColor(getResources().getColor(R.color.colorPrimary)) // 修改顶部导航颜色
                .setLoadingLayout(R.layout.loading_layout)
                .setEmptyLayout(R.layout.empty_layout)
                .setDefaultEmptyText("当前分类可能没有内容")
                .setOnStatusChildClickListener(new DefaultOnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
                        statusLayoutManager.showLoadingLayout();
                        homePresenter.getHomeLevel();
                    }

                    @Override
                    public void onErrorChildClick(View view) {
                        statusLayoutManager.showLoadingLayout();
                        homePresenter.getHomeLevel();
                    }
                })
                .build();
        statusLayoutManager.showLoadingLayout();

        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                Object item = items.get(position);
                return (item instanceof HomeAdEntity || item instanceof BannerEntity || item instanceof Category || item instanceof FooterView) ? 3 : 1;
            }
        };
        adapter = new MultiTypeAdapter();

        layoutManager.setSpanSizeLookup(spanSizeLookup);
        homeRv.setLayoutManager(layoutManager);

        //注册各个区域的view
        adapter.register(BannerEntity.class, new BannerEntityViewBinder()); //首页顶部导航
        adapter.register(Category.class, new CategoryViewBinder());
        adapter.register(VideoListDto.DataBean.class, new ItemVideosViewBinder());
        adapter.register(FooterView.class, new FooterViewViewBinder());
        adapter.register(HomeAdEntity.class, new HomeAdEntityViewBinder()); // 推荐页广告

        homeRv.setAdapter(adapter);

        items = new ArrayList<>();

        items.add(new BannerEntity());
        homePresenter = new HomePresenter(this);
        homePresenter.getHomeLevel();

        homePresenter.getHomeBannerLevel(9,1,10);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void loadDone(HomeLevelDto videoVos) {
        statusLayoutManager.showSuccessLayout();
        AdConfigDto.DataBean dataBean = new Gson().fromJson(GlobalDATA.AD_INFO, AdConfigDto.DataBean.class);
        if (videoVos.getData() != null && dataBean != null) {
            if (videoVos.getData().getLe1() != null && videoVos.getData().getLe1().size() > 0) {
                items.add(new Category("正在上映", v -> {
                    AllLevelActivity.startTo(getContext(), 7);
                }));
                items.addAll(videoVos.getData().getLe1());
            }
            if(dataBean.getAd_home_1().getShow()) items.add(new HomeAdEntity(0));

            if (videoVos.getData().getLe2() != null && videoVos.getData().getLe2().size() > 0) {
                items.add(new Category("火热更新，必看榜单", v -> { //好剧不断品质影视，
                    AllLevelActivity.startTo(getContext(), 2);
                }));
                items.addAll(videoVos.getData().getLe2());
            }
            if(dataBean.getAd_home_2().getShow()) items.add(new HomeAdEntity(1));

            if (videoVos.getData().getLe3() != null && videoVos.getData().getLe3().size() > 0) {
                items.add(new Category("最新电影", v -> {
                    AllLevelActivity.startTo(getContext(), 3);
                }));
                items.addAll(videoVos.getData().getLe3());
            }
            if(dataBean.getAd_home_3().getShow()) items.add(new HomeAdEntity(2));

            if (videoVos.getData().getLe4() != null && videoVos.getData().getLe4().size() > 0) {
                items.add(new Category("最新电视剧", v -> {
                    AllLevelActivity.startTo(getContext(), 4);
                }));
                items.addAll(videoVos.getData().getLe4());
            }
            if(dataBean.getAd_home_4().getShow()) items.add(new HomeAdEntity(3));

            if (videoVos.getData().getLe5() != null && videoVos.getData().getLe5().size() > 0) {
                items.add(new Category("最新动漫", v -> {
                    AllLevelActivity.startTo(getContext(), 5);
                }));
                items.addAll(videoVos.getData().getLe5());
            }
            if(dataBean.getAd_home_5().getShow()) items.add(new HomeAdEntity(0));

            if (videoVos.getData().getLe6() != null && videoVos.getData().getLe6().size() > 0) {
                items.add(new Category("最新综艺", v -> {
                    AllLevelActivity.startTo(getContext(), 6);
                }));
                items.addAll(videoVos.getData().getLe6());
            }

            items.add(new FooterView("已经到底了"));

            adapter.setItems(items);
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getContext(), "数据加载失败，请稍后重试", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void loadDone(ArrayList<CommonVideoVo> videoVos) {

    }

    @Override
    public void loadMore(ArrayList<CommonVideoVo> videoVos) {

    }

    @Override
    public void loadBanner(ArrayList<CommonVideoVo> videoVos) {
        if (items!=null&&items.size()>0){
            BannerEntity bannerEntity = (BannerEntity) items.get(0);
            if (bannerEntity!=null){
                bannerEntity.setVideoVos(videoVos);
                adapter.notifyItemChanged(0);
            }
        }
    }

    @Override
    public void loadError() {
        Toast.makeText(getContext(), "数据加载失败，请稍后重试", Toast.LENGTH_SHORT).show();
        statusLayoutManager.showErrorLayout();
    }

    @Override
    public void loadEmpty() {

    }
}
