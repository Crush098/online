package com.tzk.vodtest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

public class TestAuth {
    public static void main(String[] args) throws ClientException {
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tHgqLkeGdCZRXKuxBZW", "e9J6VM5VfUd6cxRBmaQfgW1O1QyW86");

        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        request.setVideoId("58815b62d5d94efb858395cbe3e82351");

        response = client.getAcsResponse(request);

        System.out.println(response.getPlayAuth());

    }
}
