package com.gx.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gx.servicebase.config.exception.GuliException;
import com.gx.serviceedu.entity.EduChapter;
import com.gx.serviceedu.entity.EduVideo;
import com.gx.serviceedu.entity.vo.chapter.ChapterVO;
import com.gx.serviceedu.entity.vo.video.VideoVO;
import com.gx.serviceedu.mapper.EduChapterMapper;
import com.gx.serviceedu.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gx.serviceedu.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterVO> getChapterVideoByCourseId(String courseId) {
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", courseId);
        //获取章节列表
        List<EduChapter> eduChapters = baseMapper.selectList(chapterQueryWrapper);
        //获取小节列表
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        List<EduVideo> eduVideoList = eduVideoService.list(videoQueryWrapper);
        //遍历章节列表
        List<ChapterVO> chapterVOList = new ArrayList<>();
        for (EduChapter eduChapter : eduChapters) {
            ChapterVO chapterVO = new ChapterVO();
            BeanUtils.copyProperties(eduChapter, chapterVO);
            chapterVOList.add(chapterVO);
            for (EduVideo eduVideo : eduVideoList) {
                if (eduVideo.getChapterId().equals(chapterVO.getId())) {
                    VideoVO videoVO = new VideoVO();
                    BeanUtils.copyProperties(eduVideo, videoVO);
                    chapterVO.getChildren().add(videoVO);
                }
            }
        }
        return chapterVOList;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public boolean removeChapterById(String id) {
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", id);
        int count = eduVideoService.count(queryWrapper);
        if (count > 0) {
            throw new GuliException(20001, "不能删除");
        } else {
            int i = baseMapper.deleteById(id);
            return i > 0;
        }
    }
}










