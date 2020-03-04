package com.ningmeng.goven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by 周周 on 2020/2/23.
 */
@EnableEurekaServer   // 标识Eureka服务
@SpringBootApplication
public class GovernGenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(GovernGenterApplication.class, args);
    }
}
