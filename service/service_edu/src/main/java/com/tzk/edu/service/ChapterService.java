package com.tzk.edu.service;

import com.tzk.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tzk.edu.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zkTang
 * @since 2022-07-19
 */
public interface ChapterService extends IService<Chapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);
}
