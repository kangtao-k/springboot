package com.md.entity.bo;

import com.md.entity.other.AddAttrs;
import com.md.entity.other.Attrs;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class AddGoods implements Serializable {
    private ArrayList<AddAttrs> attrs;// 商品属性
    private String goods_cat;// 以','分割的分类列表
    private String goods_introduce;// 介绍
    private String goods_name;// 商品名称
    private String goods_number;// 数量
    private String goods_price;// 价格
    private String goods_weight;// 重量
    private ArrayList<HashMap<String,String>> pics;// 上传的图片临时路径（对象）
}
