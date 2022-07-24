package com.gx.serviceedu.service;

import com.gx.serviceedu.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gx.serviceedu.entity.vo.chapter.ChapterVO;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author FL
 * @since 2022-06-18
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVO> getChapterVideoByCourseId(String courseId);

    boolean removeChapterById(String id);
}
