package com.tzk.edu.service;

import com.tzk.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tzk.edu.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author zkTang
 * @since 2022-07-18
 */
public interface SubjectService extends IService<Subject> {
    //添加课程
    void saveSubject(MultipartFile file,SubjectService subjectService);

    List<OneSubject> getAllOneTwoSubject();
}
