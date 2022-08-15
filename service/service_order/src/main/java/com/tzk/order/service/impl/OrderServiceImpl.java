package com.tzk.order.service.impl;

import com.tzk.commonutils.CourseInfoVoOrder;
import com.tzk.commonutils.MemberOrder;
import com.tzk.order.client.EduClient;
import com.tzk.order.client.UcenterClient;
import com.tzk.order.entity.Order;
import com.tzk.order.mapper.OrderMapper;
import com.tzk.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tzk.order.utils.OrderNoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author zkTang
 * @since 2022-08-06
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private UcenterClient ucenterClient;
    @Autowired
    private EduClient eduClient;

    @Autowired
    private OrderService orderService;

    @Override
    public String createOrders(String courseId, String memberId) {

        //通过远程调用用户id获取用户信息
        MemberOrder MemberInfoOrder = ucenterClient.getInfoMemberOrder(memberId);

        //通过远程调用课程id获取课程信息
        CourseInfoVoOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);

        //创建order对象  往里面添加相应的数据
        Order order = new Order();
        order.setOrderNo(OrderNoUtils.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberId);
        order.setMobile(MemberInfoOrder.getMobile());
        order.setNickname(MemberInfoOrder.getNickname());
        order.setStatus(0); //支付状态
        order.setPayType(1);//支付类型

        baseMapper.insert(order);
        return order.getOrderNo();
    }
}
