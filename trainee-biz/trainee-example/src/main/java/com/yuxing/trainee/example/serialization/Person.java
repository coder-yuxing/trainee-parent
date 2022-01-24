package com.yuxing.trainee.example.serialization;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yuxing
 * @since 2022/1/24
 */
@Data
public class Person implements Serializable {

    private static final long serialVersionUID = 4645918375944963622L;

    static String country = "ITALY";
    private int age;
    private String name;
    transient int height;
}
