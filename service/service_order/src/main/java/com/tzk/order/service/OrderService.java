package com.tzk.order.service;

import com.tzk.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author zkTang
 * @since 2022-08-06
 */
public interface OrderService extends IService<Order> {

    String createOrders(String courseId, String memberId);
}
