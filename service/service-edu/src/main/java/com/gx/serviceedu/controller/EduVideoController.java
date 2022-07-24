package com.gx.serviceedu.controller;

import com.gx.commonutils.ResultEntity;
import com.gx.serviceedu.client.VodClient;
import com.gx.serviceedu.entity.EduCourse;
import com.gx.serviceedu.entity.EduVideo;
import com.gx.serviceedu.entity.vo.video.VideoInfoFormVO;
import com.gx.serviceedu.service.EduVideoService;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author FL
 * @since 2022-06-18
 */
@RestController
@RequestMapping("/eduservice/video")
@Api("课程视频")
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private VodClient vodClient;

    @PostMapping("/addVideo")
    public ResultEntity addVideo(@RequestBody VideoInfoFormVO videoInfoFormVO) {
        //保存成功
        EduVideo eduVideo = new EduVideo();
        BeanUtils.copyProperties(videoInfoFormVO, eduVideo);
        if (videoInfoFormVO.getFree()) {
            eduVideo.setIsFree(1);
        } else {
            eduVideo.setIsFree(0);
        }
        eduVideoService.save(eduVideo);
        return ResultEntity.ok();
    }

    @GetMapping("{id}")
    public ResultEntity getVideo(@PathVariable("id") String id) {
        EduVideo eduVideo = eduVideoService.getById(id);
        VideoInfoFormVO videoInfoFormVO = new VideoInfoFormVO();
        BeanUtils.copyProperties(eduVideo, videoInfoFormVO);
        Integer isFree = eduVideo.getIsFree();
        if (isFree == 1) {
            videoInfoFormVO.setFree(true);
        } else {
            videoInfoFormVO.setFree(false);
        }
        return ResultEntity.ok().data("item", videoInfoFormVO);
    }

    @PutMapping("/updateVideo")
    public ResultEntity updateVideo(@RequestBody VideoInfoFormVO videoInfoFormVO) {
        EduVideo eduVideo = new EduVideo();
        BeanUtils.copyProperties(videoInfoFormVO, eduVideo);
        if (videoInfoFormVO.getFree()) {
            eduVideo.setIsFree(1);
        } else {
            eduVideo.setIsFree(0);
        }
        eduVideoService.updateById(eduVideo);
        return ResultEntity.ok();
    }

    @DeleteMapping("{id}")
    public ResultEntity removeVideo(@PathVariable("id") String id) {
        //删除课程视频
        EduVideo eduVideo = eduVideoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)) {
            //删除视频
            vodClient.removeVideo(videoSourceId);
        }
        //删除课程小节
        eduVideoService.removeById(id);
        return ResultEntity.ok();
    }
}

