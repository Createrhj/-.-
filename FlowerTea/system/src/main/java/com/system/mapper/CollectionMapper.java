package com.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.model.Collection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface  CollectionMapper extends BaseMapper<Collection> {
    boolean addCollection(Collection collection);
    boolean delCollection(@Param("teaID") String teaID, @Param("userID") String userID);
    IPage<Collection> findCollectionPage(Page Page,@Param("teaName")String teaName,@Param("userName")String userName);
    List<Object> haveCollection(@Param("teaID") String teaID, @Param("userID") String userID);
}
