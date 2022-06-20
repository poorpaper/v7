package com.poorpaper.v7searchservice;

import com.poorpaper.api.ISearchService;
import com.poorpaper.commons.pojo.ResultBean;
import com.poorpaper.entity.TProduct;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class V7SearchServiceApplicationTests {

    // 目标
    // 实现对solr的增删改查操作
    // solr-->数据库

    @Autowired
    private SolrClient solrClient;

    @Autowired
    private ISearchService searchService;

    @Test
    public void synAllDataTest() {
        ResultBean resultBean = searchService.synAllData();
        System.out.println(resultBean.getData());
    }

    @Test
    public void synByIdTest() {
        ResultBean resultBean = searchService.synById(9L);
        System.out.println(resultBean.getData());
    }

    @Test
    public void addOrUpdateTest() throws SolrServerException, IOException {
        // 1. 创建一个document对象
        SolrInputDocument document = new SolrInputDocument();
        // 2. 设置相关属性值
        document.setField("id", 999);
        document.setField("product_name", "小米MIX666代");
        document.setField("product_price", 19999);
        document.setField("product_sale_point", "全球最高像素的手机");
        document.setField("product_image", "123");
        // 3. 保存
        solrClient.add("collection2", document);
        solrClient.commit("collection2");
    }

    @Test
    public void queryTest() throws SolrServerException, IOException {
        // 1. 组装查询条件
        SolrQuery queryCondition = new SolrQuery();
        // 注意： 小米MIX4代会分词！！！
        queryCondition.setQuery("product_name:小米MIX4代");
        // 2. 执行查询
        QueryResponse response = solrClient.query(queryCondition);
        // 3. 得到结果
        SolrDocumentList solrDocuments = response.getResults();
        for (SolrDocument document : solrDocuments) {
            System.out.println(document.getFieldValue("product_name") + "->" +
                    document.getFieldValue("product_price"));
        }
    }

    @Test
    public void delTest() throws SolrServerException, IOException {
        // 注意： 删除时也会分词！！！
        solrClient.deleteByQuery("product_name:小米MIX4代");
        // 可以使用deleteById(1) 来精准删除
        solrClient.commit();
    }

    @Test
    public void querySolrTest() {
        ResultBean resultBean = searchService.queryByKeywords("初级开发工程师");
        List<TProduct> products = (List<TProduct>) resultBean.getData();
        for (TProduct product : products) {
            System.out.println(product.getName());
        }
    }
}
