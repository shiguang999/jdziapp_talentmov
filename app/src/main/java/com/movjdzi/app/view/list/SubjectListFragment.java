package com.movjdzi.app.view.list;

import android.os.Bundle;

import com.media.playerlib.model.DataInter;
import com.movjdzi.app.model.dto.TopicRDto;
import com.movjdzi.app.model.vo.CommonVideoVo;
import com.movjdzi.app.presenter.TopicPresenter;
import com.movjdzi.app.presenter.iview.ITopicView;

import java.util.ArrayList;

/**
 * @author huangyong
 * createTime 2019-09-14
 */
public class SubjectListFragment extends BaseMovListFragment implements ITopicView {


    private TopicPresenter homePresenter;
    private int topicId;

    public static SubjectListFragment getInstance(int topicId){
        Bundle bundle = new Bundle();
        bundle.putInt(DataInter.Key.TOPIC_ID,topicId);
        SubjectListFragment levelFragment = new SubjectListFragment();
        levelFragment.setArguments(bundle);
        return levelFragment;
    }

    @Override
    protected void initMovData(Bundle arguments) {
        topicId = getArguments().getInt(DataInter.Key.TOPIC_ID);
        if (topicId == 0) {
            return;
        }
        homePresenter = new TopicPresenter(this);
        homePresenter.getTopicList(topicId);
    }

    @Override
    protected void pullLoadMore() {
        if (movRv!=null){
            movRv.setNoMore(true);
        }
    }

    @Override
    protected void pullRefresh() {
        homePresenter.getTopicList(topicId);
    }


    @Override
    public void loadError() {
        super.loadError();
    }

    @Override
    public void loadDone(TopicRDto dto) {

    }

    @Override
    public void loadMore(ArrayList<CommonVideoVo> videoVos) {
    }

    @Override
    public void loadMore(TopicRDto data) {

    }
}
