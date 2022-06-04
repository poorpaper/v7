package com.poorpaper.v7productservice;

import com.github.pagehelper.PageInfo;
import com.poorpaper.api.IProductService;
import com.poorpaper.entity.TProduct;
import com.poorpaper.vo.ProductVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class V7ProductServiceApplicationTests {

    @Autowired
    private IProductService productService;

    @Test
    void contextLoads() {
        TProduct product = productService.selectByPrimaryKey(1L);
        System.out.println(product.getName());
    }

    @Test
    public void listTest() {
        List<TProduct>  list = productService.list();
        for (TProduct tProduct : list) {
            System.out.println(tProduct.getName());
        }
    }

    @Test
    public void pageTest() {
        PageInfo<TProduct> page = productService.page(1, 1);
        System.out.println(page.getList().size());
    }

    @Test
    @Transactional
    public void addVoTest() {
        TProduct product = new TProduct();
        product.setName("geli phone");
        product.setPrice(3999L);
        product.setSalePrice(3666L);
        product.setSalePoint("coooool");
        product.setImage("123");
        product.setTypeId(1);
        product.setTypeName("电子数码");
        ProductVO vo = new ProductVO();
        vo.setProduct(product);
        vo.setProductDesc("就是抗摔");

        System.out.println(productService.add(vo));
    }
}
