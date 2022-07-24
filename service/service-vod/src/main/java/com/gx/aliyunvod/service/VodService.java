package com.gx.aliyunvod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/20 21:30
 */
public interface VodService {
    String uploadVideo(MultipartFile file);

    void removeVideoById(String id);

    void removeBathVideo(List<String> videoIdList);

}
