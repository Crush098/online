package com.tzk.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tzk.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tzk.edu.entity.vo.CourseInfoVo;
import com.tzk.edu.entity.vo.CoursePublishVo;
import com.tzk.edu.entity.vo.frontvo.CourseFrontVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zkTang
 * @since 2022-07-19
 */
public interface CourseService extends IService<Course> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo publishCourseInfo(String courseId);

    int deleteCourseById(String id);

    Map<String, Object> getCourseFrontList(Page<Course> pageParam, CourseFrontVo courseFrontVo);
}

