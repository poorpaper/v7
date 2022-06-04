package com.poorpaper.commons.base;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.ui.Model;

import java.io.Serializable;
import java.util.List;

public abstract class BaseServiceImpl<T, PK extends Serializable> implements IBaseService<T, PK>{

    public abstract IBaseDao<T, PK> getBaseDao();

    @Override
    public int deleteByPrimaryKey(PK id) {
        return getBaseDao().deleteByPrimaryKey(id);
    }

    @Override
    public int insert(T record) {
        return getBaseDao().insert(record);
    }

    @Override
    public int insertSelective(T record) {
        return getBaseDao().insertSelective(record);
    }

    @Override
    public T selectByPrimaryKey(PK id) {
        return getBaseDao().selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(T record) {
        return getBaseDao().updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(T record) {
        return getBaseDao().updateByPrimaryKey(record);
    }

    @Override
    public List<T> list() { return getBaseDao().list(); }

    @Override
    public PageInfo<T> page(Integer pageIndex, Integer pageSize) {
        //1.set page info
        PageHelper.startPage(pageIndex, pageSize);
        //2.get result info, with limit
        List<T> list = this.list();
        //3.return page object
        PageInfo<T> pageInfo = new PageInfo<T>(list, 3);
        return pageInfo;
    }
}
