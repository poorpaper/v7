package com.poorpaper.commons.base;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;

public interface IBaseService<Model, PK extends Serializable> {
    int deleteByPrimaryKey(PK id);

    int insert(Model record);

    int insertSelective(Model record);

    Model selectByPrimaryKey(PK id);

    int updateByPrimaryKeySelective(Model record);

    int updateByPrimaryKey(Model record);

    List<Model> list();

    PageInfo<Model> page(Integer pageIndex, Integer pageSize);
}