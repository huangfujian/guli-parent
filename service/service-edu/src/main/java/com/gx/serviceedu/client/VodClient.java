package com.gx.serviceedu.client;

import com.gx.commonutils.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/21 11:32
 */
@Component //注入
//远程服务调用    fallback:兜底方法
@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class)
public interface VodClient {
    @DeleteMapping("/eduvod/video/{id}")
    ResultEntity removeVideo(@PathVariable("id") String id);

    @DeleteMapping("/eduvod/video/removeBathVideo")
    ResultEntity removeBathVideo(@RequestParam("videoIdList") List<String> videoIdList);
}
