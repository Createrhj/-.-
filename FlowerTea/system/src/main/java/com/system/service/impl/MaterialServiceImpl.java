package com.system.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.mapper.MaterialMapper;
import com.system.model.Material;
import com.system.service.MaterialService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper, Material> implements MaterialService {
}
