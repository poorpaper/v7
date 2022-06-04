package com.poorpaper.api;

import com.github.pagehelper.PageInfo;
import com.poorpaper.commons.base.IBaseService;
import com.poorpaper.entity.TProduct;
import com.poorpaper.vo.ProductVO;

import java.util.List;

public interface IProductService extends IBaseService<TProduct, Long> {

//    public PageInfo<TProduct> page(Integer pageIndex, Integer pageSize);

    public Long add(ProductVO productVO);   // primary key influenced
}
