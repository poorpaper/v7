package com.poorpaper.v7center.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 富文本框返回的数据的封装
 */
@Data
@AllArgsConstructor
public class WangEditorResultBean {
    private String errno;
    private String[] data;
}
