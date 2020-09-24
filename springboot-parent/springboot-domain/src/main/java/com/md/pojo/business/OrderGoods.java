package com.md.pojo.business;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "sp_order_goods")
@Data
public class OrderGoods implements Serializable {

    @Id
    private Integer id;// 主键id
    private Integer order_id;// 订单id
    private Integer goods_id;// 商品id
    private Double goods_price;// 商品单价
    private Integer goods_number;// 购买单个商品数量
    private Double goods_total_price;// 商品小计价格
}
