package com.poorpaper.api;

import com.poorpaper.api.pojo.SMSResponse;

public interface ISMS {
    // 也支持同步调用
    public SMSResponse sendCodeMessage(String phone, String code);
    public SMSResponse sendBirthdayGreeting(String phone, String username);
}
