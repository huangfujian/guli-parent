package com.gx.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/17 19:54
 */
@Data
@ToString
public class ReadData {
    @ExcelProperty(index = 0)
    private Integer sno;
    @ExcelProperty(index = 1)
    private String sname;
}
