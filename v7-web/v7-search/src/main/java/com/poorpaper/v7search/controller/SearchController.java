package com.poorpaper.v7search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.poorpaper.api.ISearchService;
import com.poorpaper.commons.pojo.PageResultBean;
import com.poorpaper.commons.pojo.ResultBean;
import com.poorpaper.entity.TProduct;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("search")
public class SearchController {

    @Reference
    private ISearchService searchService;

    @RequestMapping("synAllData")
    public ResultBean synAllData() {
        return searchService.synAllData();
    }

    /**
     * 此接口适用于APP端
     * 或者AJAX异步加载数据的方式
     * @param keywords
     * @return
     */
    @RequestMapping("queryByKeyWords")
    @ResponseBody
    public ResultBean queryByKeyWords(String keywords) {
        return searchService.queryByKeywords(keywords);
    }

    @RequestMapping("queryByKeyWords4PC/{pageIndex}/{pageSize}")
    public String queryByKeyWords4PC(String keywords, Model model,
                                     @PathVariable("pageIndex") Integer pageIndex,
                                     @PathVariable("pageSize") Integer pageSize) {
        ResultBean resultBean =searchService.queryByKeywords(keywords, pageIndex, pageSize);
        if ("200".equals(resultBean.getStatusCode())) {
//            List<TProduct> list = (List<TProduct>) resultBean.getData();
            PageResultBean<TProduct> pageResultBean = (PageResultBean<TProduct>) resultBean.getData();
            model.addAttribute("page", pageResultBean);
        }
        return "list";
    }
}
