package com.gx.aliyunvod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.gx.aliyunvod.service.VodService;
import com.gx.aliyunvod.utils.InitVodClientUtils;
import com.gx.aliyunvod.utils.VodProperties;
import com.gx.servicebase.config.exception.GuliException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/20 21:30
 */
@Service
public class VodServiceImpl implements VodService {
    @Autowired
    private VodProperties vodProperties;

    @Override
    public String uploadVideo(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();//获取原始文件名称
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(
                    vodProperties.getAccessKeyId(),
                    vodProperties.getAccessKeySecret(), title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
            String videoId = response.getVideoId();
            if (response.isSuccess()) {
                //上传无论失败还是成功都返回一个ID
                return videoId;
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                return videoId;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void removeVideoById(String id) {
        try {
            DefaultAcsClient client = InitVodClientUtils.initVodClient(vodProperties.getAccessKeyId(), vodProperties.getAccessKeySecret());
            DeleteVideoRequest request = new DeleteVideoRequest();
            //支持传入多个视频ID，多个用逗号分隔
            request.setVideoIds(id);
            //删除视频
            DeleteVideoResponse response = client.getAcsResponse(request);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001, "删除视频失败");
        }
    }

    @Override
    public void removeBathVideo(List<String> videoIdList) {
        try {
            DefaultAcsClient client = InitVodClientUtils.initVodClient(vodProperties.getAccessKeyId(), vodProperties.getAccessKeySecret());
            DeleteVideoRequest request = new DeleteVideoRequest();
            //将集合转换成数组，将使用join方法进行拼接
            String vodIds = StringUtils.join(videoIdList.toArray(), ",");
            //支持传入多个视频ID，多个用逗号分隔
            request.setVideoIds(vodIds);//批量删除
            DeleteVideoResponse acsResponse = client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001, "删除视频失败");
        }
    }
}
