package com.gx.serviceorder.client;

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
@FeignClient(name = "service-ucenter",fallback = UcenterMemberDegradeClient.class)
@Component
public interface UcenterMemberClient {
    @ApiOperation("远程调用获取会员信息")
    @GetMapping("/educenter/ucentermember/getUcenterMember/{id}")
    UcenterMemberVO getUcenterMember(@PathVariable("id") String id);
}
