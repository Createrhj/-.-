package com.system.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * 将查询出来的结果封装
 * **/
@Data
public class IsMaterial {
    @TableField(value = "tea_ID")
    private String teaID;
    @TableField(value = "material_ID")
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
    @TableField(value = "weight")
    private float weight;
}
