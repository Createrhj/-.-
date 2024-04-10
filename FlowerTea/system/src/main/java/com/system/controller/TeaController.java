package com.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.MyPage;
import com.system.common.Result;
import com.system.model.IsMaterial;
import com.system.model.Tea;
import com.system.service.TeaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/tea")
public class TeaController {

    @Autowired
    private TeaService teaService;

    /*查询该花茶的配比信息*/
    @GetMapping("/selectMaterial")
    public Result selectMaterial(@RequestParam String teaID){
        log.info("查询该花茶的配比信息");
        List list = teaService.selectMaterial(teaID);
        return list.size()>0?Result.success(list):Result.fail();
    }

    /*根据花茶编号和材料编号删除花茶的配比信息*/
    @GetMapping("/delMaterial")
    public Result delMaterial(@RequestParam String teaID,@RequestParam String materialID){
        log.info("删除花茶配比信息");
       if(teaService.delMaterial(teaID, materialID)){
           log.info("删除花茶配比信息成功");
           return Result.success();
       }else {
           log.error("删除花茶配比信息失败");
           return Result.fail();
       }
    }

    /*添加花茶配比*/
    @PostMapping("/addMaterial")
    public Result addMaterial(@RequestBody IsMaterial isMaterial){
        log.info("添加花茶配比信息");
        if(teaService.addMaterial(isMaterial)){
            log.info("添加花茶配比信息成功");
            return Result.success();
        }else {
            log.error("添加花茶配比信息失败");
            return Result.fail();
        }
    }

    /*修改花茶配比*/
    @PostMapping("/updateMaterial")
    public Result updateMaterial(@RequestBody IsMaterial isMaterial){
        log.info("修改花茶配比信息");
        if(teaService.updateMaterial(isMaterial)){
            log.info("修改花茶配比信息成功");
            return Result.success();
        }else {
            log.error("修改花茶配比信息失败");
            return Result.fail();
        }
    }

    /*判断该花茶是否存在该花茶材料*/
    @GetMapping("/haveMaterial")
    public Result haveMaterial(@RequestParam String teaID,@RequestParam String materialID){
        log.info("判断该花茶是否存在该花茶材料");
        List list = teaService.haveMaterial(teaID, materialID);
        return list.size()>0?Result.success():Result.fail();
    }

    /*根据花茶编号查询花茶*/
    @GetMapping("/selectByID")
    public Result selectByID(@RequestParam String teaID){
        List list = teaService.lambdaQuery().eq(Tea::getTeaID,teaID).list();
        return list.size()>0?Result.success(list.get(0)):Result.fail();
    }

    /*根据花茶名称查询花茶*/
    @GetMapping("/select")
    public Result select(@RequestParam String teaName){
        log.info("判断是否存在该花茶信息");
        List list = teaService.lambdaQuery().eq(Tea::getTeaName,teaName).list();
        return list.size()>0?Result.success(list.get(0)):Result.fail();
    }

    /*新增花茶信息*/
    @PostMapping("/save")
    public Result save(@RequestBody Tea tea){
        log.info("新增花茶信息");
        if(teaService.save(tea)){
            log.info("新增花茶信息成功");
            return Result.success();
        }else {
            log.error("新增花茶失败");
            return Result.fail();
        }
    }

    /*修改花茶信息*/
    @PostMapping("/update")
    public Result update(@RequestBody Tea tea){
        log.info("修改花茶信息");
        if(teaService.updateById(tea)){
            log.info("修改花茶信息成功");
            return Result.success();
        }else {
            log.error("修改花茶信息失败");
            return Result.fail();
        }
    }

    /*根据花茶编号删除花茶信息*/
    @GetMapping("/delete")
    public Result delete(@RequestParam String teaID){
        log.info("删除花茶信息");
        if(teaService.removeById(teaID)){
            log.info("删除花茶信息成功");
            return Result.success();
        }else {
            log.error("删除花茶信息失败");
            return Result.fail();
        }
    }


    /*获取花茶总数*/
    @PostMapping("/getTotal")
    public int getTotal(){
        log.info("获取花茶总数");
        List<Tea> teas=teaService.list();
        return teas.size();
    }

    /*分页查询*/
    @PostMapping("/selectPage")
    public Result selectPage(@RequestBody MyPage myPage){
        log.info("花茶信息分页查询");
        Page<Tea> page=new Page();
        page.setCurrent(myPage.getPageNum());
        page.setSize(myPage.getPageSize());
        LambdaQueryWrapper<Tea> lambdaQueryWrapper=new LambdaQueryWrapper();

        if(StringUtils.isNotBlank((String)myPage.getParam().get("teaName"))&&!"null".equals(myPage.getParam().get("teaName"))) {
            log.info("根据花茶名称查询");
            lambdaQueryWrapper.like(Tea::getTeaName, myPage.getParam().get("teaName"));
        }
        IPage result=teaService.page(page,lambdaQueryWrapper);

        if (page.getCurrent() > result.getPages()) {
            //重新查询数据
            page.setCurrent(result.getPages());
            result = teaService.page(page, lambdaQueryWrapper);
        }

        if(result.getRecords().isEmpty()) {
            log.error("花茶信息分页查询失败");
            return Result.fail();
        }else {
            log.info("花茶信息分页查询成功");
            return Result.success((int) result.getTotal(), result.getRecords());
        }
    }

}
