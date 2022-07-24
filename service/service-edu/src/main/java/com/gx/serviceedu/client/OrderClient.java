package com.gx.serviceedu.client;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/26 8:44
 */
@FeignClient(name = "service-order",fallback = OrderClientIDegradeFeignClient.class)
@Component
public interface OrderClient {

    @GetMapping("/serviceorder/order/isBuyCourse/{memberId}/{courseId}")
    @ApiOperation("判断是否购买了该课程")
    Boolean isBuyCourse(@PathVariable("memberId") String memberId,
                        @PathVariable("courseId") String courseId);
}
