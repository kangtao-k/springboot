package com.md.controller;

import com.md.entity.other.Meta;
import com.md.entity.goodsVo.OrderPageResult;
import com.md.entity.goodsVo.Result;
import com.md.pojo.business.Express;
import com.md.pojo.business.Orders;
import com.md.service.goods.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrdersController {

    @Autowired
    private OrdersService ordersService;
    private Meta meta = Meta.succ("成功");

    /**
     * 订单数据列表
     * @param pagenum
     * @param pagesize
     * @return
     * @throws Exception
     */
    @GetMapping("/orders")
    public OrderPageResult findAllOrders(@RequestParam Integer pagenum, @RequestParam Integer pagesize) throws Exception{
        List<Orders> orders = ordersService.findAllOrders(pagenum,pagesize);
        OrderPageResult result = OrderPageResult.succ(pagenum,pagesize,orders,meta);
        return result;
    }

    /**
     * 修改订单状态（增量更新）
     * @param id
     * @param is_send
     * @param order_pay
     * @param order_price
     * @param order_number
     * @return
     * @throws Exception
     */
    @PutMapping("/orders/{id}")
    public Result editOrdersById(@PathVariable Integer id,@RequestParam String is_send,
                                 @RequestParam String order_pay,@RequestParam Double order_price,
                                 @RequestParam String order_number) throws Exception{
        Orders orders = new Orders();
        orders.setOrder_id(id);
        orders.setIs_send(is_send);
        orders.setOrder_pay(order_pay);
        orders.setOrder_price(order_price);
        orders.setOrder_number(order_number);
        Orders ordersById = ordersService.editOrdersById(orders);
        Result result = Result.succ(meta, ordersById);
        return result;
    }

    /**
     * 查看订单详情
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/orders/{id}")
    public Result findOrdersById(@PathVariable Integer id) throws Exception{
        Orders orders = ordersService.findOrdersById(id);
        Result result = Result.succ(meta, orders);
        return result;
    }

    /**
     * 查看物流信息
     * @param id 订单id（order_id）
     * @return
     * @throws Exception
     */
    @GetMapping("/kuaidi/{id}")
    public Result findExpressById(@PathVariable Integer id) throws Exception{
        List<Express> expressList = ordersService.findExpressById(id);
        Result result = Result.succ(meta, expressList);
        return result;
    }
}
