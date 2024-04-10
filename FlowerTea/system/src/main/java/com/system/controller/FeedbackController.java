package com.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.MyPage;
import com.system.common.Result;
import com.system.model.Feedback;
import com.system.service.impl.FeedbackServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackServiceImpl feedbackService;

    @GetMapping("/getTotal")
    public int getTotal(@Param("mark") int mark,@Param("name") String name){
        LambdaQueryWrapper<Feedback> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        if (name!=null) {
            lambdaQueryWrapper.eq(Feedback::getName,name).eq(Feedback::getMark,mark);
        }else {
            lambdaQueryWrapper.eq(Feedback::getMark,mark);
        }
        return feedbackService.list(lambdaQueryWrapper).size();
    }

    @PostMapping("/addFeedback")
    public Result addFeedback(@RequestBody Feedback feedback){
        LocalDateTime localDateTime=LocalDateTime.now();
        feedback.setTime(localDateTime);
        if(feedbackService.save(feedback)){
            return Result.success();
        }else {
            return Result.fail();
        }
    }

    @PostMapping("/delFeedback")
    public Result delFeedback(@RequestBody Feedback feedback){
        LambdaQueryWrapper<Feedback> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Feedback::getName,feedback.getName()).eq(Feedback::getTime,feedback.getTime());
        if(feedbackService.remove(lambdaQueryWrapper)){
            return Result.success();
        }else  {
            return Result.fail();
        }
    }


    @PostMapping("/updateFeedback")
    public Result updateFeedback(@RequestBody Feedback feedback){
        LambdaUpdateWrapper<Feedback> lambdaUpdateWrapper=new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Feedback::getName,feedback.getName()).eq(Feedback::getTime,feedback.getTime());
        if(feedbackService.update(feedback,lambdaUpdateWrapper)){
            return Result.success();
        }else {
            return Result.fail();
        }
    }

    @PostMapping("/selectPage")
    public Result selectPage(@RequestBody MyPage myPage){
        Page<Feedback> page=new Page();
        page.setCurrent(myPage.getPageNum());
        page.setSize(myPage.getPageSize());

        LambdaQueryWrapper<Feedback> lambdaQueryWrapper=new LambdaQueryWrapper();

        if(StringUtils.isNotBlank((String)myPage.getParam().get("title"))&&!"null".equals(myPage.getParam().get("title"))) {
            lambdaQueryWrapper.like(Feedback::getTitle, myPage.getParam().get("title"));
        }

        if(StringUtils.isNotBlank((String)myPage.getParam().get("name"))&&!"null".equals(myPage.getParam().get("name"))) {
            lambdaQueryWrapper.eq(Feedback::getName, myPage.getParam().get("name"));
        }

        if(StringUtils.isNotBlank((String)myPage.getParam().get("mark"))&&!"null".equals(myPage.getParam().get("mark"))) {
            lambdaQueryWrapper.eq(Feedback::getMark, myPage.getParam().get("mark"));
        }

        IPage result=feedbackService.page(page,lambdaQueryWrapper);

        if (page.getCurrent() > result.getPages()) {
            //重新查询数据
            page.setCurrent(result.getPages());
            result = feedbackService.page(page, lambdaQueryWrapper);
        }
        if (result.getRecords().isEmpty()){
            return Result.fail();
        }else {
            return Result.success((int) result.getTotal(),result.getRecords());
        }

    }
}
