package com.tzk.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tzk.commonutils.R;
import com.tzk.edu.entity.Chapter;
import com.tzk.edu.entity.Video;
import com.tzk.edu.entity.chapter.ChapterVo;
import com.tzk.edu.service.ChapterService;
import com.tzk.edu.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class ChapterController {
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private VideoService videoService;
    //kec大纲列表 根据课程id查询
    @GetMapping("/getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId){
        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo",list);
    }

    //添加章节
    @PostMapping("/addChapter")
    public R addChapter(@RequestBody Chapter chapter){
        chapterService.save(chapter);
        return R.ok();
    }
    //根据章节id查询
    @GetMapping("/getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId){
        Chapter chapter = chapterService.getById(chapterId);
        return R.ok().data("chapter",chapter);
    }

    //修改章节
    @PostMapping("/updateChapter")
    public R getChapterInfo(@RequestBody Chapter chapter){
        chapterService.updateById(chapter);
        return R.ok();
    }
    //删除
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId){
        boolean flag = chapterService.deleteChapter(chapterId);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
}
