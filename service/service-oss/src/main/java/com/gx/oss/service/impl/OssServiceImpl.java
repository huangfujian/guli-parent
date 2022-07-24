package com.gx.oss.service.impl;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.gx.oss.service.OssService;
import com.gx.oss.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.UUID;
/**
 * @author FL
 * @version 1.0
 * @date 2022/6/17 11:04
 */
@Service
public class OssServiceImpl implements OssService {
    /**
     * 上传照片
     *
     * @param file
     * @return
     * @throws IOException
     */
    @Override
    public String uploadFilePicture(MultipartFile file) throws IOException {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtil.END_POINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        // 填写Bucket名称，例如examplebucket。
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
//        String objectName = "exampledir/exampleobject.txt";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 填写字符串。
//            String content = "Hello OSS";
        String filename = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        filename = uuid + filename;
        //获取当前日期
        String datePath = new DateTime().toString("yyyy/MM/dd");
        filename = datePath + "/" + filename;
        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filename, file.getInputStream());
        // 上传。
        ossClient.putObject(putObjectRequest);
        //https://gx-guli.oss-cn-guangzhou.aliyuncs.com/%E5%A3%81%E7%BA%B8%E7%B2%BE%E9%80%89%20%28230%29.jpg
        String url = "https://" + bucketName + "." + endpoint + "/" + filename;
        ossClient.shutdown();
        return url;
    }
}
