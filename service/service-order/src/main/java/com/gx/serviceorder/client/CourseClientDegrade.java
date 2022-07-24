package com.gx.serviceorder.client;
import com.gx.commonutils.vo.CourseInfoVO;
import com.gx.servicebase.config.exception.GuliException;
import org.springframework.stereotype.Component;
/**
 * @author FL
 * @version 1.0
 * @date 2022/6/25 11:12
 */
@Component
public class CourseClientDegrade implements CourseClient {
    @Override
    public CourseInfoVO getCourseInfoVo(String courseId) {
        throw new GuliException(2001, "订单异常");
    }
}
