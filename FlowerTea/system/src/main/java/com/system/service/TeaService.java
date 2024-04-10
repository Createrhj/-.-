package com.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.system.model.IsMaterial;
import com.system.model.Tea;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeaService extends IService<Tea> {
    List<Object> selectMaterial(@Param("teaID") String teaID);

    boolean delMaterial(@Param("teaID") String teaID,@Param("materialID") String materialID);

    boolean addMaterial(IsMaterial isMaterial);

    boolean updateMaterial(IsMaterial isMaterial);


    List<Object> haveMaterial(@Param("teaID") String teaID,@Param("materialID") String materialID);
}
