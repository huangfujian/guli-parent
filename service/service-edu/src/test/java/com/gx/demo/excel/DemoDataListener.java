package com.gx.demo.excel;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.read.listener.ReadListener;

import java.util.Map;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/17 19:52
 */
//创建读取excel的监听器
public class DemoDataListener extends AnalysisEventListener<ReadData> {
    //读取表头信息
    @Override
    public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {
        System.out.println("表头信息" + headMap);
    }

    //执行
    @Override
    public void invoke(ReadData readData, AnalysisContext analysisContext) {
        System.out.println("*****" + readData);
    }

    //读取完成后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
