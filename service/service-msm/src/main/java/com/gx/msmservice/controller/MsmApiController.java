package com.gx.msmservice.controller;

import com.gx.commonutils.ResultEntity;
import com.gx.msmservice.service.MsmService;
import com.gx.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/22 16:55
 */
@CrossOrigin
@RestController
@RequestMapping("/edumsm/msm")
public class MsmApiController {
    @Autowired
    private MsmService msmService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/send/{phone}")
    public ResultEntity sendCode(@PathVariable("phone") String phone) {
        String code = RandomUtil.getFourBitRandom();//生成验证码
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        msmService.sendShortMessage(phone, map);
        //存入redis中
        redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
        return ResultEntity.ok();
    }
}