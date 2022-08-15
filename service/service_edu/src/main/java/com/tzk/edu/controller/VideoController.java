package com.tzk.edu.controller;


import com.tzk.commonutils.R;
import com.tzk.edu.client.VodClient;
import com.tzk.edu.entity.Video;
import com.tzk.edu.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author zkTang
 * @since 2022-07-19
 */
@SuppressWarnings({"all"})
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class VideoController {
    @Autowired
    private VideoService videoService;

    @Autowired
    private VodClient vodClient;

    //添加小节
    @PostMapping("/addVideo")
    public R addVideo(@RequestBody Video video){
        videoService.save(video);
        return R.ok();
    }
    //根据小节id查询
    @GetMapping("/getVideoInfo/{videoId}")
    public R getChapterInfo(@PathVariable String videoId){
        Video video = videoService.getById(videoId);
        return R.ok().data("video",video);
    }
    //删除小节
    //后续完善 删除小节的同时需要删除里面的视频
    @DeleteMapping("/{id}") //小节id
    public R deleteVideo(@PathVariable String id){
        //根据小节id得到视频id
        Video video = videoService.getById(id);
        String videoSourceId = video.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)){
            vodClient.removeAlyVideo(videoSourceId);
        }
        videoService.removeById(id);
        return R.ok();
    }
    //修改小节
    @PostMapping("/updateVideo")
    public R updateVideo(@RequestBody Video video){

        videoService.updateById(video);
        return R.ok();
    }
}

