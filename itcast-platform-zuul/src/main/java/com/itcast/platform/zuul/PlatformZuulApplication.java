package com.itcast.platform.zuul;

import com.itcast.platform.zuul.filter.AuthZuulFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
@SpringBootApplication
public class PlatformZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatformZuulApplication.class, args);
    }

    @Bean
    public AuthZuulFilter authZuulFilter() {
        AuthZuulFilter filter = new AuthZuulFilter();
        return filter;
    }
}
