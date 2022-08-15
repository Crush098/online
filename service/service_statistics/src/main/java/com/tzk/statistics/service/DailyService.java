package com.tzk.statistics.service;

import com.tzk.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author zkTang
 * @since 2022-08-11
 */
public interface DailyService extends IService<Daily> {

    void registerCount(String day);

    Map<String, Object> getShowData(String type, String begin, String end);
}
