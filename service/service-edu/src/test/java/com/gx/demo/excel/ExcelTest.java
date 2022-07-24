package com.gx.demo.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import org.apache.commons.collections4.ListUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/17 19:38
 */
public class ExcelTest {
    public static void main(String[] args) {
        // write();
//        read();
        String a = "a";
        String b = "b";
        String c = a + b;
        String d = new String("ab");
        System.out.println((a + b).equals(c));
        System.out.println(a + b == c);
        System.out.println(c == d);
        System.out.println(c.equals(d));


    }

    public static void write() {
        String filename = "E:\\后端\\项目实战\\在线教育平台项目\\mydev\\test.xlsx";
        //指定写入对象,
        EasyExcel.write(filename, DemoData.class).sheet("学生信息").doWrite(getData());
    }

    public static void read() {
        String filename = "E:\\后端\\项目实战\\在线教育平台项目\\mydev\\test.xlsx";
        EasyExcel.read(filename, ReadData.class, new DemoDataListener()).sheet("学生信息").doRead();
    }

    private static List<DemoData> getData() {
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSname("tom" + i);
            data.setSno(i + 1);
            list.add(data);
        }
        return list;
    }
}
