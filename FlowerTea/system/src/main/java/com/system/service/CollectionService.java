package com.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.system.common.MyPage;
import com.system.model.Collection;
import com.system.model.IsMaterial;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollectionService extends IService<Collection> {
    boolean addCollection(Collection collection);
    boolean delCollection(@Param("teaID") String teaID, @Param("userID") String userID);
    IPage<Collection> findCollectionPage(Page page,@Param("teaName")String teaName,@Param("username")String username);
    List<Object> haveCollection(@Param("teaID") String teaID, @Param("userID") String userID);
}
