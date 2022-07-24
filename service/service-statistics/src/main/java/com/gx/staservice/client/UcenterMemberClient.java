package com.gx.staservice.client;

import com.gx.commonutils.ResultEntity;
import com.gx.commonutils.vo.UcenterMemberVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/25 11:07
 */
@FeignClient(name = "service-ucenter")
@Component
public interface UcenterMemberClient {
    @ApiOperation("统计某一天注册的人数")
    @GetMapping("/educenter/ucentermember/registerCount/{day}")
    ResultEntity registerCount(@PathVariable("day") String day);
}
