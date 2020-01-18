package com.movjdzi.app.presenter.iview;

import com.movjdzi.app.model.dto.UpdateDto;

/**
 * @author huangyong
 * createTime 2019-10-09
 */
public interface IUpdate {
    void loadDone(UpdateDto dto);
    void loadEmpty();
}
