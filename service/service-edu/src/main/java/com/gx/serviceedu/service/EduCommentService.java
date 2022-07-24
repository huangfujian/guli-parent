package com.gx.serviceedu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gx.serviceedu.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author FL
 * @since 2022-06-25
 */
public interface EduCommentService extends IService<EduComment> {

    Map<String,Object> selectEduCommentMap(Page<EduComment> eduCommentPage,String courseId);

}
