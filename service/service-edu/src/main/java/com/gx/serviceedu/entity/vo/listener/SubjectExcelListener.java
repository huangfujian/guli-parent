package com.gx.serviceedu.entity.vo.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gx.serviceedu.entity.EduSubject;
import com.gx.serviceedu.entity.vo.excel.ExcelSubjectData;
import com.gx.serviceedu.service.EduSubjectService;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/17 20:53
 */
public class SubjectExcelListener extends AnalysisEventListener<ExcelSubjectData> {

    private EduSubjectService eduSubjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    //读取数据
    @Override
    public void invoke(ExcelSubjectData excelSubjectData, AnalysisContext analysisContext) {
        //获取一级数据
        String oneSubjectName = excelSubjectData.getOneSubjectName();
        EduSubject oneEduSubject = exitOneSubjectName(oneSubjectName);
        //保存一级数据
        if (oneEduSubject == null) {
            oneEduSubject = new EduSubject();
            oneEduSubject.setParentId("0");
            oneEduSubject.setTitle(oneSubjectName);
            //进行保存
            eduSubjectService.save(oneEduSubject);
        }
        String pid = oneEduSubject.getId();
        String twoSubjectName = excelSubjectData.getTwoSubjectName();
        EduSubject twoEduSubject = exitTwoSubjectName(twoSubjectName, pid);
        //保存二级数据
        if (twoEduSubject == null) {
            twoEduSubject = new EduSubject();
            twoEduSubject.setTitle(twoSubjectName);
            twoEduSubject.setParentId(pid);
            //保存数据
            eduSubjectService.save(twoEduSubject);
        }
    }
    //读取后
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }

    private EduSubject exitOneSubjectName(String oneSubjectName) {
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", oneSubjectName).eq("parent_id", "0");
        return eduSubjectService.getOne(queryWrapper);
    }

    private EduSubject exitTwoSubjectName(String twoSubjectName, String pid) {
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", twoSubjectName).eq("parent_id", pid);
        return eduSubjectService.getOne(queryWrapper);
    }
}
