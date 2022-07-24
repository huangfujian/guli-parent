package com.gx.serviceedu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gx.commonutils.ResultEntity;
import com.gx.serviceedu.entity.EduTeacher;
import com.gx.serviceedu.entity.vo.teacher.TeacherQuery;
import com.gx.serviceedu.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author FL
 * @since 2022-06-14
 */
@RestController
@RequestMapping("/eduservice/eduteacher")
@Api("讲师管理")
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;

    @ApiOperation("所有讲师列表")
    @GetMapping("/findAll")
    public ResultEntity findAll() {
        List<EduTeacher> list = eduTeacherService.list(null);
        return ResultEntity.ok().data("items", list);
    }

    @ApiOperation("根据ID删除讲师")
    @DeleteMapping("/{id}")
    public ResultEntity removeById(@ApiParam(name = "id", value = "讲师id", required = true) //required 必须的
                                   @PathVariable("id") String id) {
        //根据Id进行删除
        boolean flag = eduTeacherService.removeById(id);
        if (flag) {
            return ResultEntity.ok();
        } else {
            return ResultEntity.error();
        }
    }

    @ApiOperation("分页讲师列表")
    @GetMapping("/{current}/{size}")
    public ResultEntity pageList(@PathVariable("current") Integer current,
                                 @PathVariable("size") Integer size) {
        Page<EduTeacher> page = new Page<>(current, size);
        eduTeacherService.page(page, null);
        List<EduTeacher> records = page.getRecords();
        long total = page.getTotal();
        //返回分页数据
        return ResultEntity.ok().data("rows", records).data("total", total);
    }

    @ApiOperation("分页条件查询讲师列表")
    @PostMapping("/pageCondition/{current}/{size}")
    public ResultEntity pageCondition(@PathVariable("current") Long current,
                                      @PathVariable("size") Long size,
                                      @RequestBody(required = false) TeacherQuery teacherQuery) {
        /*
         * @RequestBody 接收json  一定要记住 使用@RequestBody来接收数据 一定要使用post接收
         * @ResponseBody 返回json
         * */
        Page<EduTeacher> page = new Page<>(current, size);
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            queryWrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("gmt_create", end);
        }
        queryWrapper.orderByDesc("gmt_create");
        eduTeacherService.page(page, queryWrapper);
        List<EduTeacher> records = page.getRecords();
        long total = page.getTotal();
        //返回分页数据
        return ResultEntity.ok().data("rows", records).data("total", total);
    }

    @ApiOperation("添加讲师信息")
    @PostMapping("/addEduTeacher")
    public ResultEntity addEduTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = eduTeacherService.save(eduTeacher);
        if (flag) {
            return ResultEntity.ok();
        } else {
            return ResultEntity.error();
        }
    }

    @ApiOperation(value = "根据id查询讲师信息")
    @GetMapping("/findEduTeacherById/{id}")
    public ResultEntity findEduTeacherById(
            @ApiParam(name = "id", value = "讲师id", required = true)
            @PathVariable("id") String id) {
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return ResultEntity.ok().data("item", eduTeacher);
    }

    @ApiOperation("修改讲师信息")
    @PutMapping("/updateEduTeacher")
    public ResultEntity updateEduTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if (flag) {
            return ResultEntity.ok();
        } else {
            return ResultEntity.error();
        }
    }
}

