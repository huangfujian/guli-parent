package com.gx.commonutils;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/15 7:35
 */
@Data
public class ResultEntity {
    @ApiModelProperty(value = "是否成功")
    private Boolean success;
    @ApiModelProperty(value = "状态码")
    private Integer code;
    @ApiModelProperty(value = "返回消息")
    private String message;
    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<>();
    //将静态私有化
    private ResultEntity() {
        //构成一个单例模式
    }

    public static ResultEntity ok() {
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setCode(ResultCode.SUCCESS);
        resultEntity.setMessage("成功");
        resultEntity.setSuccess(true);
        return resultEntity;
    }

    public static ResultEntity error() {
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setCode(ResultCode.ERROR);
        resultEntity.setSuccess(false);
        resultEntity.setMessage("失败");
        return resultEntity;
    }

    public ResultEntity success(Boolean success) {
        this.success = success;
        return this;
    }

    public ResultEntity message(String message) {
        this.message = message;
        return this;
    }

    public ResultEntity code(Integer code) {
        this.code = code;
        return this;
    }

    public ResultEntity data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public ResultEntity data(Map<String, Object> map) {
        this.data = map;
        return this;
    }

}
