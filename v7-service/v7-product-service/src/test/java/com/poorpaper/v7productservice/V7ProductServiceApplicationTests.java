package com.poorpaper.v7productservice;

import com.github.pagehelper.PageInfo;
import com.poorpaper.api.IProductService;
import com.poorpaper.api.IProductTypeService;
import com.poorpaper.entity.TProduct;
import com.poorpaper.entity.TProductType;
import com.poorpaper.vo.ProductVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
class V7ProductServiceApplicationTests {

    @Autowired
    private IProductService productService;

    @Autowired
    private IProductTypeService productTypeService;

    @Autowired
    private DataSource dataSource;

    @Test
    public void poolTest() throws SQLException {
        System.out.println(dataSource.getConnection());
    }

    @Test
    public void listTypeTest() {
        List<TProductType> list = productTypeService.list();
        for (TProductType productType : list) {
            System.out.println(productType.getName());
        }
    }

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
