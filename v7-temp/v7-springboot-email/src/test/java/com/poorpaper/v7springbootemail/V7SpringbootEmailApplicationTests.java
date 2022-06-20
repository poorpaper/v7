package com.poorpaper.v7springbootemail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@SpringBootTest
class V7SpringbootEmailApplicationTests {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void sendSimpleMailTest() {
        // 1.构建邮件对象
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("163@163.com");
        message.setTo("163@qq.com");
        message.setSubject("热烈庆祝我公司与网易达成长期的战略合作关系");
        message.setText("您好，赶紧跑路吧");

        // 2.发送邮件
        javaMailSender.send(message);
    }

    @Test
    public void sendHTMLMailTest() throws MessagingException {
        // 1.构建邮件对象
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("163@163.com");
        helper.setTo("163@qq.com");
        helper.setSubject("热烈庆祝我公司与网易达成长期的战略合作关系");
        helper.setText("您好，赶紧跑去<a href='https://www.baidu.com'>度娘</a>吧", true);

        // 2.发送邮件
        javaMailSender.send(message);
    }

    @Test
    public void sendTemplateTest() throws MessagingException {
        // 1.构建邮件对象
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("163@163.com");
        helper.setTo("163@qq.com");
        helper.setSubject("❤生 日 大 祝 福❤");
        Context context = new Context();
        context.setVariable("username", "poorpaper");
        String content = templateEngine.process("birthday", context);
        helper.setText(content, true);

        // 2.发送邮件
        javaMailSender.send(message);
    }

    @Test
    void contextLoads() {
    }

}
