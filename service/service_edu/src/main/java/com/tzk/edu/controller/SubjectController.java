package com.tzk.edu.controller;


import com.tzk.commonutils.R;
import com.tzk.edu.entity.subject.OneSubject;
import com.tzk.edu.service.SubjectService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author zkTang
 * @since 2022-07-18
 */
@SuppressWarnings({"all"})
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
@Api(description = "课程分类管理")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    //添加课程分类
    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file){
        //上传过来的文件
        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }
    //课程分类列表
    @GetMapping("/getAllSubject")
    public R getAllSubject(){
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }
}

