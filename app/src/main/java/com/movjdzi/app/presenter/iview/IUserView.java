package com.movjdzi.app.presenter.iview;

import com.movjdzi.app.model.dto.LoginDto;

/**
 * @author huangyong
 * createTime 2019-09-17
 */
public interface IUserView extends IBase{
    void loadDone(LoginDto dto);
}
