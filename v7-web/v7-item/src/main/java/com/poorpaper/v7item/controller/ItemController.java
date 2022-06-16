package com.poorpaper.v7item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.poorpaper.api.IProductService;
import com.poorpaper.commons.pojo.ResultBean;
import com.poorpaper.entity.TProduct;
import com.poorpaper.v7item.entity.Product;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Controller
@RequestMapping("item")
@Slf4j
public class ItemController {

    @Reference
    private IProductService productService;

    @Autowired
    private Configuration configuration;

    @Autowired
    private ThreadPoolExecutor pool;

    private class CreateHtmlTask implements Callable<Long> {
        private Long productId;

        public CreateHtmlTask(Long productId) {
            this.productId = productId;
        }

        @Override
        public Long call() throws Exception {
            // 1.根据id，获取到商品数据
            TProduct product = productService.selectByPrimaryKey(productId);
            try {
                // 2.采用Freemarker生成对应的商品详情页
                Template template = configuration.getTemplate("item.ftl");
                Map<String, Object> data = new HashMap<>();
                data.put("product", product);
                // 3.输出
                // 获取到项目运行时的路径
                String serverPath = ResourceUtils.getURL("classpath:static").getPath();
                StringBuilder path = new StringBuilder(serverPath).append(File.separator).append(productId).append(".html");
                FileWriter out = new FileWriter(path.toString());
                template.process(data, out);
            } catch (IOException | TemplateException e) {
                e.printStackTrace();
                // TODO LOGS
                return productId;
            }
            System.out.println("ok!!!");
            return 0L;
        }
    }

    @RequestMapping("createById/{id}")
    @ResponseBody
    public ResultBean createById(@PathVariable("id") Long id) throws IOException, TemplateException {
        create(id);
        return new ResultBean("200", "生成静态页面成功！");
    }

    private void create(Long id) throws IOException, TemplateException {
        // 1.根据id，获取到商品数据
        TProduct product = productService.selectByPrimaryKey(id);
        // 2.采用Freemarker生成对应的商品详情页
        Template template = configuration.getTemplate("item.ftl");
        Map<String, Object> data = new HashMap<>();
        data.put("product", product);
        // 3.输出
        // 获取到项目运行时的路径
        String serverPath = ResourceUtils.getURL("classpath:static").getPath();
        StringBuilder path = new StringBuilder(serverPath).append(File.separator).append(id).append(".html");
        FileWriter out = new FileWriter(path.toString());
        template.process(data, out);
    }

    @RequestMapping("batchCreate")
    @ResponseBody
    public ResultBean batchCreate(@RequestParam List<Long> ids) throws TemplateException, IOException {
        // 优化
        // 1.放缓存，减少查询数据库
        // 2.其中某个出错了，所有都出错
        // 3.一核工作，多核围观

        // 批量生成页面：不需要关注先后的生成顺序
        // 多线程 16（核） * 2 = 32
        // 线程池 （线程数？）
        // 如何创建线程

        List<Future<Long>> list = new ArrayList<>(ids.size());
        for (Long id : ids) {
//            create(id);
            list.add(pool.submit(new CreateHtmlTask(id)));
        }
        List<Long> errors = new ArrayList<>();
        for (Future future : list) {
            try {
//                System.out.println(future.get());
                Long result = (Long) future.get();
                if (result != 0) {
                    errors.add(result);
                }

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                // TODO LOGS
            }
        }

        for (Long error : errors) {
            // 做错误处理的工作
            // 1.输出日志
            log.error("批量生成页面失败，失败的页面为[{}]", errors);
            // 2.将处理错误的id信息保存到日志表中
            // id product_id retry_times create_update update_time
            // 1  1          0           2019          2019

            // 3.通过定时任务，扫描这张表
            // select * from t_create_html_log where retry_time < 3;
            // update retry_time = retry_time + 1

            // 4.超过3次的记录，需要人工介入
         }

        return new ResultBean("200", "批量生成页面成功");
    }


}
