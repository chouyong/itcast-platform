package com.itcast;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableFeignClients
@EnableCircuitBreaker  //开启断路器
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.itcast.mapper")
public class OrderReceiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderReceiveApplication.class, args);
    }
}
