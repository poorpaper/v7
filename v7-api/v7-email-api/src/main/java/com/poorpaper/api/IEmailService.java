package com.poorpaper.api;

import com.poorpaper.commons.pojo.ResultBean;

public interface IEmailService {
    public ResultBean sendBirthday(String to, String username);
    public ResultBean sendActivate(String to, String username);
}
