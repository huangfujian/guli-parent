package com.gx.serviceedu.client;

import com.gx.servicebase.config.exception.GuliException;
import org.springframework.stereotype.Component;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/26 8:45
 */
@Component
public class OrderClientIDegradeFeignClient implements OrderClient {
    @Override
    public Boolean isBuyCourse(String memberId, String courseId) {
        throw new GuliException(20001, "系统异常");
    }
}
