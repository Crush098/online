package com.tzk.edu.mapper;

import com.tzk.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tzk.edu.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author zkTang
 * @since 2022-07-19
 */
public interface CourseMapper extends BaseMapper<Course> {

    public CoursePublishVo getPublishCourseInfo(String courseId);

}
