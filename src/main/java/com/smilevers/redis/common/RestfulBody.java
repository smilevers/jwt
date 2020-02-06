package com.smilevers.redis.common;


/**
 * @Author: smile
 * @Date: 2020/2/3
 */
public class RestfulBody<T> {
    
    private Integer code;
    private String token;
    private String msg;
    private T list;
    
    public RestfulBody(Integer code, String token, String msg, T list) {
        this.code = code;
        this.token = token;
        this.msg = msg;
        this.list = list;
    }
    
    
    public RestfulBody(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    public RestfulBody(Integer code, String token, String msg) {
        this.code = code;
        this.token = token;
        this.msg = msg;
    }
    
    public Integer getCode() {
        return code;
    }
    
    public void setCode(Integer code) {
        this.code = code;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public T getList() {
        return list;
    }
    
    public void setList(T list) {
        this.list = list;
    }
}
