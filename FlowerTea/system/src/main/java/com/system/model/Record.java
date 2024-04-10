package com.system.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Record {
    @TableField(value = "roleID")
    private int roleID;
    @TableField(value = "userName")
    private String userName;
    @TableField(value = "nickName")
    private String nickName;
    @TableField(value = "operation")
    private String operation;
    @TableField(value = "location")
    private String location;
    private String id;
    @TableField(value = "name")
    private String name;
    @TableField(value = "time")
    private LocalDateTime time;
}
