package com.gx.serviceorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gx.commonutils.JwtUtils;
import com.gx.commonutils.ResultEntity;
import com.gx.serviceorder.entity.Order;
import com.gx.serviceorder.service.OrderService;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author FL
 * @since 2022-06-25
 */
@RequestMapping("/serviceorder/order")
@CrossOrigin //跨域问题
@RestController
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/createOrder/{courseId}")
    @ApiOperation("生成订单")
    public ResultEntity createOrder(@PathVariable("courseId") String courseId, HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);//获取到会员ID
        String orderNo = orderService.saveOrder(courseId, memberId);
        //将订单号返回到前端
        return ResultEntity.ok().data("orderNo", orderNo);
    }

    /**
     * @param orderNo
     * @return
     */
    @GetMapping("/getOrderInfo/{orderNo}")
    @ApiOperation("根据订单号获取到订单信息")
    public ResultEntity getOrderInfo(@PathVariable("orderNo") String orderNo) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(queryWrapper);
        return ResultEntity.ok().data("item", order);
    }

    @GetMapping("/isBuyCourse/{memberId}/{courseId}")
    @ApiOperation("判断是否购买了该课程")
    public Boolean isBuyCourse(@PathVariable("memberId") String memberId,
                               @PathVariable("courseId") String courseId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        queryWrapper.eq("member_id", memberId);
        queryWrapper.eq("status", 1);
        int count = orderService.count(queryWrapper);
        if (count > 0) {
            //大于零说明购买了
            return true;
        } else {
            return false;
        }
    }
}
