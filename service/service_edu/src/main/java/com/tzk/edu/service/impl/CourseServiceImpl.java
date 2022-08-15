package com.tzk.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tzk.edu.client.VodClient;
import com.tzk.edu.entity.Chapter;
import com.tzk.edu.entity.Course;
import com.tzk.edu.entity.CourseDescription;
import com.tzk.edu.entity.Video;
import com.tzk.edu.entity.vo.CourseInfoVo;
import com.tzk.edu.entity.vo.CoursePublishVo;
import com.tzk.edu.entity.vo.frontvo.CourseFrontVo;
import com.tzk.edu.mapper.CourseMapper;
import com.tzk.edu.service.ChapterService;
import com.tzk.edu.service.CourseDescriptionService;
import com.tzk.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tzk.edu.service.VideoService;
import com.tzk.servicebase.exceptionhandle.CustomException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zkTang
 * @since 2022-07-19
 */
@SuppressWarnings({"all"})
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseDescriptionService courseDescriptionService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private VideoService videoService;
    @Autowired
    private VodClient vodClient;

    @Override
    @ApiOperation(value = "添加课程基本信息")
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //1 课程表添加课程基本信息
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo,course);
        int insert = baseMapper.insert(course);
        if (insert == 0){
            throw new CustomException(20001,"添加课程失败");
        }

        String courseId = course.getId();
        //2 课程简介表添加课程简介
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescription.setId(courseId);
        courseDescriptionService.save(courseDescription);
        return courseId;
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {

        Course course = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(course,courseInfoVo);
        CourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }
    //修改课程信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo,course);
        int i = baseMapper.updateById(course);
        if (i==0){
            throw new CustomException(20001,"修改课程信息失败");
        }
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(courseDescription);

    }

    @Override
    public CoursePublishVo publishCourseInfo(String courseId) {
        CoursePublishVo coursePublishVo = baseMapper.getPublishCourseInfo(courseId);
        return coursePublishVo;
    }

    @Override
    public int deleteCourseById(String id) { //id: 课程id
        //先删除小节  在删除章节 再删描述 最后删除课程
        //删除小节（所有视频）（根据课程id查询出所属的所有视频id）（数据库删除）
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id",id);
        //查询出点钱课程下的所有视频的video_source_id
        List<Video> videoList = videoService.list(videoQueryWrapper);
        ArrayList<String> videoSourceIds = new ArrayList<>();
        for (Video video : videoList){
            String videoSourceId = video.getVideoSourceId();
            if (videoSourceId != null){
                videoSourceIds.add(videoSourceId);
            }
        }
        //阿里云删除
        if (videoSourceIds.size() > 0){
            vodClient.deleteBatch(videoSourceIds);
        }
        //数据库删除
        videoService.remove(videoQueryWrapper);

        //删除章节
        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id",id);
        chapterService.remove(chapterQueryWrapper);
        //删除描述
        QueryWrapper<CourseDescription> courseDescriptionQueryWrapper = new QueryWrapper<>();
        courseDescriptionQueryWrapper.eq("id",id);
        courseDescriptionService.remove(courseDescriptionQueryWrapper);
        //删除课程
        int i = baseMapper.deleteById(id);
        if (i == 1) {
            return i;
        } else {
            throw new CustomException(20001,"删除出错了");
        }
    }


    //条件查询带分页查询课程
    @Override
    public Map<String, Object> getCourseFrontList(Page<Course> pageParam, CourseFrontVo courseFrontVo) {

        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) {
            queryWrapper.eq("subject_parent_id", courseFrontVo.getSubjectParentId());
        }

        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())) {
            queryWrapper.eq("subject_id", courseFrontVo.getSubjectId());
        }

        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) {
            queryWrapper.orderByDesc("buy_count");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {
            queryWrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {
            queryWrapper.orderByDesc("price");
        }

        baseMapper.selectPage(pageParam, queryWrapper);

        List<Course> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }
}
