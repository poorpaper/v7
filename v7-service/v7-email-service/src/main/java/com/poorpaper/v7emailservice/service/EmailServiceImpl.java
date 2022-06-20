package com.poorpaper.v7emailservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.poorpaper.api.IEmailService;
import com.poorpaper.commons.pojo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${email.server}")
    private String emailServer;

    @Override
    public ResultBean sendBirthday(String to, String username) {
        // 1.构建邮件对象
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(emailServer);
            helper.setTo(to);
            helper.setSubject("❤生 日 大 祝 福❤");
            Context context = new Context();
            context.setVariable("username", username);
            String content = templateEngine.process("birthday", context);
            helper.setText(content, true);
            // 2.发送邮件
            javaMailSender.send(message);
            return new ResultBean("200", "ok");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return new ResultBean("200", "邮件发送失败");
    }

    @Override
    public ResultBean sendActivate(String to, String username) {
        return null;
    }
}
