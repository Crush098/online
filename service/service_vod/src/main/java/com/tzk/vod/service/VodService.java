package com.tzk.vod.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface VodService {
    String uploadAlyVideo(MultipartFile file);
    //一次性删除多个阿里云的视频
    void removeMoreAlyVideo(List<String> videoIdlist);
}
