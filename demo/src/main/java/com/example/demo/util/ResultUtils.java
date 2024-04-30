package com.example.demo.util;

/**
 * @author  chris
 * @className ResultUtils
 * @date
 * @description:
 */
public class ResultUtils {
    public ResultUtils(){}

    public static <T> ResponseResult<T> wrapResult(T t){return new ResponseResult(t);}

    public static <T> ResponseResult<T> wrapResult(T t,int total){
        ResponseResult<T> rs=new ResponseResult();
        rs.setCode("00000");
        rs.setData(t);
        rs.setTotal((long)total);
        return rs;
    }

    public static <T> ResponseResult<T> wrapResult(String code, String message){
        return new ResponseResult(code,message);
    }

    public static <T> ResponseResult<T> wrapResult(String code, String message,String reminderType){
        return new ResponseResult(code,message,(Object) null,reminderType);
    }
}
