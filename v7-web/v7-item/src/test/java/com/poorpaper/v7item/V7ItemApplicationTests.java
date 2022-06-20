package com.poorpaper.v7item;

import com.poorpaper.v7item.entity.Product;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@SpringBootTest
class V7ItemApplicationTests {

    @Autowired
    private Configuration configuration;

    @Test
    void createHTMLTest() throws IOException, TemplateException {

        // 模板+数据=输出
        // 1.获取到模板对象
        FileTemplateLoader templateLoader = new FileTemplateLoader(new File(
                "H:\\newProject\\v7\\v7-web\\v7-item\\src\\main\\resources\\templates"));
        configuration.setTemplateLoader(templateLoader);
        Template template = configuration.getTemplate("freemarker.ftl");

        // 2.组装数据
        Map<String, Object> data = new HashMap<>();
        Product product = new Product("3天带你精通光明", 1999L, new Date());
        data.put("product", product);
        data.put("username", "poorpaper");
        // 保存集合
        List<Product> list = new ArrayList<>();
        list.add(new Product("1天带你精通睡觉", 999L, new Date()));
        list.add(new Product("7天带你精通睡懒觉", 888L, new Date()));
        // 3.模板+数据结合
        FileWriter fileWriter = new FileWriter(
                "H:\\newProject\\v7\\v7-web\\v7-item\\src\\main\\resources\\static\\f.html");
        template.process(data, fileWriter);
        System.out.println("静态页面生成成功！");
    }

}
