package com.gx.serviceedu.client;
import com.gx.commonutils.ResultEntity;
import com.gx.serviceedu.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;
/**
 * @author FL
 * @version 1.0
 * @date 2022/6/21 16:29
 */
@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public ResultEntity removeVideo(String id) {
        return ResultEntity.error().message("删除视频错误");
    }

    @Override
    public ResultEntity removeBathVideo(List<String> videoIdList) {
        return ResultEntity.error().message("批量删除视频错误");
    }
}
