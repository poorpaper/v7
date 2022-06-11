package com.poorpaper.v7productservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.poorpaper.api.IProductTypeService;
import com.poorpaper.commons.base.BaseServiceImpl;
import com.poorpaper.commons.base.IBaseDao;
import com.poorpaper.entity.TProductType;
import com.poorpaper.mapper.TProductTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ProductTypeServiceImpl extends BaseServiceImpl<TProductType, Integer> implements IProductTypeService {

    @Autowired
    private TProductTypeMapper productTypeMapper;

    @Override
    public IBaseDao<TProductType, Integer> getBaseDao() {
        return productTypeMapper;
    }
}
