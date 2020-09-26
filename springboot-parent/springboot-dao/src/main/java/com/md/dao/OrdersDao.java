package com.md.dao;

import com.md.pojo.business.Express;
import com.md.pojo.business.OrderGoods;
import com.md.pojo.business.Orders;
import com.md.pojo.goods.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface OrdersDao {

    @Select("select * from sp_order limit #{a},#{pageSize}")
    List<Orders> findAllOrders(@Param("a") Integer a, @Param("pageSize") Integer pageSize);

    @Select("select * from sp_order where order_id=#{order_id}")
    Orders findOrdersById(@Param("order_id") Integer order_id);

    @Update("update sp_order set is_send=#{ordersOri.is_send},order_pay=#{ordersOri.order_pay},order_price=#{ordersOri.order_price}," +
            "order_number=#{ordersOri.order_number},pay_status=#{ordersOri.pay_status} where order_id=#{ordersOri.order_id}")
    void editOrdersById(@Param("ordersOri") Orders ordersOri);

    @Select("select * from sp_order_goods where order_id=#{id}")
    List<OrderGoods> findOrderGoodsById(@Param("id") Integer id);

    @Select("select * from sp_express where order_id=#{id}")
    List<Express> findExpressById(@Param("id") Integer id);

    @Select("select count(*) from sp_order")
    Integer findTotalOrders();

}
