package com.movjdzi.app.presenter.iview;

import com.movjdzi.app.model.dto.TopicRDto;
import com.movjdzi.app.model.vo.CommonVideoVo;

import java.util.ArrayList;

/**
 * @author huangyong
 * createTime 2019-09-14
 */
public interface ITopicView extends IBase{
    void loadDone(TopicRDto dto);

    void loadMore(TopicRDto data);

    void loadMore(ArrayList<CommonVideoVo> from);

    void loadDone(ArrayList<CommonVideoVo> from);
}
