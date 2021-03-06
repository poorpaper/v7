package com.poorpaper.v7order.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlipayBizContent {
    // 平台跟支付宝签约的商品类型，比如快捷支付
    private String product_code = "FAST_INSTANT_TRADE_PAY";
    // 订单编号
    private String out_trade_no;
    // 订单总金额
    private String total_amount;
    private String subject;
    private String body;
}
