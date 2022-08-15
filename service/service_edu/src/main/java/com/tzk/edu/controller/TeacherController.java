package com.tzk.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tzk.commonutils.R;
import com.tzk.edu.entity.Teacher;
import com.tzk.edu.entity.vo.TeacherQuery;
import com.tzk.edu.service.TeacherService;
import com.tzk.servicebase.exceptionhandle.CustomException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author zkTang
 * @since 2022-07-13
 */
@SuppressWarnings({"all"})
@RestController
@RequestMapping("/eduservice/teacher")
@Api(description="讲师管理")
@CrossOrigin
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/findAll")
    @ApiOperation(value = "所有讲师列表")
    public R findAll(){
        List<Teacher> list = teacherService.list(null);
        return R.ok().data("items",list);
    }
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){
        boolean flag = teacherService.removeById(id);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
    @ApiOperation(value = "分页查询")
    @GetMapping("/pageTeacher/{current}/{limit}")
    public R pageList(@ApiParam(value = "当前页") @PathVariable long current,
                      @ApiParam(value = "每页记录数") @PathVariable long limit){
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        Page<Teacher> teacherPage = new Page<Teacher>(current,limit);
        teacherService.page(teacherPage, null);
        long total = teacherPage.getTotal();
        List<Teacher> records = teacherPage.getRecords();
        return R.ok().data("total",total).data("records",records);
    }
    @ApiOperation(value = "条件组合分页查询")
    @PostMapping("/pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@ApiParam(value = "当前页") @PathVariable long current,
                                  @ApiParam(value = "每页记录数") @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){
        Page<Teacher> teacherPage = new Page<>(current, limit);
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if (!StringUtils.isEmpty(name)){
            teacherQueryWrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(level)){
            teacherQueryWrapper.eq("level",level);
        }
        if (!StringUtils.isEmpty(begin)){
            teacherQueryWrapper.gt("gmt_create",begin);
        }
        if (!StringUtils.isEmpty(end)){
            teacherQueryWrapper.lt("gmt_create",end);
        }
        teacherQueryWrapper.orderByDesc("gmt_create");
        teacherService.page(teacherPage,teacherQueryWrapper);
        long total = teacherPage.getTotal();
        List<Teacher> records = teacherPage.getRecords();
        return R.ok().data("total",total).data("records",records);
    }
    //添加讲师
    @ApiOperation(value = "添加讲师")
    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody Teacher teacher){
        boolean save = teacherService.save(teacher);
        if (save == true){
            return R.ok();
        }else {
            return R.error();
        }
    }
    @ApiOperation(value = "根据id查询讲师")
    @GetMapping("/getTeacherById/{id}")
    public R getTaecher(@PathVariable String id){
        Teacher teacher = teacherService.getById(id);
        return R.ok().data("teacher",teacher);
    }
    @ApiOperation(value = "修改讲师信息")
    @PostMapping("/updateTeacher")
    public R updateTeacher(@RequestBody Teacher teacher){
        boolean update = teacherService.updateById(teacher);
        if (update == true){
            return R.ok();
        }else {
            return R.error();
        }
    }


}

