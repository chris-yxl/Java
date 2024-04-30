package com.example.demo.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Optional;

/**
 * @author  chris
 * @className ResponseResult
 * @date
 * @description:
 */
public class ResponseResult<T> implements Serializable {

    private static final long serialVersionUID = 8783521927948631083L;
    private String code;
    private String message;
    private transient T data;
    private long total;
    private String traceID;
    private String reminderType;

    public ResponseResult(){
        ServletRequestAttributes sra = (ServletRequestAttributes)Optional.ofNullable(RequestContextHolder.getRequestAttributes()).orElse(null);
        HttpServletRequest request = sra == null ? null :sra.getRequest();
        String traceId= null;
        if (request !=null){
            traceId=(String) Optional.ofNullable(request.getAttribute("traceid")).orElse((Object) null);
        }
        this.traceID=traceId;
    }

    public ResponseResult(T data){ this("00000","success",data);}

    public ResponseResult(String code, String message){this(code,message, null);}

    public ResponseResult(String code, String message, T data){
        this();
        this.code=code;
        this.message=message;
        this.data=data;
    }

    public ResponseResult(String code, String message, T data, String reminderType){
        this();
        this.code=code;
        this.message=message;
        this.data=data;
        this.reminderType=reminderType;
    }

    public static <T> ResponseResult<T> success(T data){return new ResponseResult(data);}

    public static <T> ResponseResult<T> success(){return new ResponseResult((Object) null);}

    public static <T> ResponseResult<T> fail(String code, String message,T data){
        return new ResponseResult(code, message,data);
    }

    public static <T> ResponseResult<T> fail(String code, String message){
        return new ResponseResult(code, message);
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public String getTraceID() {
        return this.traceID;
    }

    public void setTraceID(String traceID) {
        this.traceID = traceID;
    }

    public String getReminderType() {
        return this.reminderType;
    }

    public void setReminderType(String reminderType) {
        this.reminderType = reminderType;
    }
}
