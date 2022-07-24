package com.gx.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gx.serviceedu.entity.EduComment;
import com.gx.serviceedu.mapper.EduCommentMapper;
import com.gx.serviceedu.service.EduCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author FL
 * @since 2022-06-25
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    @Override
    public Map<String, Object> selectEduCommentMap(Page<EduComment> eduCommentPage,String courseId) {
        QueryWrapper<EduComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");
        queryWrapper.eq("course_id",courseId);
        baseMapper.selectPage(eduCommentPage, queryWrapper);
        List<EduComment> records = eduCommentPage.getRecords();
        long current = eduCommentPage.getCurrent();
        long pages = eduCommentPage.getPages();
        long size = eduCommentPage.getSize();
        long total = eduCommentPage.getTotal();
        boolean hasNext = eduCommentPage.hasNext();
        boolean hasPrevious = eduCommentPage.hasPrevious();
        Map<String, Object> map = new HashMap<>(6);
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }
}
