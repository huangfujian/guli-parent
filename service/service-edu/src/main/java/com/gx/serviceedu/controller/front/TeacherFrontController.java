package com.gx.serviceedu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gx.commonutils.ResultEntity;
import com.gx.serviceedu.entity.EduCourse;
import com.gx.serviceedu.entity.EduTeacher;
import com.gx.serviceedu.service.EduCourseService;
import com.gx.serviceedu.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
/**
 * @author FL
 * @version 1.0
 * @date 2022/6/24 14:50
 */
@RestController
@RequestMapping("/eduservice/teacherfront")
@Api("前台api")
@CrossOrigin
public class TeacherFrontController {
    @Autowired
    private EduTeacherService eduTeacherService;
    @Autowired
    private EduCourseService eduCourseService;
    /**
     * 前台分页
     *
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/getTeacherFrontList/{page}/{limit}")
    @ApiOperation("前台分页")
    public ResultEntity getTeacherFrontList(@PathVariable("page") Long page,
                                            @PathVariable("limit") Long limit) {
        Page<EduTeacher> eduTeacherPage = new Page<>(page, limit);
        Map<String, Object> map = eduTeacherService.getTeacherFrontList(eduTeacherPage);
        return ResultEntity.ok().data(map);
    }
    /**
     * 获取老师基本信息
     * @param id
     * @return
     */
    @GetMapping("{id}")
    @ApiOperation("获取讲师基本信息")
    public ResultEntity getTeacherFrontInfo(@PathVariable("id") String id) {
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id", id);
        queryWrapper.orderByDesc("gmt_modified");
        List<EduCourse> courseList = eduCourseService.list(queryWrapper);
        //返回数据
        return ResultEntity.ok().data("teacher", eduTeacher).data("courseList", courseList);
    }
}
