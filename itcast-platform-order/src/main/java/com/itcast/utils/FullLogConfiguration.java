package com.itcast.utils;

import feign.Logger;
import org.springframework.context.annotation.Bean;

public class FullLogConfiguration {
    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }
}