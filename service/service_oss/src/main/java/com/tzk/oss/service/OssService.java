package com.tzk.oss.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface OssService {
    //上传头像文件到oss
    String uploadFileAvatar(MultipartFile file);
}
