package com.tzk.order.client;

import com.tzk.commonutils.MemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
@SuppressWarnings({"all"})
@Component
@FeignClient("service-ucenter")
public interface UcenterClient {
    //根据token字符串获取用户信息
    @PostMapping("/educenter/member/getInfoMemberOrder/{id}")
    public MemberOrder getInfoMemberOrder(@PathVariable("id") String id);
}
