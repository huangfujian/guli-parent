package com.gx.aliyunvod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.gx.aliyunvod.service.VodService;
import com.gx.aliyunvod.utils.InitVodClientUtils;
import com.gx.aliyunvod.utils.VodProperties;
import com.gx.commonutils.ResultEntity;
import com.gx.servicebase.config.exception.GuliException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/20 21:27
 */
@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin //跨域
public class VodController {
    @Autowired
    private VodService vodService;
    @Autowired
    private VodProperties vodProperties;

    /**
     * 上传视频
     *
     * @return
     */
    @PostMapping("/upload")
    public ResultEntity uploadVideo(@RequestParam("file") MultipartFile file) {
        String videoId = vodService.uploadVideo(file);//上传文件
        return ResultEntity.ok().data("videoId", videoId);
    }

    @DeleteMapping("/{id}")
    public ResultEntity removeVideo(@PathVariable("id") String id) {
        vodService.removeVideoById(id);
        return ResultEntity.ok();
    }

    @DeleteMapping("/removeBathVideo")
    public ResultEntity removeBathVideo(@RequestParam("videoIdList") List<String> videoIdList) {
        vodService.removeBathVideo(videoIdList);
        //删除成功
        return ResultEntity.ok();
    }
    @GetMapping("/getVideoPlayAuth/{videoId}")
    @ApiOperation("获取视频凭证")
    public ResultEntity getVideoPlayAuth(@PathVariable("videoId") String videoId) {
        //初始化vodClient
        DefaultAcsClient client = InitVodClientUtils.initVodClient(vodProperties.getAccessKeyId(), vodProperties.getAccessKeySecret());
        //请求
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(videoId);
        GetVideoPlayAuthResponse response = null;
        //响应
        try {
            response = client.getAcsResponse(request);
        } catch (ClientException e) {
            throw new GuliException(20001, "出现异常");
        }
        //得到凭证
        String playAuth = response.getPlayAuth();
        //返回结果
        return ResultEntity.ok().message("获取凭证成功").data("playAuth", playAuth);
    }
}
