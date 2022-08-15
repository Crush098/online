package com.tzk.msm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = "com.tzk")
@EnableDiscoveryClient
public class MsmApplicaiton {
    public static void main(String[] args) {
        SpringApplication.run(MsmApplicaiton.class,args);
    }
}
