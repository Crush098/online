package com.tzk.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tzk.commonutils.JwtUtils;
import com.tzk.commonutils.R;
import com.tzk.order.entity.Order;
import com.tzk.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author zkTang
 * @since 2022-08-06
 */
@SuppressWarnings({"all"})
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    //生成订单信息
    @PostMapping("/creatOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId,HttpServletRequest request){
        String orderNo = orderService.createOrders(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderNo",orderNo);
    }

    //根据订单id查询订单信息
    @GetMapping("/getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId){
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("order_no",orderId);
        Order order = orderService.getOne(orderQueryWrapper);
        return R.ok().data("order",order);
    }
    //根据课程id和用户id查询当前订单的支付状态
    @GetMapping("/isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId,
                               @PathVariable String memberId) {
        //订单状态是1表示支付成功
        int count = orderService.count(new QueryWrapper<Order>().eq("course_id", courseId).eq("member_id", memberId).eq("status", 1));
        if(count>0) {
            return true;
        } else {
            return false;
        }
    }

}

