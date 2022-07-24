package com.gx.oss.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/17 11:04
 */
public interface OssService {
    String uploadFilePicture(MultipartFile file) throws IOException;
}
