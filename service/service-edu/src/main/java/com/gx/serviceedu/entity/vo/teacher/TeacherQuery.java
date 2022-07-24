package com.gx.serviceedu.entity.vo.teacher;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/15 8:59
 */
@Data
@ApiModel(value = "Teacher查询对象", description = "讲师查询对象封装")
public class TeacherQuery implements Serializable {
    private static final long serialVersionUID = -3725092835526167306L;
    @ApiModelProperty(value = "教师名称，模糊查询")
    private String name;
    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
    private Integer level;
    @ApiModelProperty(value = "查询开始时间", example = "2022-05-15 10:10:10")
    private String begin;
    @ApiModelProperty(value = "查询结束时间", example = "2022-06-15 10:10:10")
    private String end;
}
