package com.gx.serviceedu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gx.commonutils.ResultEntity;
import com.gx.serviceedu.client.VodClient;
import com.gx.serviceedu.entity.EduCourse;
import com.gx.serviceedu.entity.vo.course.CourseInfoVO;
import com.gx.serviceedu.entity.vo.course.CoursePublishVo;
import com.gx.serviceedu.entity.vo.course.CourseQuery;
import com.gx.serviceedu.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author FL
 * @since 2022-06-18
 */
@Api("课程管理")
@RestController
@RequestMapping("/eduservice/course")
public class EduCourseController {
    @Autowired
    private EduCourseService eduCourseService;
    @ApiOperation("添加课程基本信息")
    @PostMapping("/addCourseInfo")
    public ResultEntity addCourseInfo(@RequestBody CourseInfoVO courseInfo) {
        String id = eduCourseService.saveCourceInfo(courseInfo);
        return ResultEntity.ok().data("courseId", id);
    }

    @GetMapping("/getCourseInfo/{courseId}")
    @ApiOperation("获取课程基本信息")
    public ResultEntity getCourseInfo(@PathVariable("courseId") String courseId) {
        CourseInfoVO courseInfoVO = eduCourseService.getCourseInfo(courseId);
        return ResultEntity.ok().data("item", courseInfoVO);
    }
    @GetMapping("/getCourseInfoVo/{courseId}")
    @ApiOperation("远程调用获取课程信息")
    public com.gx.commonutils.vo.CourseInfoVO getCourseInfoVo(@PathVariable("courseId") String courseId){
        return eduCourseService.getCourseInfoVO(courseId);
    }
    @ApiOperation("修改课程基本信息")
    @PutMapping("/updateCourseInfo")
    public ResultEntity updateCourseInfo(@RequestBody CourseInfoVO courseInfoVO) {
        boolean b = eduCourseService.updateCourseInfo(courseInfoVO);
        if (b) {
            return ResultEntity.ok().data("courseId", "");
        } else {
            return ResultEntity.error();
        }
    }

    @ApiOperation("获取课程发布信息")
    @GetMapping("/getCoursePublish/{id}")
    public ResultEntity getCoursePublishVo(@PathVariable("id") String courseId) {
        CoursePublishVo coursePublishVo = eduCourseService.getCoursePublishVo(courseId);
        return ResultEntity.ok().data("item", coursePublishVo);
    }

    @ApiOperation(("课程发布"))
    @PutMapping("/publishCourseById/{id}")
    public ResultEntity publishCourseById(@PathVariable("id") String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return ResultEntity.ok();
    }

    @ApiOperation("查询课程列表")
    @GetMapping("/getCoursesList")
    public ResultEntity getCoursesList() {
        List<EduCourse> list = eduCourseService.list(null);
        return ResultEntity.ok().data("items", list);
    }

    @ApiOperation("分页查询")
    @PostMapping("/pageCondition/{pageNum}/{limit}")
    public ResultEntity pageCondition(@PathVariable("pageNum") Long pageNum,
                                      @PathVariable("limit") Long limit,
                                      @RequestBody(required = false) CourseQuery courseQuery) {
        Page<EduCourse> eduCoursePage = new Page<>(pageNum, limit);
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        String title = courseQuery.getTitle();
        String subjectId = courseQuery.getSubjectId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String teacherId = courseQuery.getTeacherId();
        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.eq("subject_id", subjectId);
        }
        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.eq("subject_parent_id", subjectParentId);
        }
        if (!StringUtils.isEmpty(teacherId)) {
            queryWrapper.eq("teacher_id", teacherId);
        }
        queryWrapper.orderByDesc("gmt_create");
        eduCourseService.page(eduCoursePage, queryWrapper);
        List<EduCourse> records = eduCoursePage.getRecords();
        long total = eduCoursePage.getTotal();
        return ResultEntity.ok().data("items", records).data("total", total);
    }
    @ApiOperation("删除课程")
    @DeleteMapping("/{id}")
    public ResultEntity removeCourseById(@PathVariable("id") String id) {
        eduCourseService.removeCourse(id);
        return ResultEntity.ok();
    }
}
