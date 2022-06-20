package com.poorpaper.api.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SMSResponse {

    //{"Message":"OK","RequestId":"4356F613-4A2F-4E92-A196-70862470C3CD","BizId":"123506073525482939^0","Code":"OK"}
//    @JsonProperty("Message")
    private String Message;
//    @JsonProperty("RequestId")
    private String RequestId;
//    @JsonProperty("BizId")
    private String BizId;
//    @JsonProperty("Code")
    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String requestId) {
        RequestId = requestId;
    }

    public String getBizId() {
        return BizId;
    }

    public void setBizId(String bizId) {
        BizId = bizId;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
