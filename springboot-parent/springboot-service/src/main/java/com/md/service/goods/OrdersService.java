package com.md.service.goods;

import com.md.pojo.business.Express;
import com.md.pojo.business.Orders;
import com.md.pojo.goods.Goods;

import java.util.List;

public interface OrdersService {
    List<Orders> findAllOrders(Integer pagenum, Integer pagesize) throws Exception;

    Orders editOrdersById(Orders orders) throws Exception;

    Orders findOrdersById(Integer id) throws Exception;

    List<Express> findExpressById(Integer id) throws Exception;
}
