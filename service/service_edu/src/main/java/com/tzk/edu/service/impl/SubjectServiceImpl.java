package com.tzk.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tzk.edu.entity.Subject;
import com.tzk.edu.entity.excel.SubjectData;
import com.tzk.edu.entity.subject.OneSubject;
import com.tzk.edu.entity.subject.TwoSubject;
import com.tzk.edu.listener.SubjectExcelListener;
import com.tzk.edu.mapper.SubjectMapper;
import com.tzk.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author zkTang
 * @since 2022-07-18
 */
@SuppressWarnings({"all"})
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {
    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file,SubjectService subjectService) {
        try {
            InputStream inputStream  = file.getInputStream();
            EasyExcel.read(inputStream, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //所有一级分类 parentid=0
        QueryWrapper<Subject> oneSubjectQueryWrapper = new QueryWrapper<>();
        oneSubjectQueryWrapper.eq("parent_id",0);
        List<Subject> oneSubjectList = baseMapper.selectList(oneSubjectQueryWrapper);
        //所有二级分类 parentid！=0
        QueryWrapper<Subject> twoSubjectQueryWrapper = new QueryWrapper<>();
        oneSubjectQueryWrapper.ne("parent_id",0);
        List<Subject> twoSubjectList = baseMapper.selectList(twoSubjectQueryWrapper);

        List<OneSubject> finalSubjectList = new ArrayList<>();
        //封装一级分类
        for (Subject subject1 : oneSubjectList){
            OneSubject oneSubject = new OneSubject();
//            oneSubject.setId(subject.getId());
//            oneSubject.setTitle(subject.getTitle());
            BeanUtils.copyProperties(subject1,oneSubject);
            finalSubjectList.add(oneSubject);
            //封装二级分类
            ArrayList<TwoSubject> twoFinalSubjectList = new ArrayList<>();
            for (Subject subject2 : twoSubjectList){
                if (subject2.getParentId().equals(subject1.getId())) {
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(subject2,twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }
            oneSubject.setChildren(twoFinalSubjectList);
        }
        //封装er级分类
        return finalSubjectList;
    }
}
