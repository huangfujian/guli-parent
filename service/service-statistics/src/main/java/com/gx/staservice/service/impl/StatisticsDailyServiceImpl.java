package com.gx.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gx.commonutils.ResultEntity;
import com.gx.staservice.client.UcenterMemberClient;
import com.gx.staservice.entity.StatisticsDaily;
import com.gx.staservice.mapper.StatisticsDailyMapper;
import com.gx.staservice.service.StatisticsDailyService;
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
 * @author FL
 * @since 2022-06-27
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
    @Autowired
    private UcenterMemberClient ucenterMemberClient;

    @Override
    public void createStatisticsByDay(String day) {
        //只要数据库存在了当条的数据，就将其删除
        QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date_calculated", day);
        baseMapper.delete(queryWrapper);
        //新增一个新的
        ResultEntity resultEntity = ucenterMemberClient.registerCount(day);
        Integer registerNum = (Integer) resultEntity.getData().get("countRegister");//注册人数
        //下面这些先进行随机生成
        Integer loginNum = RandomUtils.nextInt(100, 200);//TODO
        Integer videoViewNum = RandomUtils.nextInt(100, 200);//TODO
        Integer courseNum = RandomUtils.nextInt(100, 200);//TODO
        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setRegisterNum(registerNum);
        statisticsDaily.setDateCalculated(day);//统计日期
        statisticsDaily.setCourseNum(courseNum);
        statisticsDaily.setLoginNum(loginNum);
        statisticsDaily.setVideoViewNum(videoViewNum);
        baseMapper.insert(statisticsDaily);
    }

    @Override
    public Map<String, Object> getChartData(String type, String begin, String end) {
        QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("date_calculated", begin, end);
        queryWrapper.select(type, "date_calculated");
        List<StatisticsDaily> statisticsDailiesList = baseMapper.selectList(queryWrapper);
        Map<String, Object> map = new HashMap<>();
        List<Integer> typeList = new ArrayList<>();
        List<String> dateCalculatedList = new ArrayList<>();
        map.put("typeList", typeList);
        map.put("dateCalculatedList", dateCalculatedList);
        for (StatisticsDaily statisticsDaily : statisticsDailiesList) {
            dateCalculatedList.add(statisticsDaily.getDateCalculated());
            switch (type) {
                case "register_num":
                    typeList.add(statisticsDaily.getRegisterNum());
                    break;
                case "login_num":
                    typeList.add(statisticsDaily.getLoginNum());
                    break;
                case "video_view_num":
                    typeList.add(statisticsDaily.getVideoViewNum());
                    break;
                case "course_num":
                    typeList.add(statisticsDaily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        return map;
    }
}
