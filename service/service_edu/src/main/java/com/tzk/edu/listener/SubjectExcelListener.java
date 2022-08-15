package com.tzk.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tzk.edu.entity.Subject;
import com.tzk.edu.entity.excel.SubjectData;
import com.tzk.edu.service.SubjectService;
import com.tzk.servicebase.exceptionhandle.CustomException;
@SuppressWarnings({"all"})
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    public SubjectService subjectService;

    public SubjectExcelListener(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    public SubjectExcelListener() {
    }


    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null){
            throw new CustomException(20001,"文件数据为空");
        }
        Subject existOneSubject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
        if (existOneSubject == null){
            existOneSubject = new Subject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubjectName());
            subjectService.save(existOneSubject);
        }
        Subject existTwoSubject = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(), existOneSubject.getId());
        if (existTwoSubject == null){
            existTwoSubject = new Subject();
            existTwoSubject.setParentId(existOneSubject.getId());
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());
            subjectService.save(existTwoSubject);
        }


    }
    //判断一级分类不能重复
    public Subject existOneSubject(SubjectService subjectService,String name){
        QueryWrapper<Subject> subjectQueryWrapper = new QueryWrapper<>();
        subjectQueryWrapper.eq("title",name);
        subjectQueryWrapper.eq("parent_id","0");
        Subject oneSubject = subjectService.getOne(subjectQueryWrapper);
        return oneSubject;
    }
    //判断二级分类不能重复
    public Subject existTwoSubject(SubjectService subjectService,String name,String pid){
        QueryWrapper<Subject> subjectQueryWrapper = new QueryWrapper<>();
        subjectQueryWrapper.eq("title",name);
        subjectQueryWrapper.eq("parent_id",pid);
        Subject twoSubject = subjectService.getOne(subjectQueryWrapper);
        return twoSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
