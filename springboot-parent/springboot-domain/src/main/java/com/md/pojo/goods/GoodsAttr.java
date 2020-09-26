package com.md.pojo.goods;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 商品属性实体类
 */
@Table(name = "sp_goods_attr")
@Data
public class GoodsAttr implements Serializable {

    @Id
    private Integer id;// ID
    private Integer goods_id;// 商品id
    private Integer attr_id;// 属性id
    private String attr_value;// 商品对应属性的值
    private Double add_price;// 该属性需要额外增加的价钱

}
