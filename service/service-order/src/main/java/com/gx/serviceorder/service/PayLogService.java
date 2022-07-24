package com.gx.serviceorder.service;

import com.gx.serviceorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author FL
 * @since 2022-06-25
 */
public interface PayLogService extends IService<PayLog> {
    /**
     * 生成验证码
     * @param orderNo
     * @return
     */
    Map<String, Object> createNative(String orderNo);

    Map<String, String> queryPayStatus(String orderNo);

    void updateOrderStatus(Map<String, String> map);

}
