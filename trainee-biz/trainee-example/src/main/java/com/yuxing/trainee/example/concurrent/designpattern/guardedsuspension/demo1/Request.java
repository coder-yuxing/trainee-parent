package com.yuxing.trainee.example.concurrent.designpattern.guardedsuspension.demo1;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author yuxing
 * @since 2021/12/23
 */
@Data
@AllArgsConstructor
public class Request {

    private String id;

    private String content;
}
