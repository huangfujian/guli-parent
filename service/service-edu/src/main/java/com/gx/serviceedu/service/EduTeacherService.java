package com.gx.serviceedu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gx.serviceedu.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author FL
 * @since 2022-06-14
 */
public interface EduTeacherService extends IService<EduTeacher> {

    List<EduTeacher> selectTeacherList();

    Map<String, Object> getTeacherFrontList(Page<EduTeacher> eduTeacherPage);

}
