package com.md.service.goods.impl;

import com.md.dao.OrdersDao;
import com.md.pojo.business.Express;
import com.md.pojo.business.OrderGoods;
import com.md.pojo.business.Orders;
import com.md.pojo.goods.Goods;
import com.md.service.goods.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersDao ordersDao;

    @Override
    public List<Orders> findAllOrders(Integer pagenum, Integer pagesize) throws Exception {
        Integer a;//当前页的第一个数据的id
        a = pagesize * (pagenum - 1);
        List<Orders> orders = ordersDao.findAllOrders(a,pagesize);
        for (Orders order : orders) {
            Integer order_id = order.getOrder_id();
            List<OrderGoods> orderGoods = ordersDao.findOrderGoodsById(order_id);
            order.setGoods(orderGoods);
        }
        return orders;
    }

    @Override
    public Orders editOrdersById(Orders orders) throws Exception {
        Orders ordersOri = ordersDao.findOrdersById(orders.getOrder_id());

//        下面均为判断传进来的参数是否为空值，是否有改变
//        为空则不变，值有变则则变化
        String is_send = orders.getIs_send();
        if (!ordersOri.getIs_send().equals(is_send) && is_send != null) {
            ordersOri.setIs_send(is_send);
        }
        String order_pay = orders.getOrder_pay();
        if (!ordersOri.getOrder_pay().equals(order_pay) && order_pay != null) {
            ordersOri.setOrder_pay(order_pay);
        }
        Double order_price = orders.getOrder_price();
        if (!ordersOri.getOrder_price().equals(order_price) && order_price != null) {
            ordersOri.setOrder_price(order_price);
        }
        String order_number = orders.getOrder_number();
        if (!ordersOri.getOrder_number().equals(order_number) && order_number != null) {
            ordersOri.setOrder_number(order_number);
        }
        String pay_status = orders.getPay_status();
        if (!ordersOri.getPay_status().equals(pay_status) && pay_status != null) {
            ordersOri.setPay_status(pay_status);
        }
        ordersDao.editOrdersById(ordersOri);
        Integer order_id = orders.getOrder_id();
        List<OrderGoods> orderGoods = ordersDao.findOrderGoodsById(order_id);
        orders.setGoods(orderGoods);
        return ordersOri;
    }

    @Override
    public Orders findOrdersById(Integer id) throws Exception {
        Orders orders = ordersDao.findOrdersById(id);
        List<OrderGoods> orderGoods = ordersDao.findOrderGoodsById(id);
        orders.setGoods(orderGoods);
        return orders;
    }

    @Override
    public List<Express> findExpressById(Integer id) throws Exception {
        List<Express> expressList = ordersDao.findExpressById(id);
        return expressList;
    }

    @Override
    public Integer findTotalOrders() throws Exception {
        Integer total = ordersDao.findTotalOrders();
        return total;
    }
}
