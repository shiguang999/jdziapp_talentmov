package com.movjdzi.app.presenter.iview;

import com.movjdzi.app.model.dto.PayLogDto;
import com.movjdzi.app.model.vo.VipVo;

/**
 * @author huangyong
 * createTime 2019-09-25
 */
public interface IVipView extends IBase {
    void payDone(VipVo from);

    void loadLogDone(PayLogDto data);
}
