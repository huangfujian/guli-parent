package com.gx.serviceedu.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gx.commonutils.ResultEntity;
import com.gx.serviceedu.entity.EduComment;
import com.gx.serviceedu.service.EduCommentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author FL
 * @since 2022-06-25
 */
@RestController
@RequestMapping("/eduservice/commentfront")
@CrossOrigin
public class CommentFrontController {
    @Autowired
    private EduCommentService eduCommentService;

    @ApiOperation("分页查询")
        @GetMapping("/pageCondition/{page}/{limit}")
    public ResultEntity pageCondition(@PathVariable("page") Integer page,
                                      @PathVariable("limit") Integer limit,
                                      @RequestParam("courseId") String courseId) {
        Page<EduComment> eduCommentPage = new Page<>(page, limit);
        Map<String, Object> map = eduCommentService.selectEduCommentMap(eduCommentPage,courseId);
        return ResultEntity.ok().data(map);
    }
    @PostMapping("/addComment")
    @ApiOperation("新增评论")
    public ResultEntity addComment(@RequestBody EduComment eduComment) {
        eduCommentService.save(eduComment);
        return ResultEntity.ok();
    }
}

