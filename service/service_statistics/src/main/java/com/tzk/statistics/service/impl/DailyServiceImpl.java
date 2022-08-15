package com.tzk.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tzk.commonutils.R;
import com.tzk.statistics.client.UcenterClient;
import com.tzk.statistics.entity.Daily;
import com.tzk.statistics.mapper.DailyMapper;
import com.tzk.statistics.service.DailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author zkTang
 * @since 2022-08-11
 */
@SuppressWarnings({"all"})
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    @Autowired
    private UcenterClient ucenterClient;
    @Override
    public void registerCount(String day) {
        //注册人数一直在变 每次添加数据之前  先把之前的数据删除
        QueryWrapper<Daily> dailyQueryWrapper = new QueryWrapper<>();
        dailyQueryWrapper.eq("data_calculated",day);
        baseMapper.delete(dailyQueryWrapper);
        //通过远程调用得到某天的注册人数
        R registerR = ucenterClient.countRegisterDay(day);
        Integer countRegister = (Integer) registerR.getData().get("countRegister");
        //获取的数据添加到数据库，统计分析表里面
        Daily daily = new Daily();
        daily.setRegisterNum(countRegister);
        daily.setDateCalculated(day);
        daily.setCourseNum(RandomUtils.nextInt(100,200));
        daily.setVideoViewNum(RandomUtils.nextInt(100,200));
        daily.setLoginNum(RandomUtils.nextInt(100,200));
        baseMapper.insert(daily);
    }

    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {

        QueryWrapper<Daily> dailyQueryWrapper = new QueryWrapper<>();
        dailyQueryWrapper.between("date_calculated",begin,end);
        dailyQueryWrapper.select("date_calculated",type);
        List<Daily> dailies = baseMapper.selectList(dailyQueryWrapper);
        List<Integer> dataList = new ArrayList<Integer>();
        List<String> dateList = new ArrayList<String>();
        for (Daily daily : dailies){
            dateList.add(daily.getDateCalculated());
            switch (type) {
                case "register_num":
                    dataList.add(daily.getRegisterNum());
                    break;
                case "login_num":
                    dataList.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    dataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    dataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("dataList", dataList);
        map.put("dateList", dateList);
        return map;
    }

}
