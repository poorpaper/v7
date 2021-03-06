package com.poorpaper.commons.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一定义返回json的接口描述类型
 */

@Data
@AllArgsConstructor
public class ResultBean<T> implements Serializable {

    private String statusCode;
    private T data;
}
