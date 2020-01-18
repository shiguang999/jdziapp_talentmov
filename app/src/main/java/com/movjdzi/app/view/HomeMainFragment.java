package com.movjdzi.app.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.lib.common.util.DataInter;
import com.movjdzi.app.R;
import com.movjdzi.app.adapter.HomePagerAdpter;
import com.movjdzi.app.category.CategoryActivity;
import com.movjdzi.app.http.UrlConfig;
import com.movjdzi.app.model.vo.VideoTypeVo;
import com.movjdzi.app.view.list.MovListFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author huangyong
 * createTime 2019-09-14
 */
public class HomeMainFragment extends Fragment {

    @BindView(R.id.tablayout)
    SlidingTabLayout tablayout;
    @BindView(R.id.self_history)
    ImageView history;
    @BindView(R.id.to_search)
    TextView toSearch;
    @BindView(R.id.home_head)
    RelativeLayout homeHead;
    @BindView(R.id.toobar)
    Toolbar toobar;
    @BindView(R.id.tab_vp)
    ViewPager tabVp;
    Unbinder unbinder;
    @BindView(R.id.self_favor)
    ImageView selfFavor;


    public static HomeMainFragment getInstance(VideoTypeVo videoTypeVo) {
        HomeMainFragment mainFragment = new HomeMainFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DataInter.KEY.TYPE_ID, videoTypeVo);
        mainFragment.setArguments(bundle);
        return mainFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_tab_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        initview();
        return view;
    }

    private void initview() {

        VideoTypeVo typeVo = (VideoTypeVo) getArguments().getSerializable(DataInter.KEY.TYPE_ID);

        if (typeVo == null) {
            return;
        }

        ArrayList<VideoTypeVo.ClassBean> movie = new ArrayList<>();
        ArrayList<VideoTypeVo.ClassBean> seris = new ArrayList<>();
        ArrayList<VideoTypeVo.ClassBean> currtoon = new ArrayList<>();
        ArrayList<VideoTypeVo.ClassBean> show = new ArrayList<>();


        ArrayList<Fragment> arrayList = new ArrayList<>();

        for (VideoTypeVo.ClassBean classInfo : typeVo.getClassInfo()) {

            if (classInfo.getType_id() == 1 || classInfo.getType_id() == 2) {
                continue;
            }

            if (classInfo.getType_name().endsWith("剧")) {
                seris.add(classInfo);
                UrlConfig.seri = seris;
            }
            if (classInfo.getType_name().endsWith("片")) {
                movie.add(classInfo);
                UrlConfig.movie = movie;
            }
            if (classInfo.getType_name().contains("动漫")) {
                currtoon.add(classInfo);
                UrlConfig.curtoon = currtoon;
            }
            if (classInfo.getType_name().contains("综艺")) {
                show.add(classInfo);
                UrlConfig.show = show;
            }

        }


        toSearch.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
        });


        HomeTabFragment rootFragment = new HomeTabFragment();
        arrayList.add(rootFragment);

        MovieRootFragment movieTabRoot = MovieRootFragment.newInstance(movie);
        MovieRootFragment seriTabRoot = MovieRootFragment.newInstance(seris);
        arrayList.add(movieTabRoot);
        arrayList.add(seriTabRoot);


        if (currtoon.size() > 1) {
            MovieRootFragment curtoonTabRoot = MovieRootFragment.newInstance(currtoon);
            arrayList.add(curtoonTabRoot);
        } else {
            MovListFragment listFragment = MovListFragment.newInstance(currtoon.get(0).getType_id());
            arrayList.add(listFragment);
        }

        if (show.size() > 1) {
            MovieRootFragment showTabRoot = MovieRootFragment.newInstance(show);
            arrayList.add(showTabRoot);
        } else {
            MovListFragment listFragment = MovListFragment.newInstance(show.get(0).getType_id());
            arrayList.add(listFragment);
        }

        HomePagerAdpter homePagerAdpter = new HomePagerAdpter(getChildFragmentManager(), arrayList, getActivity());
        tabVp.setAdapter(homePagerAdpter);
        tablayout.setViewPager(tabVp);

        history.setOnClickListener(v -> AllHistoryActivity.startTo(getContext()));
        selfFavor.setOnClickListener(v -> {
            CategoryActivity.start(getActivity());
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
