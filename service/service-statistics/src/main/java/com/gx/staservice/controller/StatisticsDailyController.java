package com.gx.staservice.controller;
import com.gx.commonutils.ResultEntity;
import com.gx.staservice.service.StatisticsDailyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author FL
 * @since 2022-06-27
 */
@RestController
@RequestMapping("/staservice/statisticsdaily")
@CrossOrigin //跨域
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    /**
     * 根据当天创建出数据统计
     *
     * @return
     */
    @PostMapping("{day}")
    @ApiOperation("根据日期创建出数据统计")
    public ResultEntity createStatisticsByDay(@PathVariable("day") String day) {
        statisticsDailyService.createStatisticsByDay(day);//创建统计数据
        return ResultEntity.ok();//数据统计成功
    }
    /**
     * 统计图数据
     *
     * @param type
     * @param begin
     * @param end
     * @return
     */
    @ApiOperation("统计图数据")
    @GetMapping("/show/chart/{type}/{begin}/{end}")
    public ResultEntity showChart(@PathVariable("type") String type,
                                  @PathVariable("begin") String begin,
                                  @PathVariable("end") String end) {
        Map<String, Object> data = statisticsDailyService.getChartData(type, begin, end);
        //直接返回数据
        return ResultEntity.ok().data(data);
    }
}

