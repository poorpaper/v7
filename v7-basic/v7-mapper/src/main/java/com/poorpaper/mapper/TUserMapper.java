package com.poorpaper.mapper;

import com.poorpaper.commons.base.IBaseDao;
import com.poorpaper.entity.TUser;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import java.util.List;

/**
 * TUserDAO继承基类
 */
@Repository
public interface TUserMapper extends IBaseDao<TUser, Long> {
    List<TUser> selectByUserName(String userName);
    List<TUser> selectByPhone(String phone);
    List<TUser> selectByEmail(String email);
}