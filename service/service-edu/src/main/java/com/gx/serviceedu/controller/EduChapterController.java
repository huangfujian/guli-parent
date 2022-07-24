package com.gx.serviceedu.controller;
import com.gx.commonutils.ResultEntity;
import com.gx.serviceedu.entity.EduChapter;
import com.gx.serviceedu.entity.vo.chapter.ChapterVO;
import com.gx.serviceedu.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author FL
 * @since 2022-06-18
 */
@RestController
@RequestMapping("/eduservice/chapter")
@Api("课程章节")
public class EduChapterController {
    @Autowired
    private EduChapterService eduChapterService;
    @GetMapping("/getChapterVideo/{courseId}")
    @ApiOperation("获取课程大列表")
    public ResultEntity getChapterVideo(@PathVariable("courseId") String courseId) {
        List<ChapterVO> chapterVOList = eduChapterService.getChapterVideoByCourseId(courseId);
        return ResultEntity.ok().data("items", chapterVOList);
    }
    @PostMapping("/addChapter")
    @ApiOperation("添加章节")
    public ResultEntity addChapter(@RequestBody ChapterVO chapterVO) {
        //保存
        EduChapter eduChapter = new EduChapter();
        BeanUtils.copyProperties(chapterVO, eduChapter);
        boolean save = eduChapterService.save(eduChapter);
        if (save) {
            return ResultEntity.ok();
        } else {
            return ResultEntity.error();
        }
    }
    @ApiOperation("修改章节")
    @PutMapping("/updateChapter")
    public ResultEntity updateChapter(@RequestBody ChapterVO chapterVO) {
        EduChapter eduChapter = new EduChapter();
        BeanUtils.copyProperties(chapterVO, eduChapter);
        //修改章节
        boolean b = eduChapterService.updateById(eduChapter);
        if (b) {
            return ResultEntity.ok();
        } else {
            return ResultEntity.error();
        }
    }
    @ApiOperation("根据ID获取数据")
    @GetMapping("{id}")
    public ResultEntity getChapter(@PathVariable("id") String id) {
        EduChapter eduChapter = eduChapterService.getById(id);
        return ResultEntity.ok().data("item", eduChapter);
    }
    @ApiOperation("删除章节")
    @DeleteMapping("/{id}")
    public ResultEntity removeChapter(@PathVariable("id") String id) {
        boolean b = eduChapterService.removeChapterById(id);
        if (b) {
            return ResultEntity.ok();
        } else {
            return ResultEntity.error();
        }
    }
}

