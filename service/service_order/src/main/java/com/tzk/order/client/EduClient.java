package com.tzk.order.client;

import com.tzk.commonutils.CourseInfoVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@SuppressWarnings({"all"})
@Component
@FeignClient("service-edu")
public interface EduClient {

    //根据课程id查询课程信息
    @GetMapping("/eduservice/coursefront/getCourseInfoOrder/{courseId}")
    public CourseInfoVoOrder getCourseInfoOrder(@PathVariable("courseId") String courseId);
}
