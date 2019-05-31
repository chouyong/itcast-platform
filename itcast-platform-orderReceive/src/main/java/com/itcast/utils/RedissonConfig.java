package com.itcast.utils;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {
    @Value("${redisson.single-address}")
    private String singleAddress;

    @Value("${redisson.single-password}")
    private String singlePassword;

    @Value("${redisson.sentinel-master-name}")
    private String sentinelMasterName;

    @Value("${redisson.sentinel-addresses}")
    private String sentinelAddresses;

    @Value("${redisson.sentinel-password}")
    private String sentinelPassword;

    /**
     * 单机模式自动装配
     * @return
     */
    @Bean
    @ConditionalOnProperty(name="redisson.single-is",havingValue="true")
    public RedissonClient getSingleRedisson(){
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer().setAddress("redis://" + singleAddress).setPassword(singlePassword);
        if(StringUtils.isNotBlank(singlePassword)){
            serverConfig.setPassword(singlePassword);
        }
        return Redisson.create(config);
    }

    /**
     * 哨兵模式自动装配
     * @return
     */
    @Bean
    @ConditionalOnProperty(name="redisson.sentinel-is",havingValue="true")
    public RedissonClient getSentinelRedisson(){
        Config config = new Config();
        SentinelServersConfig serverConfig = config.useSentinelServers().addSentinelAddress(sentinelAddresses).setMasterName(sentinelMasterName);
        if(StringUtils.isNotBlank(sentinelPassword)) {
            serverConfig.setPassword(sentinelPassword);
        }
        return Redisson.create(config);
    }
}
