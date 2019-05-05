package com.itcast.controller;

import com.itcast.common.dto.Response;
import com.itcast.common.utils.JsonUtils;
import com.itcast.common.utils.ResponseUtils;
import com.itcast.entity.User;
import com.itcast.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserMapper userMapper;

    @ResponseBody
    @RequestMapping("/search")
    public String searchUser(String userName){
        logger.info("用户查询入参 userName：" + userName);
        User user = userMapper.selectByUsername(userName);
        Response response = null;
        if(user != null){
            response = ResponseUtils.success("用户信息查询成功！",user);
        }else{
            response = ResponseUtils.failure("没有查询到用户信息！");
        }
        return JsonUtils.toText(response);
    }
}
