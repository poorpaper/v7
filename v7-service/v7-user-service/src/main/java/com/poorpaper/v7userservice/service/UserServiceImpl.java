package com.poorpaper.v7userservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.poorpaper.api.IUserService;
import com.poorpaper.commons.base.BaseServiceImpl;
import com.poorpaper.commons.base.IBaseDao;
import com.poorpaper.commons.constant.MQConstant;
import com.poorpaper.commons.pojo.ResultBean;
import com.poorpaper.commons.util.CodeUtils;
import com.poorpaper.entity.TUser;
import com.poorpaper.mapper.TUserMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl extends BaseServiceImpl<TUser, Long> implements IUserService {

    @Autowired
    private TUserMapper userMapper;

    @Resource(name = "myStringRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public IBaseDao<TUser, Long> getBaseDao() {
        return userMapper;
    }

    @Override
    public ResultBean checkUserNameExists(String username) {
        List<TUser> users = userMapper.selectByUserName(username);
        if (users == null || users.size() == 0) {
            return new ResultBean("200", "false");
        }
        return new ResultBean("200", "true");
    }

    @Override
    public ResultBean checkPhoneExists(String phone) {
        List<TUser> users = userMapper.selectByPhone(phone);
        if (users == null || users.size() == 0) {
            return new ResultBean("200", "false");
        }
        return new ResultBean("200", "true");
    }

    @Override
    public ResultBean checkEmailExists(String email) {
        List<TUser> users = userMapper.selectByEmail(email);
        if (users == null || users.size() == 0) {
            return new ResultBean("200", "false");
        }
        return new ResultBean("200", "true");
    }

    @Override
    public ResultBean generateCode(String identification) {
        // 1.生成验证码
        String code = CodeUtils.generateCode(6);
        // 2.往redis保存一个凭证跟验证码的对应关系key-value
        redisTemplate.opsForValue().set(identification, code, 2, TimeUnit.MINUTES);
        Map<String, String> params = new HashMap<>();
        params.put("identification", identification);
        params.put("code", code);
        rabbitTemplate.convertAndSend(MQConstant.EXCHANGE.SMS_EXCHANGE, "sms.code", params);

        Map<String, String> params2 = new HashMap<>();
        params2.put("to", "sdfghjkzx@qq.com");
        params2.put("username", "pppp");
        rabbitTemplate.convertAndSend(MQConstant.EXCHANGE.EMAIL_EXCHANGE, "email.birthday", params2);
        // 3.发送消息，给手机发送验证码
        // 3.1 调通阿里云提供的短信demo
        // 3.2 发送短信这个功能，整个体系很多系统可能都会用上，变成一个公共的服务
        // 3.3 发送消息，异步处理发送短息
        return new ResultBean("200", "OK");
    }
}
