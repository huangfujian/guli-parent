package com.gx.serviceedu.entity.vo.excel;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
/**
 * @author FL
 * @version 1.0
 * @date 2022/6/17 20:47
 */
@Data
public class ExcelSubjectData {
    //属性
    @ExcelProperty(index = 0)
    private String oneSubjectName;
    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
