package com.tzk.statistics.schedule;

import com.tzk.statistics.service.DailyService;
import com.tzk.statistics.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

public class ScheduleTask {

    @Autowired
    private DailyService dailyService;
    @Scheduled(cron = "0 0 1 * * ?")
    public void task1(){
        dailyService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(),-1)));
    }
}
