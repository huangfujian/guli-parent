package com.gx.serviceorder.client;

import com.gx.commonutils.vo.UcenterMemberVO;
import com.gx.servicebase.config.exception.GuliException;
import org.springframework.stereotype.Component;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/25 11:46
 */
@Component
public class UcenterMemberDegradeClient implements UcenterMemberClient {
    @Override
    public UcenterMemberVO getUcenterMember(String id) {
        throw new GuliException(2001, "订单异常");
    }
}
