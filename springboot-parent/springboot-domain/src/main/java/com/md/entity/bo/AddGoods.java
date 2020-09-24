package com.md.entity.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

@Data
public class AddGoods implements Serializable {
    private Integer goods_id;// 商品id
    private String goods_name;// 商品名称
    private String goods_cat;// 以','分割的分类列表
    private Integer goods_price;// 价格
    private Integer goods_number;// 数量
    private Integer goods_weight;// 重量
    private String goods_introduce;// 介绍
    private ArrayList<HashMap<String,String>> pics;// 上传的图片临时路径（对象）
    private ArrayList<HashMap<String,String>> attrs;// 商品的参数

}
