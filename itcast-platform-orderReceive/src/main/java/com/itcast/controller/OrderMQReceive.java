package com.itcast.controller;

import com.alibaba.fastjson.JSON;
import com.itcast.entity.Order;
import com.itcast.mapper.OrderMapper;
import com.itcast.utils.IDUtil;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

@Component
public class OrderMQReceive {

    private Logger logger = LoggerFactory.getLogger(OrderMQReceive.class);

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RedissonClient redissonClient;

    //监听延迟订单队列
    @RabbitListener(queues = "${register.delay.queue.name}",containerFactory = "singleListenerContainer")
    public void process(@Payload Order record) {
        logger.info("抢购队列入参参数：" + JSON.toJSONString(record));
        //获取锁
        RLock rlock = redissonClient.getLock(record.getUserId()+record.getGoodsId()+record.getCampaignId());
        try {
            //等待5秒，锁过期时间6秒，单位：秒
            boolean isLock = rlock.tryLock(5, 6, TimeUnit.SECONDS);
            if (isLock) {
                //订单号
                String orderId = IDUtil.getInstance().createID();
                record.setId(orderId);
                int payOrderResult = orderMapper.insert(record);
                //释放锁
                rlock.unlock();
            }
        } catch (InterruptedException e) {
            //e.printStackTrace();
            logger.error("获取锁异常：" + e);
            //释放锁
            rlock.unlock();
        }
    }

    //监听延迟订单转发队列
    @RabbitListener(queues = "${register.queue.name}",containerFactory = "multiListenerContainer")
    public void consumeMessage(@Payload Order record){
        try {
            logger.info("消费者监听延迟队列交易记录信息： {} ", JSON.toJSONString(record));
            //TODO：表示已经到ttl了，却还没付款，则需要处理为失效
        }catch (Exception e){
            logger.error("消息体解析 发生异常； ",e.fillInStackTrace());
        }
    }
}
