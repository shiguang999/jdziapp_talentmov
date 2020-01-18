package com.movjdzi.app.presenter.iview;

import com.movjdzi.app.model.vo.CommonVideoVo;

import java.util.ArrayList;

/**
 * @author huangyong
 * createTime 2019-09-15
 */
public interface IRecView extends IBase{

    void loadRecmmedDone(ArrayList<CommonVideoVo> videoVos);
}
