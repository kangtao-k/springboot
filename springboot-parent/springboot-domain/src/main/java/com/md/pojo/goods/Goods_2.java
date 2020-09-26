package com.md.pojo.goods;

import com.md.entity.other.Attrs;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * 商品详情
 */
@Table(name = "sp_goods")
@Data
public class Goods_2 implements Serializable {

    @Id
    private Integer goods_id;// 主键id
    private String goods_name;// 商品名称
    private Integer goods_price;// 商品价格
    private Integer goods_number;// 商品数量
    private Integer goods_weight;// 商品重量
    private Integer cat_id;// 类型id
    private String goods_introduce;// 商品详情介绍
    private String goods_big_logo;// 商品logo大图
    private String goods_small_logo;// 商品logo小图
    private String is_del;// 0：正常，1：删除
    private Integer add_time;// 添加商品时间
    private Integer upd_time;// 修改商品时间
    private Integer delete_time;// 软删除标志字段
    private Integer cat_one_id;// 一级分类id
    private Integer cat_two_id;// 二级分类id
    private Integer cat_three_id;// 三级分类id
    private Integer hot_number;// 热卖数量
    private Integer is_promote;// 是否促销
    private Integer goods_state;// 商品状态 0:未通过 1:审核中 1:已审核

}
