package com.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Tea {
    @TableId(type = IdType.ASSIGN_UUID,value = "teaID")
    private String teaID;
    @TableField(value = "teaName")
    private String teaName;
    @TableField(value = "temperature")
    private float temperature;
    @TableField(value = "teaTime")
    private float teaTime;
    @TableField(value = "teaFunction")
    private String teaFunction;

}
