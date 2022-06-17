package com.poorpaper.v7index.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.poorpaper.api.IProductService;
import com.poorpaper.api.IProductTypeService;
import com.poorpaper.commons.pojo.ResultBean;
import com.poorpaper.entity.TProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("index")
public class IndexController {

    @Reference
    private IProductTypeService productTypeService;

    @RequestMapping("show")
    public String showIndex(Model model) {
        List<TProductType> list = productTypeService.list();
        model.addAttribute("list", list);
        return "index";
    }

    @RequestMapping("listType")
    @ResponseBody
    public ResultBean listType() {
        System.out.println("controller listType");
        List<TProductType> list = productTypeService.list();
        return new ResultBean("200", list);
    }
}
