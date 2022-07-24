package com.gx.msmservice.service;

import java.util.Map;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/22 16:57
 */
public interface MsmService {

    boolean sendShortMessage(String phone, Map<String, Object> code);
}
