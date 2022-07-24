package com.gx.serviceedu.mapper;

import com.gx.serviceedu.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gx.serviceedu.entity.vo.course.CoursePublishVo;
import com.gx.serviceedu.entity.vo.frontvo.CourseWebVo;
import org.springframework.data.repository.query.Param;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author FL
 * @since 2022-06-18
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    CoursePublishVo selectCoursePublishVoById(@Param("courseId") String courseId);

    CourseWebVo selectInfoWebById(@Param("id") String courseId);

}
