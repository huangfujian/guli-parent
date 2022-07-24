package com.gx.serviceorder.service;

import com.gx.serviceorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author FL
 * @since 2022-06-25
 */
public interface OrderService extends IService<Order> {

    String saveOrder(String courseId, String memberId);
}
