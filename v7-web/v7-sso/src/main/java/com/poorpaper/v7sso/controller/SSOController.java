package com.poorpaper.v7sso.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.poorpaper.api.IUserService;
import com.poorpaper.commons.constant.MQConstant;
import com.poorpaper.commons.pojo.ResultBean;
import com.poorpaper.entity.TUser;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("sso")
public class SSOController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Reference
    private IUserService userService;

    @PostMapping("checkLogin")
    @ResponseBody
    public ResultBean checkLogin(TUser user,
                                 HttpServletResponse response) {
        ResultBean resultBean = userService.checkLogin(user);
        if ("200".equals(resultBean.getStatusCode())) {
            // TODO 写cookie给客户端，保存凭证
            // 1.获取uuid
            String uuid = (String) resultBean.getData();
            // 2.创建cookie对象
            Cookie cookie = new Cookie("user_token", uuid);
            cookie.setPath("/");
            // 设置cookie的域名为父域名，这样所有子域名都可以访问该cookie，解决cookie的跨域问题
            cookie.setDomain("wpz.com");
            cookie.setHttpOnly(true);
            // 3.写cookie到客户端
            response.addCookie(cookie);
        }
        return resultBean;
    }

    @PostMapping("checkLogin4PC")
    public String checkLogin4PC(TUser user,
                                HttpServletRequest request,
                                HttpServletResponse response,
                                @CookieValue(name = "cart_token", required = false) String cartToken) {
        // 1.用户服务做认证，判断当前的用户账号信息是否正确
        ResultBean resultBean = userService.checkLogin(user);
        // 2.如果正确，则在服务端保存凭证信息
        if ("200".equals(resultBean.getStatusCode())) {
            // TODO 写cookie给客户端，保存凭证
            // 1.获取uuid
//            String uuid = (String) resultBean.getData();
            Map<String, String> datas = (Map<String, String>) resultBean.getData();
            String uuid = datas.get("jwtToken");
            // 2.创建cookie对象
            Cookie cookie = new Cookie("user_token", uuid);
            cookie.setPath("/");
            cookie.setDomain("wpz.com");
            cookie.setHttpOnly(true);
            // 3.写cookie到客户端
            response.addCookie(cookie);
//            request.getSession().setAttribute("user", user.getUsername());
            // 登陆成功，默认跳转到首页

            // 发送消息，到交换机
            Map<String, String> params = new HashMap<>();
            params.put("noLoginKey", cartToken);
            params.put("loginKey", datas.get("username"));
            rabbitTemplate.convertAndSend(MQConstant.EXCHANGE.SSO_EXCHANGE, "user.login", params);

            return "redirect:http://localhost:9091";
        }
        // 3.否则，跳转回登录页面
        return "index";
    }

    @GetMapping("createSession")
    @ResponseBody
    public String createSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("key", "ok");
        session.setMaxInactiveInterval(30);
        return "ok";
    }

    @GetMapping("noSession")
    @ResponseBody
    public String noSession() {
        return "ok";
    }

    @GetMapping("getSession")
    @ResponseBody
    public String getSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        long lastAccessTime = session.getLastAccessedTime();
        System.out.println("lastAccessedTime->" + lastAccessTime);
        return "ok";
    }

    @GetMapping("logout")
    @ResponseBody
    public ResultBean logout(@CookieValue(name = "user_token", required = false) String token,
                             HttpServletResponse response) {
//        request.getSession().removeAttribute("user");
        if (token != null) {
            Cookie cookie = new Cookie("user_token", token);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setDomain("wpz.com");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        return new ResultBean("200", true);
    }

    @GetMapping("logout4PC")
    @ResponseBody
    public String logout4PC() {
        return null;
    }

//    @GetMapping("checkIsLogin")
//    @ResponseBody
//    public ResultBean checkIsLogin(HttpServletRequest request) {
//        // TODO
//        // 1.获取cookie，获取user_token的值
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null && cookies.length > 0) {
//            // 遍历查询我们需要的那个cookie
//            for (Cookie cookie : cookies) {
//                if ("user_token".equals(cookie.getName())) {
//                    String uuid = cookie.getValue();
//                    // 2.去redis中查询，是否存在该凭证信息
//                    ResultBean resultBean = userService.checkIsLogin(uuid);
//                    return resultBean;
//                }
//            }
//        }
////        Object user = request.getSession().getAttribute("user");
////        if (user != null) {
////            return new ResultBean("200", user);
////        }
//        return new ResultBean("404", null);
//    }

    @GetMapping("checkIsLogin")
    @CrossOrigin(origins = "http://localhost:9091", allowCredentials = "true")
    @ResponseBody
    public ResultBean checkIsLogin(@CookieValue(name = "user_token", required = false) String uuid) {
        // TODO
        // 1.获取cookie，获取user_token的值
        if (uuid != null) {
            ResultBean resultBean = userService.checkIsLogin(uuid);
            return resultBean;
        }
        return new ResultBean("404", null);
    }

    @GetMapping("checkIsLogin4PC")
    @ResponseBody
    public String checkIsLogin4PC() {
        return null;
    }
}
