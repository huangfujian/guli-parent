package com.gx.oss.controller;

import com.gx.commonutils.ResultEntity;
import com.gx.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
/**
 * @author FL
 * @version 1.0
 * @date 2022/6/17 11:02
 */
@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin //开启跨域访问
public class OssController {
    @Autowired
    private OssService ossService;

    @PostMapping
    public ResultEntity uploadOssFile(MultipartFile file) {
        try {
            String url = ossService.uploadFilePicture(file);
            return ResultEntity.ok().data("url", url);
        } catch (IOException e) {
            return ResultEntity.error().message(e.getMessage());
        }
    }
}
