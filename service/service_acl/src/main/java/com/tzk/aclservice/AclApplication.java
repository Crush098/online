package com.tzk.aclservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AclApplication {
    public static void main(String[] args) {
        SpringApplication.run(AclApplication.class,args);
    }
}
