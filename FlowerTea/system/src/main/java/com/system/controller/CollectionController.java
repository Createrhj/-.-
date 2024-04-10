package com.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.MyPage;
import com.system.common.Result;
import com.system.model.Collection;
import com.system.service.impl.CollectionServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/collection")
public class CollectionController {

    @Autowired
    private CollectionServiceImpl collectionService;

    //收藏夹的分页查询
    @PostMapping("/selectPage")
    public Result selectPage(@RequestBody MyPage myPage){
        log.info("收藏夹分页查询");
        Page page=new Page<>();
        page.setCurrent(myPage.getPageNum());
        page.setSize(myPage.getPageSize());
        String teaName= (String) myPage.getParam().get("teaName");
        String userName= (String) myPage.getParam().get("userName");

        IPage<Collection> result = collectionService.findCollectionPage(page,teaName,userName);

        //当前页数大于总页数时,重新查询数据,防止删除页面最后一条参数时导致分页查询查不到数据
        if (page.getCurrent() > result.getPages()) {
            page.setCurrent(result.getPages());
            result = collectionService.findCollectionPage(page,teaName,userName);
        }

        if(result.getRecords().isEmpty()) {
            log.error("收藏夹分页查询失败");
            return Result.fail();
        }else {
            log.info("收藏夹分页查询成功");
            return Result.success((int) result.getTotal(), result.getRecords());
        }
    }

    //添加收藏
    @PostMapping("/save")
    public Result save(@RequestBody Collection collection) {
        log.info("添加收藏");
        //获取当前时间
        LocalDateTime now=LocalDateTime.now();
        collection.setTime(now);
        if (collectionService.addCollection(collection)) {
            log.info("添加收藏成功");
            return Result.success();
        } else {
            log.error("添加收藏失败");
            return Result.fail();
        }
    }

    //取消收藏
    @GetMapping("/delete")
    public Result delete(@RequestParam String teaID,@RequestParam String userID){
        log.info("删除收藏");
        if(collectionService.delCollection(teaID,userID)){
            log.info("删除收藏成功");
            return Result.success();
        }else {
            log.error("删除收藏失败");
            return Result.fail();
        }
    }

    //判断是否存在该收藏信息
    @GetMapping("/haveCollection")
    public Result haveCollection(@RequestParam String teaID,@RequestParam String userID){
        log.info("判断是否存在该收藏");
        List<Object> list=collectionService.haveCollection(teaID, userID);
        if(list.isEmpty()){
            log.info("不存在该收藏");
            return Result.fail();
        }else {
            log.error("存在该收藏");
            return Result.success();
        }
    }

}
