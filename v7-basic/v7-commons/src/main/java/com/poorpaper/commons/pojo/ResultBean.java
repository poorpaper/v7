package com.poorpaper.commons.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 统一定义返回json的接口描述类型
 */

@Data
@AllArgsConstructor
public class ResultBean<T> {

    private String statusCode;
    private T data;
}
