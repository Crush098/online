package com.tzk.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.tzk.commonutils.R;
import com.tzk.servicebase.exceptionhandle.CustomException;
import com.tzk.vod.service.VodService;
import com.tzk.vod.utils.ConstantPropertiesUtils;
import com.tzk.vod.utils.InitObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@SuppressWarnings({"all"})
@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;
    //上传视频到阿里云
    @PostMapping("/uploadAlyVideo")
    public R uploadAlyVideo(MultipartFile file){
        String videoId = vodService.uploadAlyVideo(file);
        return R.ok().data("videoId",videoId);
    }
    //删除阿里云上的视频
    @DeleteMapping("/removeAlyVideo/{videoSourceId}")
    public R removeAlyVideo(@PathVariable("videoSourceId") String videoSourceId){

        try {

            //初始化对象
            DefaultAcsClient client = InitObject.initVodClient(ConstantPropertiesUtils.ACCESS_KEY_ID, ConstantPropertiesUtils.ACCESS_KEY_SECRET);
            //创建删除视频的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //向request中设置id
            request.setVideoIds(videoSourceId);
            //调用初始化对象的方法实现实现删除
            client.getAcsResponse(request);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new CustomException(20001,"删除视频失败");
    }
    //一次性删除多个阿里云的视频
    @DeleteMapping("/delete-batch")
    public R deleteBatch(@RequestBody List<String> videoIdlist){//加上RequestBody注解 不然会有list错误
        vodService.removeMoreAlyVideo(videoIdlist);
        return R.ok();
    }
    //根据视频id获取视频凭证
    @GetMapping("/getPlayAuth/{videoId}")
    public R getPlayAuth(@PathVariable String videoId){
        try {
            DefaultAcsClient client = InitObject.initVodClient(ConstantPropertiesUtils.ACCESS_KEY_ID, ConstantPropertiesUtils.ACCESS_KEY_SECRET);
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(videoId);
            GetVideoPlayAuthResponse acsResponse = client.getAcsResponse(request);
            String playAuth = acsResponse.getPlayAuth();
            return R.ok().data("playAuth",playAuth);
        } catch (ClientException e) {
            throw new CustomException(20001,"视频播放失败");
        }

    }

}
