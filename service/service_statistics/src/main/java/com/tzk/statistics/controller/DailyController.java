package com.tzk.statistics.controller;


import com.tzk.commonutils.R;
import com.tzk.statistics.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author zkTang
 * @since 2022-08-11
 */
@SuppressWarnings({"all"})
@RestController
@RequestMapping("/staservice/daily")
@CrossOrigin
public class DailyController {

    @Autowired
    private DailyService dailyService;

    //查询当天的注册人数
    @PostMapping("/registerCount/{day}")
    public R registerCount(@PathVariable String day){
        dailyService.registerCount(day);
        return R.ok();
    }
    //图表显示 返回两部分数据 日期json数组 数量json数组

    @GetMapping("/showData/{type}/{begin}/{end}")
    public R showData(@PathVariable String type,
                      @PathVariable String begin,
                      @PathVariable String end){
        Map<String,Object> map = dailyService.getShowData(type,begin,end);
        return R.ok().data("showData",map);
    }
}

