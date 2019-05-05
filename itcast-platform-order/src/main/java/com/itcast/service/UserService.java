package com.itcast.service;

import com.itcast.common.dto.Response;
import com.itcast.utils.FullLogConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "PLATFORM-USER", configuration = FullLogConfiguration.class, fallback = UserServiceFallbackImpl.class)
public interface UserService {
    /***
     * 用户信息查询
     */
    @GetMapping(value = "/user/search")
    public String searchUser(@RequestParam("userName") String userName);
}