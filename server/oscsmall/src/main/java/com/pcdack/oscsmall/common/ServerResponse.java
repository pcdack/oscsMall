package com.pcdack.oscsmall.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * Created by pcdack on 17-9-6.
 *
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//不存在Null的Value
public class ServerResponse<T> implements Serializable{
    private int status;
    private String msg;
    private T data;

    private ServerResponse(int status) {
        this.status = status;
    }

    private ServerResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private ServerResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private ServerResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
    @JsonIgnore
    //不进行序列化
    public boolean isSuccess(){
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public int getStatus(){
        return status;
    }

    /**
     * 请求成功
     */
    public static <T> ServerResponse<T> createBySuccess(){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
    }
    public static <T> ServerResponse<T> createBySuccess(String msg){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg);
    }
    public static <T> ServerResponse<T> createBySuccess(T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),data);
    }
    public static <T> ServerResponse<T> createBySuccess(String msg,T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg,data);
    }
    /**
     * 请求失败
     */
    public static <T> ServerResponse<T> createByError(){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getMsg());
    }
    public static <T> ServerResponse<T> createByErrorMessage(String errorMsg){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),errorMsg);
    }
    /**
     * 其他情况
     */
    public static <T> ServerResponse<T> createByErrorCodeMessage(int code,String msg){
        return new ServerResponse<T>(code,msg);
    }



}
