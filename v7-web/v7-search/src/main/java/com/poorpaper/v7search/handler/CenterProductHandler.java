package com.poorpaper.v7search.handler;

import com.alibaba.dubbo.config.annotation.Reference;
import com.poorpaper.api.ISearchService;
import com.poorpaper.commons.constant.MQConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = MQConstant.QUEUE.CENTER_PRODUCT_EXCHANGE_SEARCH_QUEUE)
@Slf4j
public class CenterProductHandler {
    // 1. 声明队列 center-product-exchange-search-queue
    // 2. 绑定交换机
    // 借助管理平台来实现

    @Reference
    private ISearchService searchService;

    @RabbitHandler
    public void process(Long id) {
        log.info("处理了id为：{}的消息", id);
        searchService.synById(id);
    }
}
