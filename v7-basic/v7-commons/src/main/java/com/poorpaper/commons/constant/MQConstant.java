package com.poorpaper.commons.constant;

public interface MQConstant {

    public static class EXCHANGE {
        public static final String CENTER_PRODUCT_EXCHANGE = "center-product-exchange";
        public static final String SMS_EXCHANGE = "sms-exchange";
        public static final String EMAIL_EXCHANGE = "email-exchange";
    }

    public static class QUEUE {
        public static final String CENTER_PRODUCT_EXCHANGE_SEARCH_QUEUE = "center-product-exchange-search-queue";
        public static final String SMS_EXCHANGE_CODE_QUEUE = "sms-exchange-code-queue";
        public static final String SMS_EXCHANGE_BIRTHDAY_QUEUE = "sms-exchange-birthday-queue";
        public static final String EMAIL_EXCHANGE_CODE_QUEUE = "email-exchange-code-queue";
        public static final String EMAIL_EXCHANGE_BIRTHDAY_QUEUE = "email-exchange-birthday-queue";
    }
}
