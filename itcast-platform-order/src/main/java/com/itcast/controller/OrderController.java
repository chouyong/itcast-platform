package com.itcast.controller;

import com.alibaba.fastjson.JSONObject;
import com.itcast.common.utils.JsonUtils;
import com.itcast.common.utils.ResponseUtils;
import com.itcast.dto.OrderInfo;
import com.itcast.dto.User;
import com.itcast.entity.Order;
import com.itcast.mapper.OrderMapper;
import com.itcast.service.UserService;
import com.itcast.utils.MqUtil;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/order")
public class OrderController {
    private Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RedissonClient redissonClient;

    @PostMapping(value = "/pay")
    public String pay(@RequestBody OrderInfo orderInfo) {
        String strJson = null;
        logger.info("订单入参参数：" + JsonUtils.toText(orderInfo));
        //获取锁
        RLock rlock = redissonClient.getLock(orderInfo.getUserName());
        try {
            //等待5秒，锁过期时间6秒，单位：秒
            boolean isLock = rlock.tryLock(5, 6, TimeUnit.SECONDS);
            if (isLock) {
                // 业务代码
                strJson = userService.searchUser(orderInfo.getUserName());
                //解析返回json
                JSONObject result = JSONObject.parseObject(strJson);
                //调用用户系统接口成功，进行下单逻辑处理
                if ("0".equals(result.getString("code"))) {
                    String data = result.getString("data");
                    User user = JSONObject.parseObject(data, User.class);
                    String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                    Order order = new Order();
                    order.setId(uuid);
                    order.setUserid(user.getId());
                    //发送消息队列
                    MqUtil.instance(host,port,username,password,virtualHost).sendMsg("orderQueue", JsonUtils.toText(order));
                    int payOrderResult = orderMapper.insert(order);
                    if(payOrderResult > 0){
                        strJson = JsonUtils.toText(ResponseUtils.success("下单成功",order));
                    }else{
                        strJson = JsonUtils.toText(ResponseUtils.failure("下单失败"));
                    }
                }
                //释放锁
                rlock.unlock();
            }
        } catch (InterruptedException e) {
            //e.printStackTrace();
            logger.error("获取锁异常：" + e);
            //释放锁
            rlock.unlock();
            strJson = JsonUtils.toText(ResponseUtils.failure("获取锁异常！"));
        }
        return strJson;
    }
}
