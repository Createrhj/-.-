package com.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.MyPage;
import com.system.common.Result;
import com.system.model.User;
import com.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /*根据用户名查询*/
    @GetMapping("/select")
    public Result select(@RequestParam String userName){
        log.info("根据用户名查询用户信息");
        List list = userService.lambdaQuery().eq(User::getUserName,userName).list();
        return list.size()>0?Result.success(list.get(0)):Result.fail();
    }

    /*新增用户信息*/
    @PostMapping("/save")
    public Result save(@RequestBody User user){
        log.info("新增用户信息");
        if(userService.save(user)){
            log.info("新增用户信息成功");
            return Result.success();
        }else {
            log.error("新增用户信息失败");
            return Result.fail();
        }
    }

    /*修改用户信息*/
    @PostMapping("/update")
    public Result update(@RequestBody User user){
        log.info("修改用户信息");
        if(userService.updateById(user)){
            log.info("修改用户信息成功");
            return Result.success();
        }else {
            log.error("修改用户信息失败");
            return Result.fail();
        }
    }

    /*修改用户状态*/
    @PostMapping("/changeStatue")
    public Result changeStatue(@RequestBody User user){
        log.info("修改用户状态");
        LambdaUpdateWrapper<User> lambdaUpdateWrapper=new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(User::getUserName,user.getUserName()).set(User::getStatue,user.getStatue());
        if(userService.update(lambdaUpdateWrapper)){
            log.info("修改用户状态成功");
            return Result.success();
        }else {
            log.error("修改用户状态失败");
            return Result.fail();
        }
    }

    /*删除用户信息*/
    @GetMapping("/delete")
    public Result delete(@RequestParam String userID){
        log.info("删除用户信息");
        if(userService.removeById(userID)){
            log.info("删除用户信息成功");
            return Result.success();
        }else {
            log.error("删除用户信息失败");
            return Result.fail();
        }
    }

    /*用户登录*/
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        log.info("用户登录");
        List list = userService.lambdaQuery()
                .eq(User::getUserName,user.getUserName())
                .eq(User::getPassword,user.getPassword()).list();
        /*用户信息封装*/
        if(list.size()>0){
            User CurUser=(User) list.get(0);
            HashMap res=new HashMap();
            res.put("user",CurUser);
            log.info("用户成功登录");
            return Result.success(res);
        }
        log.error("用户登录失败");
        return Result.fail();
    }

    /*用户校验*/
    @PostMapping("/checkUser")
    public Result checkPassword(@RequestBody User user){
        log.info("用户校验");
        String username=user.getUserName();
        String phone=user.getPhone();
        String email=user.getEmail();
        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper();

        if(StringUtils.isNotBlank(username)&&!"".equals(username)
                &&StringUtils.isNotBlank(phone)&&!"".equals(phone)
                &&StringUtils.isNotBlank(email)&&!"".equals(email)){
            lambdaQueryWrapper.eq(User::getUserName,username).eq(User::getPhone,phone).eq(User::getEmail,email);
        }

        List list=userService.list(lambdaQueryWrapper);

        return list.size()==1?Result.success(list.get(0)):Result.fail();
    }
    /*修改用户密码*/
    @PostMapping("updatePassword")
    public Result updatePassword(@RequestBody User user){
        log.info("用户修改密码");
        LambdaUpdateWrapper<User> lambdaUpdateWrapper=new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(User::getUserName,user.getUserName()).set(User::getPassword,user.getPassword());
        if(userService.update(lambdaUpdateWrapper)){
            log.info("修改用户密码成功");
            return Result.success();
        }else {
            log.error("修改用户密码失败");
            return Result.fail();
        }
    }
    /*获取用户总数*/
    @PostMapping("/getTotal")
    public int getTotal(){
        log.info("获取用户总数");
        List<User> users=userService.list();
        return users.size();
    }
    /*分页查询*/
    @PostMapping("/selectPage")
    public Result selectPage(@RequestBody MyPage myPage){
        log.info("用户分页查询");
        Page<User> page=new Page();
        page.setCurrent(myPage.getPageNum());
        page.setSize(myPage.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper();
        if(StringUtils.isNotBlank((String)myPage.getParam().get("userName"))&&!"null".equals(myPage.getParam().get("userName"))) {
            lambdaQueryWrapper.like(User::getUserName, myPage.getParam().get("userName"));
            log.info("根据用户名分页查询");
        }

        if (StringUtils.isNotBlank((String)myPage.getParam().get("roleID"))&&!"null".equals(myPage.getParam().get("roleID"))){
            lambdaQueryWrapper.eq(User::getRoleID, myPage.getParam().get("roleID"));
            log.info("根据用户权限分页查询");
        }

        IPage result=userService.page(page,lambdaQueryWrapper);

        if (page.getCurrent() > result.getPages()) {
            //重新查询数据
            page.setCurrent(result.getPages());
            result = userService.page(page, lambdaQueryWrapper);
        }

        if(result.getRecords().isEmpty()) {
            log.error("用户信息分页查询失败");
            return Result.fail();
        }else {
            log.info("用户信息分页查询成功");
            return Result.success((int) result.getTotal(), result.getRecords());
        }
    }
}
