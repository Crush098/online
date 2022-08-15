package com.tzk.edu.client.impl;

import com.tzk.commonutils.R;
import com.tzk.edu.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;


//删除视频出现错误时调用下面的方法
@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public R removeAlyVideo(String videoSourceId) {
        return R.error().message("删除视频出错了");
    }

    @Override
    public R deleteBatch(List<String> videoIdlist) {
        return R.error().message("删除多个视频出错了");
    }
}
