package com.poorpaper.v7register.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.poorpaper.api.IUserService;
import com.poorpaper.commons.pojo.ResultBean;
import com.poorpaper.commons.util.CodeUtils;
import com.poorpaper.entity.TUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("user")
public class UserController {

    @Reference
    private IUserService userService;

    @GetMapping("checkUserNameExists/{username}")
    @ResponseBody
    public ResultBean checkUserNameExists(@PathVariable("username") String username) {
        return userService.checkUserNameExists(username);
    }

    @GetMapping("checkPhoneExists/{phone}")
    @ResponseBody
    public ResultBean checkPhoneExists(@PathVariable("phone") String phone) {
        return userService.checkPhoneExists(phone);
    }

    @GetMapping("checkEmailExists/{email}")
    @ResponseBody
    public ResultBean checkEmailExists(@PathVariable("email") String email) {
        return userService.checkEmailExists(email);
    }

    @PostMapping("generateCode/{identification}")
    @ResponseBody
    public ResultBean generateCode(@PathVariable("identification") String identification) {
//        System.out.println(identification);
        return userService.generateCode(identification);
    }

    /**
     * 适合处理异步请求
     * @return
     */
    @PostMapping("register")
    @ResponseBody
    public ResultBean register(TUser user) {
        return null;
    }

    /**
     * 适合处理同步请求，跳转到相关页面
     * @return
     */
    @PostMapping("register4PC")
    public String register4PC(TUser user) {
        return null;
    }

    @GetMapping("activate")
    public ResultBean activate(String token) {
        return null;
    }
}
