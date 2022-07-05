package com.poorpaper.v7order.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poorpaper.v7order.pojo.AlipayBizContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller@RequestMapping("pay")
public class PayController {

    @Autowired
    private AlipayClient alipayClient;

    @RequestMapping("generate")
    public void generatePay(String orderNo,
                            HttpServletResponse response) throws IOException {
        // 1.创建一个AlipayClient对象
//        AlipayClient alipayClient = new DefaultAlipayClient(
//                "https://openapi.alipaydev.com/gateway.do",
//                "2021000121614288",
//                "privateKey",
//                "JSON",
//                "utf-8",
//                "alipayPublicKey",
//                "RSA2"
//        );
        // 2.创建交易请求对象，用于封装业务相关的参数
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setReturnUrl("https://www.baidu.com");  // TODO 替换成我们的地址
        request.setNotifyUrl("http://vhjwyf.natappfree.cc/pay/notifyPayResult");  // TODO 替换成我们的地址
        // 设置业务相关的参数
        //关键参数：out_trade_no 订单编号
        //total_amount 订单总金额
        AlipayBizContent bizContent = new AlipayBizContent(
                "FAST_INSTANT_TRADE_PAY", orderNo, "7777", "FD", "精致");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(bizContent);
        request.setBizContent(json);
//        request.setBizContent("{" +
//                "    \"out_trade_no\":\"" + orderNo + "\"," +
//                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
//                "    \"total_amount\":9888," +
//                "    \"subject\":\"IphoneX 512G\"," +
//                "    \"body\":\"IphoneX 512G\"" +
//                "  }"); //填充业务参数
        // 3.通过client发送请求
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(form);
        response.getWriter().flush();
        response.getWriter().close();
    }

    // 异步回调的接口
    // 说明：因为此时我们是在内网做实验，所以需要内网穿透工具
    @PostMapping("notifyPayResult")
    @ResponseBody
    public void notifyPayResult(HttpServletRequest request,
                                HttpServletResponse response) throws AlipayApiException, IOException {

        System.out.println("支付宝异步回调我们了！！！");

        // 1.将异步通知中收到的所有参数都存放到map中
        Map<String, String> paramsMap = new HashMap<>();
        // 所有的请求参数都会封装到request对象中
        Map<String, String[]> sourceMap = request.getParameterMap();
        // sourceMap -> paramsMap
        // value不同，String[] -> String
        Set<Map.Entry<String, String[]>> entries = sourceMap.entrySet();
        for (Map.Entry<String, String[]> entry : entries) {
            String[] values = entry.getValue();
            StringBuilder value = new StringBuilder();
            for (int i = 0; i < values.length - 1; i++) {
                value.append(values[i] + ",");
            }
            value.append(values[values.length - 1]);
            // 将结果赋值给paramsMap
            paramsMap.put(entry.getKey(), value.toString());
        }

        // 2.采用AlipaySignature签名工具类，实现验签
        boolean signVerified = AlipaySignature.rsaCheckV1(
                paramsMap,
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjGO0W5GL3F82wEijzZ3rzDHRS+6oj6djYuc9Xd0sBBJQtrnUv3mihUkKwms94bcOYjK8ivkPesQ9+kmNQB918mfk+LzOQwGJa19eKPPhydM9a7jKN6fJ5Of1d2D6BN4YlldpD01QJFc20zC3iqqrlbbQoJ6I88vGKvXCZ0Ny8IgeiMogHStug/L0EsqsQPWTPsdO2lxzvjbAcKosnOt0mUzvmWWqz9hDv7iX82JrMDK0U8d5rTyrKRuo9cS/jzTnhYxcTEGzDU4gF+W/AhhLt7MAvYY6qvEidsPJhB6MdozsqbcwNE4mA9CMax1fhwFZCHW1OOo0Pm9oy5aAllq6EwIDAQAB",
                "utf-8",
                "RSA2"
        );
        if (signVerified) {
            // TODO 验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success
            // TODO 并继续商户自身业务处理，校验失败返回failure
            System.out.println("验签成功！表明是支付宝发送过来的请求");
            // 还要核对业务参数
            String trade_status = request.getParameter("trade_status");
            // 判断支付状态是否为成功
            if("TRADE_SUCCESS".equals(trade_status)) {
                // 跟订单相关的业务参数
                String out_trade_no = request.getParameter("out_trade_no");
                String total_amount = request.getParameter("total_amount");
                String receipt_amount = request.getParameter("receipt_amount");
                // 需要跟我们的订单数据作比较
                // 如果匹配成功，更新订单的状态为"已支付"

                // 相关的支付流水的数据+上面的业务参数
                String trade_no = request.getParameter("trade_no");
                String app_id = request.getParameter("app_id");
                String buyer_id = request.getParameter("buyer_id");
                String seller_id = request.getParameter("seller_id");

                // 记录支付流水（表），状态为"未对账"

                // 搞定之后，给支付宝回馈success
                response.getWriter().write("success");
                response.getWriter().flush();
                response.getWriter().close();
            }
        } else {
            // TODO 验签失败则记录异常日志，并在response中返回failure
            System.out.println("验签失败，不是支付宝发送过来的");
            response.getWriter().write("failure");
            response.getWriter().flush();
            response.getWriter().close();
        }
    }
}
