package com.yuxing.trainee.common.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一接口响应格式
 *
 * @author yuxing
 */
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -5818131758898987235L;

    private Integer code;

    private String message;

    private T data;

    public Result() {
    }

    public Result(Respond respond) {
        this.code = respond.getCode();
        this.message = respond.getMessage();
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success() {
        return restResult(ResultCode.SUCCESS);
    }

    public static <T> Result<T> success(T data) {
        return restResult(data, ResultCode.SUCCESS);
    }

    public static <T> Result<T> failed(int code, String msg) {
        return restResult(null, code, msg);
    }

    public static <T> Result<T> failed() {
        return restResult(ResultCode.BAD_REQUEST);
    }

    public static <T> Result<T> failed(Respond respond) {
        return restResult(respond);
    }

    public static <T> Result<T> failed(String msg) {
        return restResult(null, ResultCode.BAD_REQUEST.getCode(), msg);
    }

    private static <T> Result<T> restResult(T data, Respond respond) {
        return restResult(data, respond.getCode(), respond.getMessage());
    }

    private static <T> Result<T> restResult(Respond respond) {
        return restResult(null, respond.getCode(), respond.getMessage());
    }

    private static <T> Result<T> restResult(T data, int code, String msg) {
        Result<T> apiResult = new Result<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMessage(msg);
        return apiResult;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>(3);
        result.put("code", this.code);
        result.put("message", this.message);
        if (this.data != null) {
            result.put("data", this.data);
        }
        return result;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
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
}
