package com.yuxing.trainee.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 搜索服务启动类
 *
 * @author yuxing
 * @since 2022/1/14
 */
@EnableDiscoveryClient
@SpringBootApplication
public class TraineeSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(TraineeSearchApplication.class, args);
    }
}
