package com.poorpaper.mapper;

import com.poorpaper.commons.base.IBaseDao;
import com.poorpaper.entity.TProduct;
import org.springframework.stereotype.Repository;

/**
 * TProductMapper继承基类
 */
@Repository
public interface TProductMapper extends IBaseDao<TProduct, Long> {
}