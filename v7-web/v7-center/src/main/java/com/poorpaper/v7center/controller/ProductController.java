package com.poorpaper.v7center.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.poorpaper.api.IProductService;
import com.poorpaper.commons.constant.MQConstant;
import com.poorpaper.entity.TProduct;
import com.poorpaper.vo.ProductVO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("product")
public class ProductController {

    @Reference
    private IProductService productService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("get/{id}")
    @ResponseBody
    public TProduct getById(@PathVariable("id") Long id) {
        return productService.selectByPrimaryKey(id);
    }

    @RequestMapping("list")
    public String list(Model model) {
        System.out.println("test");
        List<TProduct> list = productService.list();
        model.addAttribute("list", list);
        return "product/list";
    }

    @RequestMapping("page/{pageIndex}/{pageSize}")
    public String page(Model model,
                       @PathVariable("pageIndex") Integer pageIndex,
                       @PathVariable("pageSize") Integer pageSize) {

        List<TProduct> list = productService.list();
        PageInfo<TProduct> page = productService.page(pageIndex, pageSize);
        model.addAttribute("page", page);
        return "product/list";
    }

    @PostMapping("add")
    public String add(ProductVO vo) {
        Long newId = productService.add(vo);

        // 发送一个消息到消息中间件
        // map.put("type", "add")
        // map.put("data", newId)
        rabbitTemplate.convertAndSend(MQConstant.EXCHANGE.CENTER_PRODUCT_EXCHANGE, "product.add", newId);

        // 跳转回到第一页，按照添加时间排序
        return "redirect:/product/page/1/5";
    }
}
