package com.poorpaper.v7productservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.poorpaper.api.IProductTypeService;
import com.poorpaper.commons.base.BaseServiceImpl;
import com.poorpaper.commons.base.IBaseDao;
import com.poorpaper.entity.TProductType;
import com.poorpaper.mapper.TProductTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductTypeServiceImpl extends BaseServiceImpl<TProductType, Integer> implements IProductTypeService {

    @Autowired
    private TProductTypeMapper productTypeMapper;

    @Resource(name = "myStringRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public IBaseDao<TProductType, Integer> getBaseDao() {
        return productTypeMapper;
    }

    /**
     * 重写获取列表的方法，加入判断缓存的逻辑
     * @return
     */
    @Override
    public List<TProductType> list() {
        // 1.查询当前缓存是否存在分类信息
        List<TProductType> list = (List<TProductType>) redisTemplate.opsForValue().get("productType:list");
        if (list == null || list.size() == 0) {
            // 2.缓存不存在
            list = super.list();
            // 3.将查询结果保存到缓存中
            redisTemplate.opsForValue().set("productType:list", list);
        }
        return list;
    }
}
