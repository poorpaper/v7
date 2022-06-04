package com.poorpaper.vo;

import com.poorpaper.entity.TProduct;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProductVO implements Serializable {
    private TProduct product;
    private String productDesc;
}
