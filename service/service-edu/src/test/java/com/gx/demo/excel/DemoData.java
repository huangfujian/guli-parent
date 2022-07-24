package com.gx.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
/**
 * @author FL
 * @version 1.0
 * @date 2022/6/17 19:39
 */
//设置表头和添加数据的字段
@Data
public class DemoData {
    //设置表头名称
    @ExcelProperty("学生编号")
    private Integer sno;
    @ExcelProperty("学生姓名")
    private String sname;
}
