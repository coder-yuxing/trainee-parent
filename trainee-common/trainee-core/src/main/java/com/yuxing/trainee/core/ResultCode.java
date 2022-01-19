package com.yuxing.trainee.core;

/**
 * 接口响应状态码
 *
 * @author yuxing
 */
public enum ResultCode implements Respond {

    /* 成功 */
    SUCCESS(1, "success"),

    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    PAYMENT_REQUIRED(402, "Payment Required"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    SESSION_TIMEOUT(440, "Session Timeout"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    ;



    public final int code;
    public final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
