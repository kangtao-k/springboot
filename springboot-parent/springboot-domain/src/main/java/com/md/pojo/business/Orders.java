package com.md.pojo.business;

import com.md.pojo.goods.Goods;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Table(name = "sp_order")
@Data
public class Orders implements Serializable {

    @Id
    private Integer order_id;// 主键id
    private Integer user_id;// 下订单会员id
    private String order_number;// 订单编号
    private Double order_price;// 订单总金额
    private String order_pay;// 支付方式  0未支付 1支付宝  2微信  3银行卡
    private String is_send;// 订单是否已经发货
    private String trade_no;// 支付宝交易流水号码
    private String order_fapiao_title;// 发票抬头 个人 公司
    private String order_fapiao_company;// 公司名称
    private String order_fapiao_content;// 发票内容
    private String consignee_addr;// 收货人地址
    private String pay_status;// 订单状态： 0未付款、1已付款
    private Integer create_time;// 记录生成时间
    private Integer update_time;// 记录修改时间
    private List<OrderGoods> goods;// 商品
}
