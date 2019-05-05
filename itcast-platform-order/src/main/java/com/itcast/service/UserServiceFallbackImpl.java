package com.itcast.service;

import com.itcast.common.dto.Response;
import com.itcast.common.utils.JsonUtils;
import com.itcast.common.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
//@Service
public class UserServiceFallbackImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceFallbackImpl.class);

    @Override
    public String searchUser(String userName){
        LOGGER.error("用户信息查询接口调用异常:searchUser");
        return JsonUtils.toText(ResponseUtils.failure("调用用户信息查询接口服务异常！"));
    }
}
