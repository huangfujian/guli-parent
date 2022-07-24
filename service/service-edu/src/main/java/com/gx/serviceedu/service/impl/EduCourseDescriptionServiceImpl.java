package com.gx.serviceedu.service.impl;

import com.gx.serviceedu.entity.EduCourseDescription;
import com.gx.serviceedu.entity.vo.course.CoursePublishVo;
import com.gx.serviceedu.mapper.EduCourseDescriptionMapper;
import com.gx.serviceedu.service.EduCourseDescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author FL
 * @since 2022-06-18
 */
@Service
public class EduCourseDescriptionServiceImpl extends ServiceImpl<EduCourseDescriptionMapper, EduCourseDescription> implements EduCourseDescriptionService {

}
