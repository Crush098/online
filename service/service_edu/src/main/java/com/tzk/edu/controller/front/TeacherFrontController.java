package com.tzk.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tzk.commonutils.R;
import com.tzk.edu.entity.Course;
import com.tzk.edu.entity.Teacher;
import com.tzk.edu.service.CourseService;
import com.tzk.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@SuppressWarnings({"all"})
@RestController
@CrossOrigin
@RequestMapping("/eduservice/teacherfront")

public class TeacherFrontController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseService courseService;
    @PostMapping("/getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable long page,@PathVariable long limit){
        Page<Teacher> teacherPage = new Page<>(page,limit);
        Map<String,Object> map = teacherService.getTeacherFrontList(teacherPage);
        return R.ok().data("map",map);
    }
    @GetMapping("/getFrontTeacherById/{teacherId}")
    public R getTeacher(@PathVariable String teacherId){
        Teacher teacher = teacherService.getById(teacherId);

        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("teacher_id",teacherId);
        List<Course> courseList = courseService.list(courseQueryWrapper);
        return R.ok().data("teacher",teacher).data("courseList",courseList);
    }
}
