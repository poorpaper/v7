package com.poorpaper.v7smsservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.poorpaper.api.ISMS;
import com.poorpaper.api.pojo.SMSResponse;

import java.io.DataInput;

@Service
public class SMSService implements ISMS {
    @Override
    public SMSResponse sendCodeMessage(String phone, String code) {
        //凭证信息
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou",
                "LTAI5tPEfWt4bcdFKqEdH9jm", "fF30XuOKqtc7uKNvGGSWyZhIUDCUZ1");
        IAcsClient client = new DefaultAcsClient(profile);

        //1.0  2.0
        //开源软件
        CommonRequest request = new CommonRequest();
//        request.setMethod(MethodType.POST);
//        request.setDomain("dysmsapi.aliyuncs.com");
//        request.setVersion("2017-05-25");
//        request.setAction("SendSms");
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysAction("SendSms");
        request.setSysVersion("2017-05-25");

        request.putQueryParameter("RegionId", "cn-hangzhou");
        //手机号
        request.putQueryParameter("PhoneNumbers", phone);
        //模板信息
        request.putQueryParameter("SignName", "阿里云短信测试");
        request.putQueryParameter("TemplateCode", "SMS_154950909");
        request.putQueryParameter("TemplateParam", "{\"code\":\""+code+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            // 将返回的结果的String转换为一个对象
//            ObjectMapper objectMapper = new ObjectMapper();
//            return objectMapper.readValue(response.getData(), SMSResponse.class);
            Gson gson = new Gson();
            System.out.println(response.getData());
            return gson.fromJson(response.getData(), SMSResponse.class);
//            return response.getData();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public SMSResponse sendBirthdayGreeting(String phone, String username) {
        //凭证信息
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou",
                "LTAI5tPEfWt4bcdFKqEdH9jm", "fF30XuOKqtc7uKNvGGSWyZhIUDCUZ1");
        IAcsClient client = new DefaultAcsClient(profile);

        //1.0  2.0
        //开源软件
        CommonRequest request = new CommonRequest();
//        request.setMethod(MethodType.POST);
//        request.setDomain("dysmsapi.aliyuncs.com");
//        request.setVersion("2017-05-25");
//        request.setAction("SendSms");
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysAction("SendSms");
        request.setSysVersion("2017-05-25");

        request.putQueryParameter("RegionId", "cn-hangzhou");
        //手机号
        request.putQueryParameter("PhoneNumbers", phone);
        //模板信息
        request.putQueryParameter("SignName", "阿里云短信测试");
        request.putQueryParameter("TemplateCode", "SMS_154950909");
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + username +"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            // 将返回的结果的String转换为一个对象
//            ObjectMapper objectMapper = new ObjectMapper();
//            return objectMapper.readValue(response.getData(), SMSResponse.class);
            Gson gson = new Gson();
            System.out.println(response.getData());
            return gson.fromJson(response.getData(), SMSResponse.class);
//            return response.getData();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }


    public SMSResponse sendBirthdayGreetingMessage(String phone, String username) {
        // 亲爱的${username},今天是的您的生日...
        return null;
    }
    // 未来这个短信服务，可以支持多种方式的短息
    // 生日祝福的短信，申请对应的短信模板
    // 积分变更的短信，申请对应的模板
}
