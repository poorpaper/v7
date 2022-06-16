package com.poorpaper.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 生产者，负责发送消息到队列
 */
public class Producer {

    private static final String EXCHANGE_NAME = "direct_exchange";

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

        // 3.声明交换机
        // durable autoDelete exclusive
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        // 4.发送消息到交换机上
//        for (int i = 0; i < 10; i++) {
//            String message = "说了多少次了？" + i + "次";
//            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
//        }
        String message1 = "重大的小消息：今晚只要打平就出线";
        channel.basicPublish(EXCHANGE_NAME, "football", null, message1.getBytes());

        String message2 = "小小的大消息：只要连赢1场，我们就能破纪录";
        channel.basicPublish(EXCHANGE_NAME, "basketball", null, message2.getBytes());

        // 我们没有指定交换机
        // 实际是使用默认交换机
        System.out.println("发送消息成功！");
    }
}
