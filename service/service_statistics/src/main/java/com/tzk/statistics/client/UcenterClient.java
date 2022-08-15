package com.tzk.statistics.client;

import com.tzk.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@SuppressWarnings({"all"})
@Component
@FeignClient("service-ucenter")
public interface UcenterClient {
    @GetMapping("/educenter/member/countRegisterDay/{day}")
    public R countRegisterDay(@PathVariable("day") String day);
}
