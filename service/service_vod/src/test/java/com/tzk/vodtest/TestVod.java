package com.tzk.vodtest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;

import java.util.List;

public class TestVod {
    public static void main(String[] args) throws ClientException {

        //根据视频id获取视频播放地址

        //创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tHgqLkeGdCZRXKuxBZW", "e9J6VM5VfUd6cxRBmaQfgW1O1QyW86");
        //创建获取视频地址的request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        //向request对象里面设置视频id
        request.setVideoId("58815b62d5d94efb858395cbe3e82351");
        //调用初始化对象里面的方法，传递request，获取数据
        response = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();

        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList){
            System.out.println(playInfo.getPlayURL()); //得到视频播放地址
        }
        //Base信息
        System.out.println(response.getVideoBase().getTitle());
    }
}
