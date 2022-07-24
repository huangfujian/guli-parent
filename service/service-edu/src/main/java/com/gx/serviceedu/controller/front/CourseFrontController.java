package com.gx.serviceedu.controller.front;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gx.commonutils.JwtUtils;
import com.gx.commonutils.ResultEntity;
import com.gx.serviceedu.client.OrderClient;
import com.gx.serviceedu.entity.EduCourse;
import com.gx.serviceedu.entity.vo.chapter.ChapterVO;
import com.gx.serviceedu.entity.vo.frontvo.CourseFrontVO;
import com.gx.serviceedu.entity.vo.frontvo.CourseWebVo;
import com.gx.serviceedu.service.EduChapterService;
import com.gx.serviceedu.service.EduCourseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
/**
 * @author FL
 * @version 1.0
 * @date 2022/6/24 16:39
 */
@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {
    @Autowired
    private EduCourseService eduCourseService;
    @Autowired
    private EduChapterService eduChapterService;
    @Autowired
    private OrderClient orderClient;

    @PostMapping("/getCourseFrontList/{page}/{limit}")
    @ApiOperation("获取课程数据")
    public ResultEntity getCourseFrontList(@PathVariable("page") Long page,
                                           @PathVariable("limit") Long limit,
                                           @RequestBody CourseFrontVO courseFrontVO) {
        Page<EduCourse> eduCoursePage = new Page<>(page, limit);
        Map<String, Object> map = eduCourseService.getCourseFrontList(eduCoursePage, courseFrontVO);
        return ResultEntity.ok().data(map);
    }

    @ApiOperation("获取课程详情信息")
    @GetMapping("{courseId}")
    public ResultEntity getCoursesDetailsInfo(@PathVariable("courseId") String courseId, HttpServletRequest request) {
        //查询课程基本信息
        CourseWebVo courseWebVo = eduCourseService.getCourseDetail(courseId);
        //查询课程大纲
        List<ChapterVO> chapterVoList = eduChapterService.getChapterVideoByCourseId(courseId);
        //查询是否购买
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //判断课程是否被购买
        Boolean buyCourse = orderClient.isBuyCourse(memberId, courseId);
        //返回
        return ResultEntity.ok().data("course", courseWebVo).data("chapterVoList", chapterVoList).data("buyCourse", buyCourse);
    }

}
