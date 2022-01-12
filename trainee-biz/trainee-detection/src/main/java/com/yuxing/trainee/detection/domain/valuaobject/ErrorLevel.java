package com.yuxing.trainee.detection.domain.valuaobject;

public enum ErrorLevel {

    ERROR(1),

    WARN(2),

    ;
    ErrorLevel(int code) {
        this.code = code;
    }

    public final int code;
}