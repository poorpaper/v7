package com.poorpaper.vo;

import com.poorpaper.entity.TProduct;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 跟视图层对应的对象 view object
 */
@Data
@AllArgsConstructor
public class CartItemVO implements Serializable {

    private TProduct product;
    private Integer count;
    private Date updateTime;
}
