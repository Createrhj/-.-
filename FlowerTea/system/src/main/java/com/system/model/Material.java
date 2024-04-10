package com.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Material {
    @TableId(type = IdType.ASSIGN_UUID,value = "materialID")
    private String materialID;
    @TableField(value = "materialName")
    private String materialName;
    @TableField(value = " nickName")
    private String nickName;
    @TableField(value = "classification")
    private String classification;
    @TableField(value = "production")
    private String production;
    @TableField(value = "composition")
    private String composition;
    @TableField(value = "materialFunction")
    private String materialFunction;
    @TableField(value = "flavor")
    private String flavor;
    @TableField(value = "price")
    private float price;
}
