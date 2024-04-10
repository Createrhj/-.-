package com.system.mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.system.model.Material;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface MaterialMapper extends BaseMapper<Material> {

}
