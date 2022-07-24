package com.gx.serviceorder.controller;

import com.gx.commonutils.ResultEntity;
import com.gx.serviceorder.service.PayLogService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/25 18:16
 */
@RestController
@RequestMapping("/serviceorder/paylog")
@CrossOrigin
@Api("支付接口")
@Slf4j
public class PayLogController {
    @Autowired
    private PayLogService payLogService;

    /**
     * 根据订单生成二维码
     *
     * @return
     */
    @GetMapping("/createNative/{orderNo}")
    public ResultEntity createNative(@PathVariable("orderNo") String orderNo) {
        Map<String, Object> map = payLogService.createNative(orderNo);
        System.out.println("**********生成的二维码信息:{}"+map.toString());
        return ResultEntity.ok().data(map);
    }

    /**
     * 查询支付状态
     *
     * @param orderNo
     * @return
     */
    @GetMapping("/queryPayStatus/{orderNo}")
    public ResultEntity queryPayStatus(@PathVariable("orderNo") String orderNo) {
        //查看微信那边接口是否支付成功
        //支付成功的话，进行修改订单状态，
        //调用查询接口，
        Map<String, String> map = payLogService.queryPayStatus(orderNo);
        System.out.println("**********查询订单状态map集合:" + map.toString());
        if (map == null) {//出错
            return ResultEntity.error().message("支付出错");
        }
        if (map.get("trade_state").equals("SUCCESS")) {
            //如果成功
            //更改订单状态
            payLogService.updateOrderStatus(map);
            return ResultEntity.ok().message("支付成功");
        }
        return ResultEntity.ok().code(25000).message("支付中");
    }
}
