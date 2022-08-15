package com.tzk.edu.service.impl;

import com.tzk.edu.entity.Video;
import com.tzk.edu.mapper.VideoMapper;
import com.tzk.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author zkTang
 * @since 2022-07-19
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

}
