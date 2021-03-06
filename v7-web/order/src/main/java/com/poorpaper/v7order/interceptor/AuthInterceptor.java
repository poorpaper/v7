package com.poorpaper.v7order.interceptor;

import com.alibaba.dubbo.config.annotation.Reference;
import com.poorpaper.api.IUserService;
import com.poorpaper.commons.pojo.ResultBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Reference
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断当前是否处于登录状态
        // 如果是登录状态，则将当前用户信息保存到request中
        // 登录了才可以放行
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("user_token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    ResultBean resultBean = userService.checkIsLogin(token);
                    if ("200".equals(resultBean.getStatusCode())) {
                        // 说明当前用户已登录
                        request.setAttribute("user", resultBean.getData());
                        return true;
                    }
                }
            }
        }
        // 未登录 不放行，跳转到登录页面
        // 如果需要实现从哪儿来回哪儿去效果，此处需要传递一个url地址
        StringBuffer requestURL = request.getRequestURL();
        response.sendRedirect("http://localhost:9095?referer=" + requestURL.toString());
        return false;
    }
}
