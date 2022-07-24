package com.gx.aliyunvod;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.*;
import com.gx.aliyunvod.utils.InitVodClientUtils;

import java.util.List;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/20 19:09
 */
public class VoidTest {

    public static void main(String[] args) throws ClientException {
      //  testUploadVideo();
        testGetVideoPlayAuth();
    }

    /**
     * 获取视频凭证
     *
     * @param
     */
    public static void testGetVideoPlayAuth() {
        //初始化客户端、请求对象和相应对象
        DefaultAcsClient client = InitVodClientUtils.initVodClient("LTAI5tHzwCybBftRKLJzVAqG", "mzybEb7Gvpnx6hArmJe2LzEA1A6JXV");
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        try {
            //设置请求参数
            request.setVideoId("ad7da49583714d37ae96f3cd66fd3d87");
            //获取请求响应
            response = client.getAcsResponse(request);
            //输出请求结果
            //播放凭证
            String playAuth = response.getPlayAuth();
            System.out.print("PlayAuth = " + playAuth + "\n");
            //videoMeta信息
            String title = response.getVideoMeta().getTitle();
            System.out.print("VideoMeta.Title = " + title + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //获取请求ID
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }

    /**
     * 获取视频播放地址
     */
    public static void testGetPlayInfo() throws ClientException {
        //初始化客户端客户端、请求对象和响应对象
        DefaultAcsClient client = InitVodClientUtils.initVodClient("LTAI5tHzwCybBftRKLJzVAqG", "mzybEb7Gvpnx6hArmJe2LzEA1A6JXV");
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        //设置请求参数
        //注意：这里只能获取非加密视频的播放地址
        request.setVideoId("453d3ee22760429dba179bcb0c6d4ca9");
        //获取请求响应
        response = client.getAcsResponse(request);
        //输出请求结果
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.println("PlayInfo.PlayURL =" + playInfo.getPlayURL());
        }
        //Base信息
        System.out.println("VideoBase.Title =" + response.getVideoBase().getTitle());
        //请求ID
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }

    /**
     * 本地上传
     */
    public static void testUploadVideo() {
        String title = "3-How Does Project Submission Work - upload by sdk";//视频标题
        String fileName = "D:\\6 - What If I Want to Move Faster.mp4";//视频路径
        UploadVideoRequest request = new UploadVideoRequest("LTAI5tHzwCybBftRKLJzVAqG", "mzybEb7Gvpnx6hArmJe2LzEA1A6JXV", title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }
}
