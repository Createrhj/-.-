package com.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.MyPage;
import com.system.common.Result;
import com.system.model.Material;
import com.system.service.MaterialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/material")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    /*获取该花茶材料信息*/
    @PostMapping("/selectByName")
    public Result selectByName(@RequestBody Material material){
        log.info("获取该花茶材料信息");
        //select * from material where materialName like ?
        List list = materialService.lambdaQuery()
                .like(Material::getMaterialName,material.getMaterialName()).list();
        return list.size()>0?Result.success(list.get(0)):Result.fail();
    }

    /*根据材料名称和类型查询材料*/
    @PostMapping("/select")
    public Result select(@RequestBody Material material){
        log.info("判断是否存在该花茶信息");
        //select * from material where materialName=? and classification=?
        List list = materialService.lambdaQuery()
                .eq(Material::getClassification,material.getClassification())
                .eq(Material::getMaterialName,material.getMaterialName()).list();
        return list.size()>0?Result.success(list.get(0)):Result.fail();
    }
    /*新增材料信息*/
    @PostMapping("/save")
    public Result save(@RequestBody Material material){
        log.info("新增花茶材料信息");
        if(materialService.save(material)){
            log.info("新增花茶材料信息成功");
            return Result.success();
        }else {
            log.error("新增花茶材料信息失败");
            return Result.fail();
        }
    }
    /*修改材料信息*/
    @PostMapping("/update")
    public Result update(@RequestBody Material material){
        log.info("修改花茶材料信息");
        if(materialService.updateById(material)){
            log.info("修改花茶材料信息成功");
            return Result.success();
        }else {
            log.error("修改花茶材料失败");
            return Result.fail();
        }
    }
    /*删除材料信息*/
    @GetMapping("/delete")
    public Result delete(@RequestParam String materialID){
        log.info("删除花茶");
        if(materialService.removeById(materialID)){
            log.info("删除花茶成功");
            return Result.success();
        }else {
            log.error("删除花茶失败");
            return Result.fail();
        }
    }

    /*获取花茶材料总数*/
    @PostMapping("/getTotal")
    public int getTotal(){
        log.info("查询花茶材料总数");
        List<Material> materials=materialService.list();
        return materials.size();
    }

    /*分页查询*/
    @PostMapping("/selectPage")
    public Result selectPage(@RequestBody MyPage myPage){
        log.info("花茶材料分页查询");
        Page<Material> page=new Page();
        page.setCurrent(myPage.getPageNum());
        page.setSize(myPage.getPageSize());

        LambdaQueryWrapper<Material> lambdaQueryWrapper=new LambdaQueryWrapper();

        //select * from material where materialName=?
        if(StringUtils.isNotBlank((String)myPage.getParam().get("materialName"))&&!"null".equals(myPage.getParam().get("materialName"))) {
            log.info("根据花茶名称查询");
            lambdaQueryWrapper.like(Material::getMaterialName, myPage.getParam().get("materialName"));
        }

        //select * from material where classification=?
        if(StringUtils.isNotBlank((String)myPage.getParam().get("classification"))&&!"null".equals(myPage.getParam().get("classification"))) {
            log.info("根据花茶类型查询");
            lambdaQueryWrapper.eq(Material::getClassification, myPage.getParam().get("classification"));
        }

        IPage result=materialService.page(page,lambdaQueryWrapper);

        if (page.getCurrent() > result.getPages()) {
            //重新查询数据
            page.setCurrent(result.getPages());
            result = materialService.page(page, lambdaQueryWrapper);
        }

        if (result.getRecords().isEmpty()){
            log.error("花茶材料分页查询失败");
            return Result.fail();
        }else {
            log.info("花茶材料分页查询成功");
            return Result.success((int) result.getTotal(),result.getRecords());
        }

    }

}
