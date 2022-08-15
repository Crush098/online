package com.tzk.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tzk.commonutils.R;
import com.tzk.edu.entity.Course;
import com.tzk.edu.entity.Teacher;
import com.tzk.edu.service.CourseService;
import com.tzk.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/eduservice/indexfront")
public class IndexFrontController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseService courseService;
    //查询前8条热门课程、查询前四条讲师记录
    @GetMapping("/index")
    public R index(){
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.orderByDesc("id");
        teacherQueryWrapper.last("limit 4");
        List<Teacher> hotTeacherList= teacherService.list(teacherQueryWrapper);
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.orderByDesc("id");
        courseQueryWrapper.last("limit 8");
        List<Course> famousCourseList = courseService.list(courseQueryWrapper);
        return R.ok().data("hotTeacherList",hotTeacherList).data("famousCourseList",famousCourseList);
    }

}
