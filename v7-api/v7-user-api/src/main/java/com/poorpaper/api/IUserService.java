package com.poorpaper.api;

import com.poorpaper.commons.base.IBaseService;
import com.poorpaper.commons.pojo.ResultBean;
import com.poorpaper.entity.TUser;

public interface IUserService extends IBaseService<TUser, Long> {

    public ResultBean checkUserNameExists(String username);
    public ResultBean checkPhoneExists(String phone);
    public ResultBean checkEmailExists(String email);

    public ResultBean generateCode(String identification);

    // 添加用户，是否可以用默认的实现？
    // 可。（默认插入）

    // 激活用户，改变用户的状态值，也不用写（默认update）
}
