package com.gx.serviceedu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gx.commonutils.ResultEntity;
import com.gx.serviceedu.entity.EduCourse;
import com.gx.serviceedu.entity.EduTeacher;
import com.gx.serviceedu.service.EduCourseService;
import com.gx.serviceedu.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/22 7:35
 */
@RestController
@RequestMapping("/eduservice/index")
@CrossOrigin
public class IndexController {
    @Autowired
    private EduTeacherService eduTeacherService;
    @Autowired
    private EduCourseService eduCourseService;

    @GetMapping("/getData")
    public ResultEntity index() {
        //查询前8门热门课程
        List<EduCourse> courseList = eduCourseService.selectCourseList();
        //查询前4条名师
        List<EduTeacher> teacherList = eduTeacherService.selectTeacherList();
        //返回数据
        return ResultEntity.ok().data("courseList", courseList).data("teacherList", teacherList);
    }
}
