package com.gx.serviceedu.entity.vo.course;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/19 22:05
 */
@Data
@ApiModel(value = "课程发布信息")
public class CoursePublishVo implements Serializable {
    private static final long serialVersionUID = 6627869687393191717L;
    @ApiModelProperty(value = "课程标题")
    private String title;
    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;
    @ApiModelProperty(value = "课时")
    private Integer lessonNum;
    @ApiModelProperty(value = "一级分类")
    private String subjectLevelOne;
    @ApiModelProperty(value = "二级分类")
    private String subjectLevelTwo;
    @ApiModelProperty(value = "讲师名称")
    private String teacherName;
    @ApiModelProperty(value = "用于显示")
    private String price;
}
