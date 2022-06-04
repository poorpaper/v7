package com.poorpaper.v7productservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.poorpaper.api.IProductService;
import com.poorpaper.commons.base.BaseServiceImpl;
import com.poorpaper.commons.base.IBaseDao;
import com.poorpaper.entity.TProduct;
import com.poorpaper.entity.TProductDesc;
import com.poorpaper.mapper.TProductDescMapper;
import com.poorpaper.mapper.TProductMapper;
import com.poorpaper.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class ProductServiceImpl extends BaseServiceImpl<TProduct, Long> implements IProductService {

    @Autowired
    private TProductMapper productMapper;

    @Autowired
    private TProductDescMapper productDescMapper;

    @Override
    public IBaseDao<TProduct, Long> getBaseDao() {
        return productMapper;
    }



    @Override
    public Long add(ProductVO productVO) {
        productMapper.insertSelective(productVO.getProduct());
        TProductDesc productDesc = new TProductDesc();
        productDesc.setProductId(productVO.getProduct().getId());
        productDesc.setProductDesc(productVO.getProductDesc());
        productDescMapper.insertSelective(productDesc);
        return productVO.getProduct().getId();
    }
}
