package com.system.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Feedback {
    @TableField(value = "name")
    private String name;
    @TableField(value = "title")
    private String title;
    @TableField(value = "suggestion")
    private String suggestion;
    @TableField(value = "reply")
    private String reply;
    @TableField(value = "respondent")
    private String respondent;
    @TableField(value = "time")
    private LocalDateTime time;
    @TableField(value="mark")
    private int mark;
}
