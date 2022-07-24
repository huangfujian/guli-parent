package com.gx.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gx.commonutils.ResultEntity;
import com.gx.servicebase.config.exception.GuliException;
import com.gx.serviceedu.client.VodClient;
import com.gx.serviceedu.entity.*;
import com.gx.serviceedu.entity.vo.course.CourseInfoVO;
import com.gx.serviceedu.entity.vo.course.CoursePublishVo;
import com.gx.serviceedu.entity.vo.frontvo.CourseFrontVO;
import com.gx.serviceedu.entity.vo.frontvo.CourseWebVo;
import com.gx.serviceedu.mapper.EduCourseMapper;
import com.gx.serviceedu.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author FL
 * @since 2022-06-18
 */
@Service
@Transactional(readOnly = true)
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;
    @Autowired
    private EduChapterService eduChapterService;
    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private VodClient vodClient;
    @Autowired
    private EduTeacherService eduTeacherService;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public String saveCourceInfo(CourseInfoVO courseInfoVO) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVO, eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert <= 0) {
            //保存
            throw new GuliException(20001, "课程信息保存失败");
        }
        String id = eduCourse.getId();
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        String description = courseInfoVO.getDescription();
        eduCourseDescription.setDescription(description);
        eduCourseDescription.setId(id);
        //这里因为 EduCourseDescription  和 EduCourse 是一对一关系，所以要保持这两个表在数据ID为一致
        eduCourseDescriptionService.save(eduCourseDescription);
        return id;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public boolean updateCourseInfo(CourseInfoVO courseInfoVO) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVO, eduCourse);
        int flag = baseMapper.updateById(eduCourse);//修改课程信息
        if (flag <= 0) {
            throw new GuliException(20001, "课程信息修改失败");
        }
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(eduCourse.getId());//id
        eduCourseDescription.setDescription(courseInfoVO.getDescription());//描述
        boolean b = eduCourseDescriptionService.updateById(eduCourseDescription);//修改
        return true;
    }

    @Override
    public CourseInfoVO getCourseInfo(String courseId) {
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVO courseInfoVO = new CourseInfoVO();
        BeanUtils.copyProperties(eduCourse, courseInfoVO);
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(courseId);
        courseInfoVO.setDescription(eduCourseDescription.getDescription());
        return courseInfoVO;
    }

    @Override
    public CoursePublishVo getCoursePublishVo(String courseId) {
        return baseMapper.selectCoursePublishVoById(courseId);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public void removeCourse(String id) {
        //1、删除课程章节
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", id);
        eduChapterService.remove(chapterQueryWrapper);
        //2、删除视频
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", id);
        queryWrapper.select("video_source_id");
        List<EduVideo> eduVideoList = eduVideoService.list(queryWrapper);
        List<String> videoSourceIds = new ArrayList<>();
        for (EduVideo eduVideo : eduVideoList) {
            if (eduVideo != null) {
                String videoSourceId = eduVideo.getVideoSourceId();
                if (!StringUtils.isEmpty(videoSourceId)) {
                    //不等于空的情况
                    videoSourceIds.add(videoSourceId);
                }
            }
        }
        if (videoSourceIds.size() > 0) {
            //当大于零的情况，进行删除
            ResultEntity resultEntity = vodClient.removeBathVideo(videoSourceIds);
            if (!resultEntity.getSuccess()) {
                throw new GuliException(20001, resultEntity.getMessage());
            }
        }
        //3、删除课时
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", id);
        eduVideoService.remove(videoQueryWrapper);
        //4、删除课程
        int i = baseMapper.deleteById(id);
        if (i <= 0) {
            throw new GuliException(20001, "删除失败");
        }
    }

    //设置缓存
    @Cacheable(value = "course", key = "'selectCourseList'")
    @Override
    public List<EduCourse> selectCourseList() {
        QueryWrapper<EduCourse> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.orderByDesc("id").last("limit 8");
        //集合
        List<EduCourse> courseList = baseMapper.selectList(courseQueryWrapper);
        return courseList;
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageParam, CourseFrontVO courseQuery) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseQuery.getSubjectParentId())) {
            queryWrapper.eq("subject_parent_id", courseQuery.getSubjectParentId());
        }

        if (!StringUtils.isEmpty(courseQuery.getSubjectId())) {
            queryWrapper.eq("subject_id", courseQuery.getSubjectId());
        }
        //关注度
        if (!StringUtils.isEmpty(courseQuery.getBuyCountSort())) {
            queryWrapper.orderByDesc("buy_count");
        }
        //最新
        if (!StringUtils.isEmpty(courseQuery.getGmtCreateSort())) {
            queryWrapper.orderByDesc("gmt_create");
        }
        //价格
        if (!StringUtils.isEmpty(courseQuery.getPriceSort())) {
            queryWrapper.orderByDesc("price");
        }
        baseMapper.selectPage(pageParam, queryWrapper);
        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }

    @Override
    public CourseWebVo getCourseDetail(String courseId) {
        return baseMapper.selectInfoWebById(courseId);
    }

    @Override
    public com.gx.commonutils.vo.CourseInfoVO getCourseInfoVO(String courseId) {
        EduCourse eduCourse = baseMapper.selectById(courseId);
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", eduCourse.getTeacherId());
        queryWrapper.select("name");
        EduTeacher eduTeacher = eduTeacherService.getOne(queryWrapper);
        com.gx.commonutils.vo.CourseInfoVO courseInfoVO = new com.gx.commonutils.vo.CourseInfoVO();
        BeanUtils.copyProperties(eduCourse,courseInfoVO);
        courseInfoVO.setTeacherName(eduTeacher.getName());
        return courseInfoVO;
    }
}
