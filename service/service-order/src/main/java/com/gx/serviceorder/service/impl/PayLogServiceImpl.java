package com.gx.serviceorder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.gx.servicebase.config.exception.GuliException;
import com.gx.serviceorder.entity.Order;
import com.gx.serviceorder.entity.PayLog;
import com.gx.serviceorder.mapper.PayLogMapper;
import com.gx.serviceorder.service.OrderService;
import com.gx.serviceorder.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gx.serviceorder.utils.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author FL
 * @since 2022-06-25
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {
    @Autowired
    private OrderService orderService;

    /**
     * 生成二维码
     *
     * @param orderNo
     * @return
     */
    @Override
    public Map<String, Object> createNative(String orderNo) {
        try {
            //根据订单号获取订单信息
            QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("order_no", orderNo);
            Order order = orderService.getOne(queryWrapper);//订单信息
            Map<String, String> map = new HashMap<>();
            //1、设置支付参数
            map.put("appid", "wx74862e0dfcf69954");
            map.put("mch_id", "1558950191");//商户号
            map.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串
            map.put("body", order.getCourseTitle());//订单内容
            map.put("out_trade_no", orderNo);//流水号
                                            //一定要按照这个写法
            map.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue() + "");//总金额
            map.put("spbill_create_ip", "127.0.0.1");//网站域名
            map.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify");//通知微信接口
            map.put("trade_type", "NATIVE");//贸易类型
            //2、HttpClient来根据URL访问第三方接口并且传递参数
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            //client设置参数
            client.setXmlParam(WXPayUtil.generateSignedXml(map, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);//解析xml
            //4、封装返回结果集
            Map<String, Object> result = new HashMap<>();
            result.put("out_trade_no", order.getOrderNo());//流水号
            result.put("course_id", order.getCourseId());//课程ID
            result.put("total_fee", order.getTotalFee());//总金额
            result.put("result_code", resultMap.get("result_code"));//返回二维码操作状态码
            result.put("code_url", resultMap.get("code_url"));//二维码地址
            return result;
        } catch (Exception e) {
            throw new GuliException(20001, "生成二维码失败");
        }
    }

    /**
     * 查询支付状态
     *
     * @param orderNo
     * @return
     */
    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        //1、封装参数
        try {
            Map<String, String> paramMap = new HashMap<>(10);
            paramMap.put("appid", "wx74862e0dfcf69954");
            paramMap.put("mch_id", "1558950191");
            paramMap.put("out_trade_no", orderNo);//流水号
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串
            //2、设置请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(paramMap, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);//解析xml
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 修改订单状态
     *
     * @param map
     */
    @Override
    public void updateOrderStatus(Map<String, String> map) {
        //根据流水号获取到订单号
        String orderNo = map.get("out_trade_no");
        //根据订单号查询订单信息
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(queryWrapper);
        if (order.getStatus() == 1) {//等于1的说明该订单支付
            return;
        }
        order.setStatus(1);
        orderService.updateById(order);
        //修改成功后,记录支付日志
        PayLog payLog = new PayLog();
        payLog.setOrderNo(order.getOrderNo());//支付订单号
        payLog.setPayTime(new Date());//支付时间
        payLog.setPayType(1);//支付类型
        payLog.setTotalFee(order.getTotalFee());//总金额
        payLog.setTradeState(map.get("trade_state"));//支付状态
        payLog.setTransactionId(map.get("transaction_id"));//交易流水号
        payLog.setAttr(JSONObject.toJSONString(map));//其他属性
        baseMapper.insert(payLog);//插入到支付日志表中
    }
}
