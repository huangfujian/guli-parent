package com.gx.serviceorder.client;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/25 11:09
 */
@FeignClient(name = "service-edu", fallback = CourseClientDegrade.class)
@Component
public interface CourseClient {
    @GetMapping("/eduservice/course/getCourseInfoVo/{courseId}")
    @ApiOperation("远程调用获取课程信息")
    com.gx.commonutils.vo.CourseInfoVO getCourseInfoVo(@PathVariable("courseId") String courseId);
}
