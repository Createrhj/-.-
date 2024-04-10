package com.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.common.MyPage;
import com.system.mapper.CollectionMapper;
import com.system.model.Collection;
import com.system.model.IsMaterial;
import com.system.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionServiceImpl extends ServiceImpl<CollectionMapper, Collection> implements CollectionService {

    @Autowired
    private CollectionMapper collectionMapper;

    @Override
    public boolean addCollection(Collection collection) {
        if(collectionMapper.addCollection(collection)){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean delCollection(String teaID, String userID) {
        if(collectionMapper.delCollection(teaID, userID)){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public IPage<Collection> findCollectionPage(Page page,String teaName,String userName) {
        IPage<Collection> CollectionPage = collectionMapper.findCollectionPage(page,teaName,userName);
        return CollectionPage;
    }

    @Override
    public List<Object> haveCollection(String teaID, String userID) {
        List<Object> list=collectionMapper.haveCollection(teaID, userID);
        return list;
    }
}
