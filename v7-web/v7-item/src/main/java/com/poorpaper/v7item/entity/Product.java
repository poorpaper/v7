package com.poorpaper.v7item.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Product {
    private String name;
    private Long price;
    private Date createTime;
}
