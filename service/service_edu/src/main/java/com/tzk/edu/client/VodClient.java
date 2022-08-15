package com.tzk.edu.client;

import com.tzk.commonutils.R;
import com.tzk.edu.client.impl.VodFileDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@SuppressWarnings({"all"})
@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {

    @DeleteMapping("/eduvod/video/removeAlyVideo/{videoSourceId}")
    public R removeAlyVideo(@PathVariable("videoSourceId") String videoSourceId);

    //一次性删除多个阿里云的视频
    @DeleteMapping("/eduvod/video/delete-batch")
    public R deleteBatch(List<String> videoIdlist);
}
