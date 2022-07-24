package com.gx.serviceorder.service.impl;

import com.gx.commonutils.vo.CourseInfoVO;
import com.gx.commonutils.vo.UcenterMemberVO;
import com.gx.serviceorder.client.CourseClient;
import com.gx.serviceorder.client.UcenterMemberClient;
import com.gx.serviceorder.entity.Order;
import com.gx.serviceorder.mapper.OrderMapper;
import com.gx.serviceorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gx.serviceorder.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author FL
 * @since 2022-06-25
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private CourseClient courseClient;
    @Autowired
    private UcenterMemberClient ucenterMemberClient;
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    @Override
    public String saveOrder(String courseId, String memberId) {
        CourseInfoVO courseInfoVo = courseClient.getCourseInfoVo(courseId);
        UcenterMemberVO ucenterMember = ucenterMemberClient.getUcenterMember(memberId);
        //创建订单
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());//生成订单号
        order.setCourseCover(courseInfoVo.getCover());
        order.setCourseId(courseInfoVo.getId());
        order.setTeacherName(courseInfoVo.getTeacherName());
        order.setCourseTitle(courseInfoVo.getTitle());
        order.setMemberId(ucenterMember.getId());
        order.setNickname(ucenterMember.getNickname());
        order.setMobile(ucenterMember.getMobile());
        order.setTotalFee(courseInfoVo.getPrice());
        order.setStatus(0);
        order.setPayType(1);//支付类型
        baseMapper.insert(order);
        return order.getOrderNo();//返回订单号
    }
}
