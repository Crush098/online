package com.tzk.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tzk.edu.entity.Chapter;
import com.tzk.edu.entity.Video;
import com.tzk.edu.entity.chapter.ChapterVo;
import com.tzk.edu.entity.chapter.VideoVo;
import com.tzk.edu.mapper.ChapterMapper;
import com.tzk.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tzk.edu.service.VideoService;
import com.tzk.servicebase.exceptionhandle.CustomException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {
    @Autowired
    private VideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //根据课程id查询出课程的所有章节
        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id",courseId);
        List<Chapter> chapterList = baseMapper.selectList(chapterQueryWrapper);

        //根据课程id查询出课程的所有小节
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id",courseId);
        List<Video> videoList = videoService.list(videoQueryWrapper);


        //封装章节集合
        ArrayList<ChapterVo> chapterVoList = new ArrayList<>();
        for (Chapter chapter : chapterList){
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter,chapterVo);
            chapterVoList.add(chapterVo);
            //封装小节集合
            ArrayList<VideoVo> videoVoList = new ArrayList<>();
            for (Video video : videoList){
                if (video.getChapterId().equals(chapter.getId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video,videoVo);
                    videoVoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVoList);
        }
        return chapterVoList;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        //根据章节id查询小节表 如果有小节数据 就不能直接删除章节(需要先删除小节)
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("chapter_id",chapterId);
        int count = videoService.count(videoQueryWrapper);
        if (count > 0){ //有小节数据则不能进行删除
            throw new CustomException(20001,"章节下面含有小节，不能直接删除");
        }else { //没有小节数据 可以直接删除章节
            int i = baseMapper.deleteById(chapterId);
            return i > 0;
        }
    }
}
