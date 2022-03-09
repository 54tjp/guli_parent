package com.attang.eduservice.controller;


import com.atguigu.commonutils.R;
import com.attang.eduservice.entity.EduTeacher;
import com.attang.eduservice.entity.vo.TeacherQuery;
import com.attang.eduservice.mapper.EduTeacherMapper;
import com.attang.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *nginx: [warn] conflicting server name "192.168.57.128" on 0.0.0.0:9001, ignored
 * @author testjava
 * @since 2022-02-06
 */
@Api(tags = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {
    // 1.注入service
    @Autowired
    private EduTeacherService eduTeacherService;
    @Autowired
    private EduTeacherMapper eduTeacherMapper;
    // 2.查询讲师表所有的数据
    // rust风格
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher(){
        List<EduTeacher> teacherList = eduTeacherService.list(null);
        return R.ok().data("items",teacherList);
    }

    @ApiOperation(value = "根据ID逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeById(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id){
        boolean flag = eduTeacherService.removeById(id);
        if(!flag){
            return R.error();
        }
        return R.ok();
    }

    //分页条件查询
    @ApiOperation(value = "分页讲师列表")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageList(@ApiParam(name = "current", value = "当前页码", required = true) @PathVariable Long current,@ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Long limit){
        Page<EduTeacher> teacherPage = new Page<>(current,limit);
        eduTeacherService.page(teacherPage,null);
        //获取对应页的记录
        List<EduTeacher> records = teacherPage.getRecords();
        //总数量
        long total = teacherPage.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    //条件查询带分页的方法
    @ApiOperation(value = "条件查询并分页")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageQuery(@PathVariable Long current, @PathVariable Long limit,@RequestBody(required = false) TeacherQuery teacherQuery){
        //构建条件
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        //分页对象
//        System.out.println(current);
//        System.out.println(limit);
        Page<EduTeacher> teacherPage = new Page<>(current,limit);
        //获取对应的条件值
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //判断条件是否为空,为空则不构建条件
        if(!StringUtils.isEmpty(name)){
            queryWrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            queryWrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            queryWrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            queryWrapper.le("gmt_modified",end);
        }
        //按照时间排序
        queryWrapper.orderByDesc("gmt_modified");
        //调用page方法分页对象和条件
        eduTeacherService.page(teacherPage,queryWrapper);
        //获取当前页数据
        List<EduTeacher> records = teacherPage.getRecords();
        //获取总记录数
        long total = teacherPage.getTotal();

        //返回结果
        return  R.ok().data("total", total).data("rows", records);
    }

    //添加讲师
    @ApiOperation("新增讲师")
    @PostMapping("saveTeacher")
    public R saveTeacher(@ApiParam(name = "teacher", value = "讲师信息", required = true)
                             @RequestBody EduTeacher eduTeacher){
//            if (!Strings.isBlank(eduTeacher.getName())) {
//                EduTeacher eduTeacher1 = eduTeacherMapper.selectOne(new QueryWrapper<EduTeacher>().lambda().eq(EduTeacher::getName, eduTeacher.getName()));
//                if (null != eduTeacher1) {
//                    return R.error().message("该用户已存在！！");
//                }
//            }
        boolean flag = eduTeacherService.save(eduTeacher);
        if(!flag){
            return R.error();
        }
        return R.ok();
    }

    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("getTeacher/{id}")
    public R getById(@ApiParam(name = "id", value = "讲师ID", required = true)
                        @PathVariable String id){
        EduTeacher teacher = eduTeacherService.getById(id);
//        int i = 10/0;
        return R.ok().data("teacher", teacher);
    }

    //修改讲师
    @ApiOperation("根据ID修改讲师")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if(!flag){
            return R.error();
        }
        return R.ok();
    }
}

