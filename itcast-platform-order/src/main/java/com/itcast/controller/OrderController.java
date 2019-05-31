package com.itcast.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itcast.common.utils.JsonUtils;
import com.itcast.common.utils.ResponseUtils;
import com.itcast.dto.OrderInfo;
import com.itcast.dto.ResultMsg;
import com.itcast.entity.Order;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

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
    private Environment env;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping(value = "/pay")
    public String pay(@RequestBody OrderInfo orderInfo) {
        String strJson = null;
        logger.info("订单入参参数：" + JsonUtils.toText(orderInfo));
        Order order = new Order();
        order.setUserId(orderInfo.getUserId());
        order.setGoodsId(orderInfo.getGoodsId());
        order.setCampaignId(orderInfo.getCampaignId());
        //发送消息队列
        //TODO：设置超时，用mq处理已超时的下单记录（一旦记录超时，则处理为无效）
        final Long ttl=env.getProperty("trade.record.ttl",Long.class);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setExchange(env.getProperty("register.delay.exchange.name"));
        rabbitTemplate.setRoutingKey("");
        rabbitTemplate.convertAndSend(order, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,"testUser");
                message.getMessageProperties().setExpiration(ttl+"");
                return message;
            }
        });

        //ResultMsg resultMsg = MqUtil.instance(host,port,username,password,virtualHost).sendMsg("orderQueue", JsonUtils.toText(order));
        ResultMsg resultMsg = new ResultMsg(true);
        if(resultMsg.getResult()){
            strJson = JsonUtils.toText(ResponseUtils.success("抢购信息发送成功",order));
        }else{
            strJson = JsonUtils.toText(ResponseUtils.success("抢购信息发送失败",order));
        }
        return strJson;
    }
}
