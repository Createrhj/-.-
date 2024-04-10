package com.system.common;

import lombok.Data;
/*后端数据的封装*/
@Data
public class Result {
    /*编码:*/
    private int code;
    /*结果*/
    private String msg;
    /*数据总数*/
    private int total;
    /*具体数据*/
    private Object data;

    public static Result fail(){
        return result(999,"失败",0,null);
    }

    public static Result success(){
        return result(888,"成功",0,null);
    }

    public static Result success(Object data){
        return result(888,"成功",0,data);
    }

    public static Result success(int total,Object data){
        return result(888,"成功",total,data);
    }

    public static Result result(int code,String msg,int total,Object data){
        Result result=new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setTotal(total);
        result.setData(data);
        return result;
    }
}
