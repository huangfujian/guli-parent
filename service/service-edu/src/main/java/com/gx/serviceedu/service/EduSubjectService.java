package com.gx.serviceedu.service;

import com.gx.serviceedu.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gx.serviceedu.entity.vo.subject.TreeSubjectVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author FL
 * @since 2022-06-17
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile multipartFile, EduSubjectService eduSubjectService);

    List<TreeSubjectVO> getTreeSubject();
}
