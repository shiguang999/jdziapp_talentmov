package com.movjdzi.app.adapter.event;

import com.movjdzi.app.model.vo.CommonVideoVo;

/**
 * @author huangyong
 * createTime 2019-09-15
 */
public interface OnDetailClickListener {
    void clickReport(String vodId);

    void clickShare(String vodId);

    void clickDesc(CommonVideoVo videoVo);
}
