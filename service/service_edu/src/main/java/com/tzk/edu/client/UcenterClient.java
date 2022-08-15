package com.tzk.edu.client;

import com.tzk.edu.client.impl.VodFileDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@FeignClient(name = "service-ucenter")
@Component
public interface UcenterClient {
}
