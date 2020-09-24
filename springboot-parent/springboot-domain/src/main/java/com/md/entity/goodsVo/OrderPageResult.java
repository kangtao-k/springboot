package com.md.entity.goodsVo;

import com.md.entity.other.Meta;
import com.md.pojo.business.Orders;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderPageResult implements Serializable {

    private Integer pageNum;// 当前页码
    private Integer pageSize;// 每页显示条数
    private List<Orders> goods;// 商品信息
    private Meta meta;

    public static OrderPageResult succ(Integer pageNum,Integer pageSize,List<Orders> orders,Meta meta){
        OrderPageResult result = new OrderPageResult();
        result.setPageNum(pageNum);
        result.setPageSize(pageSize);
        result.setGoods(orders);
        result.setMeta(meta);
        return result;
    }
}
