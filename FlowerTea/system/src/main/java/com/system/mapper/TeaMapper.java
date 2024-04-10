package com.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.system.model.IsMaterial;
import com.system.model.Tea;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface TeaMapper extends BaseMapper<Tea> {
    List<Object> selectMaterial(@Param("teaID") String teaID);

    List<Object> haveMaterial(@Param("teaID") String teaID,@Param("materialID") String materialID);

    boolean delMaterial(@Param("teaID") String teaID,@Param("materialID") String materialID);

    boolean addMaterial(IsMaterial isMaterial);

    boolean updateMaterial(IsMaterial isMaterial);

}

