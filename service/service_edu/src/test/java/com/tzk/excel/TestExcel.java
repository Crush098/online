package com.tzk.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestExcel {

    public static void main(String[] args) {
        //excel写操作
        //设置文件的路径以及名称
//        String fileName = "D:\\write.xlsx";
//        //调用write方法实现写的操作
//        //第一个参数：文件的路径以及名称 第二个参数：实体类class
//        EasyExcel.write(fileName, DemoData.class).sheet("学生列表").doWrite(getData());

        //excel读操作
        String fileName = "D:\\write.xlsx";
        EasyExcel.read(fileName,DemoData.class,new ExcelListener()).sheet().doRead();

    }

    private static List<DemoData> getData(){
        ArrayList<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            DemoData demoData = new DemoData();
            demoData.setSno(i);
            demoData.setSname("zzz"+i);
            list.add(demoData);
        }
        return list;
    }



}
