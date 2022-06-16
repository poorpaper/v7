package com.poorpaper.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 生产者，负责发送消息到队列
 */
public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1.创建连接，连接上MQ服务器
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.56.129");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/java1907");
        connectionFactory.setUsername("java1907");
        connectionFactory.setPassword("123");

        Connection connection = connectionFactory.newConnection();

        // 2.基于这个连接对象，创建对应的通道
        Channel channel = connection.createChannel();

        // 3.声明队列
        // 如果该队列不存在，则创建该队列，否则不创建
        channel.queueDeclare("simple-queue", true, false, false, null);

        // 4.发送消息到队列上
        String message = "DIE! DIE! DIE!";
        // 我们没有指定交换机
        // 实际是使用默认交换机
        channel.basicPublish("", "simple-queue", null, message.getBytes());
        System.out.println("发送消息成功！");
    }
}
