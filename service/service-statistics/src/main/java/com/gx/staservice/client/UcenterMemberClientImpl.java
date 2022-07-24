package com.gx.staservice.client;

import com.gx.commonutils.ResultEntity;
import com.gx.servicebase.config.exception.GuliException;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/27 10:46
 */
public class UcenterMemberClientImpl implements UcenterMemberClient {
    @Override
    public ResultEntity registerCount(String day) {
        throw new GuliException(20001, "数据异常");
    }
}
