package com.system.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.mapper.TeaMapper;
import com.system.model.IsMaterial;
import com.system.model.Tea;
import com.system.service.TeaService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeaServiceImpl extends ServiceImpl<TeaMapper, Tea> implements TeaService {
    @Autowired
    private TeaMapper teaMapper;
    public List<Object> selectMaterial(@Param("teaID") String teaID) {
        return teaMapper.selectMaterial(teaID);
    }


    public boolean delMaterial(@Param("teaID") String teaID,@Param("materialID") String materialID){
        if(teaMapper.delMaterial(teaID, materialID)){
            return true;
        }else {
            return false;
        }
    }

    public boolean addMaterial(IsMaterial isMaterial){
        if(teaMapper.addMaterial(isMaterial)){
            return true;
        }else {
            return false;
        }
    }

    public boolean updateMaterial(IsMaterial isMaterial){
        if(teaMapper.updateMaterial(isMaterial)){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<Object> haveMaterial(@Param("teaID") String teaID,@Param("materialID") String materialID) {
        return teaMapper.haveMaterial(teaID,materialID);
    }
}
