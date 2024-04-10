package com.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.MyPage;
import com.system.common.Result;
import com.system.model.Record;
import com.system.service.impl.RecordServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
@Slf4j
@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordServiceImpl recordService;
    /*分页查询*/
    @PostMapping("/selectPage")
    public Result selectPage(@RequestBody MyPage myPage) {
        log.info("记录管理分页查询");
        Page<Record> page = new Page();
        page.setCurrent(myPage.getPageNum());
        page.setSize(myPage.getPageSize());
        LambdaQueryWrapper<Record> lambdaQueryWrapper = new LambdaQueryWrapper();
        if (StringUtils.isNotBlank((String) myPage.getParam().get("userName")) && !"null".equals(myPage.getParam().get("userName"))) {
            log.info("根据用户名查询");
            lambdaQueryWrapper.eq(Record::getUserName, myPage.getParam().get("userName"));
        }
        if (StringUtils.isNotBlank((String)myPage.getParam().get("roleID"))&&!"null".equals(myPage.getParam().get("roleID"))){
            log.info("根据用户名权限查询");
            lambdaQueryWrapper.eq(Record::getRoleID, myPage.getParam().get("roleID"));
        }
        if (StringUtils.isNotBlank((String)myPage.getParam().get("location"))&&!"null".equals(myPage.getParam().get("location"))){
            log.info("根据记录页表查询");
            lambdaQueryWrapper.eq(Record::getLocation, myPage.getParam().get("location"));
        }

        IPage result = recordService.page(page, lambdaQueryWrapper);

        if (page.getCurrent() > result.getPages()) {
            //重新查询数据
            page.setCurrent(result.getPages());
            result = recordService.page(page, lambdaQueryWrapper);
        }


        System.out.println(result.getPages());

        if (result.getRecords().isEmpty()) {
            log.error("记录管理分页查询失败");
            return Result.fail();
        } else {
            log.info("记录管理分页查询成功");
            return Result.success((int) result.getTotal(), result.getRecords());
        }
    }
    /*添加用户操作记录*/
    @PostMapping("/save")
    public Result save(@RequestBody Record record){
        log.info("添加用户操作记录");
        LocalDateTime now = LocalDateTime.now();
        record.setTime(now);
        if(recordService.save(record)){
            log.info("添加用户操作记录成功");
            return Result.success();
        }else {
            log.error("添加用户操作记录失败");
            return Result.fail();
        }
    }
    /*删除用户记录*/
    @PostMapping("/delete")
    public Result delete(@RequestBody Record record){
        log.info("删除用户记录");
        LambdaQueryWrapper<Record> lambdaQueryWrapper=new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Record::getUserName, record.getUserName())
                .eq(Record::getId, record.getId())
                .eq(Record::getOperation,record.getOperation())
                .eq(Record::getTime,record.getTime());
        if(recordService.remove(lambdaQueryWrapper)){
            log.info("删除用户记录成功");
            return Result.success();
        }else {
            log.error("删除用户记录失败");
            return Result.fail();
        }
    }
}
