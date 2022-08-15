package com.tzk.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tzk.commonutils.R;
import com.tzk.edu.entity.Course;
import com.tzk.edu.entity.vo.CourseInfoVo;
import com.tzk.edu.entity.vo.CoursePublishVo;
import com.tzk.edu.entity.vo.CourseQuery;
import com.tzk.edu.service.ChapterService;
import com.tzk.edu.service.CourseService;
import com.tzk.edu.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author zkTang
 * @since 2022-07-19
 */
@SuppressWarnings({"all"})
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin //解决跨域问题
@Api(description = "课程信息管理")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private VideoService videoService;


    //添加课程基本信息
    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        //返回添加的课程的id

        String courseId = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",courseId);
    }
    //根据课程id查询课程信息
    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }
    //修改课程信息
    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }
    //根据课程id查询课程的确认信息
    @GetMapping("/getPublishCourseInfo/{courseId}")
    public R getPublishCourseInfo(@PathVariable String courseId){
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(courseId);
        return R.ok().data("coursePublishVo",coursePublishVo);
    }

    //课程最终发布
    //修改课程的发布状态
    @PostMapping("/publishCourse/{courseId}")
    public R publishCourse(@PathVariable String courseId){
        Course course = new Course();
        course.setId(courseId);
        course.setStatus("Normal");
        courseService.updateById(course);
        return R.ok();
    }
    //取消已经发布的课程
    @PostMapping("/cancelPublishCourse/{courseId}")
    public R cancelPublishCourse(@PathVariable String courseId){
        Course course = new Course();
        course.setId(courseId);
        course.setStatus("Draft");
        courseService.updateById(course);
        return R.ok();
    }

    @GetMapping("/getCourseList")
    public R getCourseList(){
        List<Course> courseList = courseService.list(null);
        return R.ok().data("courseList",courseList);
    }
    //分页条件组合查询课程
    @PostMapping("/pageCourseCondition/{current}/{limit}")
    public R pageCoueseCondition(@ApiParam(value = "当前页") @PathVariable long current,
                                  @ApiParam(value = "每页记录数") @PathVariable long limit,
                                  @RequestBody(required = false) CourseQuery courseQuery){
        Page<Course> coursePage = new Page<>(current, limit);
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();
        if (!StringUtils.isEmpty(title)){
            courseQueryWrapper.like("title",title);
        }
        if (!StringUtils.isEmpty(status)){
            courseQueryWrapper.eq("status",status);
        }
        courseService.page(coursePage,courseQueryWrapper);
        long total = coursePage.getTotal();
        List<Course> records = coursePage.getRecords();
        return R.ok().data("total",total).data("records",records);
    }
    //根据id删除课程
    @DeleteMapping("{id}")
    public R deleteCourseById(@PathVariable String id) {
        int i = courseService.deleteCourseById(id);
        if (i == 1){
            return R.ok();
        }else {
            return R.error();
        }
    }
}

