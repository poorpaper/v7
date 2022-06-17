package com.poorpaper.mapper;

import com.poorpaper.commons.base.IBaseDao;
import com.poorpaper.entity.TUser;
import org.springframework.stereotype.Repository;

/**
 * TUserDAO继承基类
 */
@Repository
public interface TUserMapper extends IBaseDao<TUser, Long> {
}