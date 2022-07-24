package com.gx.serviceucenter.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/22 20:23
 */
@Data
@ApiModel(value = "登录对象")
public class LoginVO {
    @ApiModelProperty(value = "手机号")
    private String mobile;
    @ApiModelProperty(value = "密码")
    private String password;
}
