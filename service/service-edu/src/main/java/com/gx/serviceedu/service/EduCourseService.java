package com.gx.serviceedu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gx.serviceedu.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gx.serviceedu.entity.vo.course.CourseInfoVO;
import com.gx.serviceedu.entity.vo.course.CoursePublishVo;
import com.gx.serviceedu.entity.vo.frontvo.CourseFrontVO;
import com.gx.serviceedu.entity.vo.frontvo.CourseWebVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author FL
 * @since 2022-06-18
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourceInfo(CourseInfoVO courseInfoVO);

    boolean updateCourseInfo(CourseInfoVO courseInfoVO);

    CourseInfoVO getCourseInfo(String courseId);

    CoursePublishVo getCoursePublishVo(String courseId);

    void removeCourse(String id);

    List<EduCourse> selectCourseList();

    Map<String, Object> getCourseFrontList(Page<EduCourse> eduCoursePage, CourseFrontVO courseFrontVO);

    CourseWebVo getCourseDetail(String courseId);

    com.gx.commonutils.vo.CourseInfoVO getCourseInfoVO(String courseId);

}
