package com.poorpaper.v7cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.poorpaper.api.ICartService;
import com.poorpaper.api.IUserService;
import com.poorpaper.commons.pojo.ResultBean;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping("cart")
public class CartController {

    @Reference
    private ICartService cartService;

    @Reference
    private IUserService userService;

    @GetMapping("add/{productId}/{count}")
    public ResultBean add(@PathVariable("productId") Long productId,
                          @PathVariable("count") Integer count,
                          @CookieValue(name = "cart_token", required = false) String cartToken,
//                          @CookieValue(name = "user_token", required = false) String userToken,
                          HttpServletRequest request,
                          HttpServletResponse response) {

        // 由我们传递一个合适的token
        // 未登录，传递cartToken
        // 已登录，传递当前登录用户的唯一标识
        // 使用checkIsLogin判断是否登录

        // 方式一: http://localhost:9095/sso/checkIsLogin  (HttpClient)
        // 方式二: 调用service
        String userToken = (String) request.getAttribute("user");
        if (userToken != null) {
            // 说明已登录
            return cartService.add(userToken, productId, count);
        }
        if (cartToken == null) {
            cartToken = UUID.randomUUID().toString();
        }
        // 把cookie写到客户端
        refreshCookie(cartToken, response);
        return cartService.add(cartToken, productId, count);

//        String token = "";
//        ResultBean resultBean = userService.checkIsLogin(userToken);
//        if ("200".equals(resultBean.getStatusCode())) {
//            token = resultBean.getData().toString();
//        } else {
//            if (cartToken == null) {
//                cartToken = UUID.randomUUID().toString();
//            }
//            token = cartToken;
//            // 把cookie写到客户端
//            refreshCookie(cartToken, response);
//        }
//
//        return cartService.add(token, productId, count);
    }

    @GetMapping("query")
    public ResultBean query(@CookieValue(name = "cart_token", required = false) String cartToken,
                            HttpServletRequest request,
                            HttpServletResponse response) {
        String userToken = (String) request.getAttribute("user");

        System.out.println("*********************");
        System.out.println(userToken);

        if (userToken != null) {
            // 说明已登录
            return cartService.query(userToken);
        }
//        if (cartToken == null) {
//            cartToken = UUID.randomUUID().toString();
//        }

        if (cartToken != null) {
            ResultBean resultBean = cartService.query(cartToken);
            refreshCookie(cartToken, response);
            return resultBean;
        }
        return new ResultBean("404", null);
    }

    @GetMapping("delete/{productId}")
    public ResultBean delete(@PathVariable("productId") Long productId,
                             @CookieValue(name = "cart_token",required = false) String cartToken,
                             HttpServletRequest request,
                             HttpServletResponse response){

        String userToken = (String) request.getAttribute("user");
        if(userToken != null){
            //说明已登录
            return cartService.del(userToken,productId);
        }

        if(cartToken != null){
            ResultBean resultBean = cartService.del(cartToken, productId);
            refreshCookie(cartToken,response);
            return resultBean;
        }
        return new ResultBean("404",false);
    }

    @GetMapping("update/{productId}/{count}")
    public ResultBean update(@PathVariable("productId") Long productId,
                             @PathVariable("count") Integer count,
                             @CookieValue(name = "cart_token",required = false) String cartToken,
                             HttpServletRequest request,
                             HttpServletResponse response){

        String userToken = (String) request.getAttribute("user");
        if(userToken != null){
            //说明已登录
            return cartService.update(userToken,productId,count);
        }

        if(cartToken != null){
            ResultBean resultBean = cartService.update(cartToken, productId, count);
            refreshCookie(cartToken,response);
            return resultBean;
        }
        return new ResultBean("404",false);
    }

    private void refreshCookie(String cartToken, HttpServletResponse response) {
        Cookie cookie = new Cookie("cart_token", cartToken);
        cookie.setPath("/");
        cookie.setDomain("wpz.com");
        cookie.setMaxAge(15 * 24 * 60 * 60);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }
}
