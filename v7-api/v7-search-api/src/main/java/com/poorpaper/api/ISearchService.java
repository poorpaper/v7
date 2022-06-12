package com.poorpaper.api;

import com.poorpaper.commons.pojo.ResultBean;

public interface ISearchService {
    /**
     * 做全量同步
     * 在数据初始化时使用
     * @return
     */
    public ResultBean synAllData();

    /**
     * 增量同步
     * @param id
     * @return
     */
    public ResultBean synById(Long id);

    /**
     * 删除
     * @param id
     * @return
     */
    public ResultBean delById(Long id);

    /**
     * 查询操作
     * @param keywords
     * @return
     */
    public ResultBean queryByKeywords(String keywords);

    ResultBean queryByKeywords(String keywords, Integer pageIndex, Integer pageSize);
}
