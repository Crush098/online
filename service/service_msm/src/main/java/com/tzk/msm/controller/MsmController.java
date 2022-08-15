package com.tzk.msm.controller;


import com.tzk.commonutils.R;
import com.tzk.msm.service.MsmService;
import com.tzk.msm.utils.RandomUtil;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@SuppressWarnings({"all"})
@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping("/sendcode/{phone}")
    public R sendAuthcode(@PathVariable String phone){

        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)){
            return R.ok().data("phone",phone).data("code",code);
        }
        code = RandomUtil.getFourBitRandom();
        redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
        System.out.println(redisTemplate.opsForValue().get(phone));
        return R.ok().data("phone",phone).data("code",code);
    }
}
