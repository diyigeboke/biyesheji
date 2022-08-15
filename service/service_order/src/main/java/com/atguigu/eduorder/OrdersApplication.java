package com.atguigu.eduorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu"})
@MapperScan("com.atguigu.eduorder.mapper")
@EnableFeignClients
@EnableDiscoveryClient
public class OrdersApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrdersApplication.class,args);
    }
}
