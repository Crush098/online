package com.tzk.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tzk.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author zkTang
 * @since 2022-07-13
 */
public interface TeacherService extends IService<Teacher> {

    Map<String, Object> getTeacherFrontList(Page<Teacher> teacherPage);

}
