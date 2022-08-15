package com.tzk.edu.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tzk.commonutils.CourseInfoVoOrder;
import com.tzk.commonutils.JwtUtils;
import com.tzk.commonutils.R;
import com.tzk.edu.client.OrdersClient;
import com.tzk.edu.entity.Course;
import com.tzk.edu.entity.CourseDescription;
import com.tzk.edu.entity.Subject;
import com.tzk.edu.entity.Teacher;
import com.tzk.edu.entity.chapter.ChapterVo;
import com.tzk.edu.entity.vo.CourseInfoVo;
import com.tzk.edu.entity.vo.frontvo.CourseFrontVo;
import com.tzk.edu.service.*;
import org.apache.poi.hssf.record.DVALRecord;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"all"})
@RestController
@CrossOrigin
@RequestMapping("/eduservice/coursefront")
public class CourseFrontController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseDescriptionService courseDescriptionService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private OrdersClient ordersClient;
    @PostMapping("/getCourseFrontList/{page}/{limit}")
    public R getCourseFrontList(@RequestBody(required = false) CourseFrontVo courseFrontVo,
                                @PathVariable long page, @PathVariable long limit){

        Page<Course> pageParam = new Page<Course>(page, limit);
        Map<String, Object> map = courseService.getCourseFrontList(pageParam, courseFrontVo);

        return R.ok().data("map",map);
    }
    @GetMapping("/getCourseInfoById/{courseId}")
    public R getCourseInfoById(@PathVariable String courseId, HttpServletRequest request){
        //得到课程信息
        Course course = courseService.getById(courseId);
        //根据课程id得到课程简介
        CourseDescription courseDescription = courseDescriptionService.getById(courseId);
        //根据课程id和用户id查询当前订单的支付状态
        boolean buyCourse = ordersClient.isBuyCourse(courseId, JwtUtils.getMemberIdByJwtToken(request));
        //根据课程id得到讲师信息
        String teacherId = course.getTeacherId();
        Teacher teacher = teacherService.getById(teacherId);
        //获取章节和小节信息
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);
        //获取所属的二级分类和一级分类
        String subjectId = course.getSubjectId();
        Subject subject = subjectService.getById(subjectId);
        String subjectParentId = course.getSubjectParentId();
        Subject parentSubject = subjectService.getById(subjectParentId);
        return R.ok().data("course",course).data("teacher",teacher)
                        .data("courseDescription",courseDescription)
                .data("chapterVideoList",chapterVideoList)
                .data("subject",subject).data("parentSubject",parentSubject)
                .data("buyCourse",buyCourse);
    }

    //根据课程id查询课程信息
    @GetMapping("/getCourseInfoOrder/{courseId}")
    public CourseInfoVoOrder getCourseInfoOrder(@PathVariable String courseId) {
        CourseInfoVo courseInfo = courseService.getCourseInfo(courseId);
        String teacherId = courseInfo.getTeacherId();
        Teacher teacher = teacherService.getById(teacherId);
        CourseInfoVoOrder courseInfoVoOrder = new CourseInfoVoOrder();
        courseInfoVoOrder.setTeacherName(teacher.getName());
        BeanUtils.copyProperties(courseInfo,courseInfoVoOrder);
        return courseInfoVoOrder;
    }
}
