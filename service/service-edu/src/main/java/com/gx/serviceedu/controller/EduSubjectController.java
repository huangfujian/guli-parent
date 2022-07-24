package com.gx.serviceedu.controller;
import com.gx.commonutils.ResultEntity;
import com.gx.serviceedu.entity.vo.subject.TreeSubjectVO;
import com.gx.serviceedu.service.EduSubjectService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author FL
 * @since 2022-06-17
 */
@RestController
@RequestMapping("/eduservice/subject")
@Api("课程分类")
public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;

    @PostMapping("/addSubject")
    public ResultEntity addSubject(MultipartFile file) {
        eduSubjectService.saveSubject(file, eduSubjectService);
        return ResultEntity.ok();
    }

    @GetMapping("/getSubjectAll")
    public ResultEntity getSubjectAll() {
        List<TreeSubjectVO> treeSubject = eduSubjectService.getTreeSubject();
        return ResultEntity.ok().data("items", treeSubject);
    }
}

