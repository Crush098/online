package com.tzk.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan(basePackages = "com.tzk")
@SpringBootApplication
@EnableDiscoveryClient //nacos注册
@EnableFeignClients
public class EduApplicaton {
    public static void main(String[] args) {
        SpringApplication.run(EduApplicaton.class,args);
    }
}
