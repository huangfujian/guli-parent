package com.gx.serviceedu.entity.vo.course;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/20 14:34
 */
@Data
public class CourseQuery implements Serializable {
    private static final long serialVersionUID = -4404888748123407031L;
    @ApiModelProperty(value = "课程名称")
    private String title;
    @ApiModelProperty(value = "讲师id")
    private String teacherId;
    @ApiModelProperty(value = "一级类别id")
    private String subjectParentId;
    @ApiModelProperty(value = "二级类别id")
    private String subjectId;
}
