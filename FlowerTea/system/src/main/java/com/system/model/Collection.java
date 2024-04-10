package com.system.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Collection {
    @TableField(value = "userID")
    private String userID;
    @TableField(value = "userName")
    private String userName;
    @TableField(value = "name")
    private String name;
    @TableField(value = "teaID")
    private String teaID;
    @TableField(value = "teaName")
    private String teaName;
    @TableField(value = "temperature")
    private String temperature;
    @TableField(value = "teaTime")
    private String teaTime;
    @TableField(value = "teaFunction")
    private String teaFunction;
    @TableField(value = "time")
    private LocalDateTime time;

}
