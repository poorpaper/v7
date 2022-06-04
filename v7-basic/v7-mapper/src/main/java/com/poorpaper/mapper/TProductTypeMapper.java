package com.poorpaper.mapper;

import com.poorpaper.commons.base.IBaseDao;
import com.poorpaper.entity.TProductType;
import org.springframework.stereotype.Repository;

/**
 * TProductTypeMapper继承基类
 */
@Repository
public interface TProductTypeMapper extends IBaseDao<TProductType, Integer> {
}