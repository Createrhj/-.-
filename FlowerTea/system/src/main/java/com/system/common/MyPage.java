package com.system.common;

import lombok.Data;

import java.util.HashMap;
/*分页查询的数据封装*/
@Data
public class MyPage {
    private static int PAGE_SIZE=10;
    private static int PAGE_NUM=1;
    /*一页含多少条*/
    private int pageSize=PAGE_SIZE;
    private int pageNum=PAGE_NUM;
    /*存储页面数据*/
    private HashMap param=new HashMap();
}
