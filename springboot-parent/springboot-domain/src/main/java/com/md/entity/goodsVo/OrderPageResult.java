package com.md.entity.goodsVo;

import com.md.entity.other.Meta;
import com.md.pojo.business.Orders;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderPageResult implements Serializable {

    private Integer total;// 总条数
    private String pageNum;// 当前页码
    private List<Orders> goods;// 商品信息

    public static OrderPageResult succ(Integer total,String pageNum,List<Orders> orders){
        OrderPageResult result = new OrderPageResult();
        result.setTotal(total);
        result.setPageNum(pageNum);
        result.setGoods(orders);
        return result;
    }
}
