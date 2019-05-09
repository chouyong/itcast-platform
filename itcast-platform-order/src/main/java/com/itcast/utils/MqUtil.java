package com.itcast.utils;

import com.rabbitmq.client.*;
import com.itcast.dto.EEnum;
import com.itcast.dto.ResultMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MqUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(MqUtil.class);

    ConnectionFactory factory=null;
    private static MqUtil instanceCurrent=null;
    private static Object syncRoot=new Object();

    public static MqUtil instance(String host, int port, String username, String password, String virtualHost){
        if(instanceCurrent==null){
            synchronized (syncRoot){
                if(instanceCurrent==null){
                    instanceCurrent=new MqUtil(host,port,username,password,virtualHost);
                }
            }
        }
        return instanceCurrent;
    }

    public MqUtil(String host, int port, String username, String password, String virtualHost){
        try {
            factory=new ConnectionFactory();
            factory.setHost(host);
            factory.setPort(port);
            factory.setUsername(username);
            factory.setPassword(password);
            factory.setVirtualHost(virtualHost);
            factory.setAutomaticRecoveryEnabled(true);//自动连接
        }
        catch (Exception e){
            LOGGER.error("失败：mq连接失败！");
        }
    }
    
    public ResultMsg sendMsg(String queueName, String msg){
        ResultMsg resultMsg = new ResultMsg(false);
        Connection connection=null;
        Channel channel=null;
        try {
            LOGGER.info("队列名称："+queueName+"发送队列信息"+msg);
            connection=factory.newConnection();
            channel=connection.createChannel();
            
            channel.exchangeDeclare("e-"+queueName+"Dead","direct",true);//死信路由
            channel.exchangeDeclare("e-"+queueName,"direct",true);//正常路由
            
            //创建死信队列-并绑定到正常路由
            Map map=new HashMap();
            map.put("x-dead-letter-exchange","e-"+queueName);
            map.put("x-dead-letter-routing-key", "key");
            //消息过期时间 单位毫秒 15*60*1000 15分钟
            map.put("x-message-ttl", 15*60*1000);
            channel.queueDeclare(queueName+"Dead",true,false,false,map);
            channel.queueBind(queueName+"Dead","e-"+queueName+"Dead","key");

            //创建正常队列-并绑定到死信路由
            map.clear();
            map.put("x-dead-letter-exchange","e-"+queueName+"Dead");
            map.put("x-dead-letter-routing-key", "key");
            channel.queueDeclare(queueName,true,false,false,map);
            channel.queueBind(queueName,"e-"+queueName,"key");

            channel.basicPublish("e-"+queueName,"key",null,msg.getBytes());
            resultMsg=new ResultMsg(true);
            resultMsg.setMsg("成功：发送队列信息成功！");
        }catch (Exception e){
            resultMsg=new ResultMsg(false);
            resultMsg.setMsg(e.toString());
            LOGGER.error(e.toString());
        }finally {
            try {
                if(channel != null){
                	channel.close();
                }
                if(connection != null){
                	connection.close();
                }
            }catch (Exception e){
                LOGGER.error(e.toString()+"关闭队列异常");
            }
        }
        return resultMsg;
    }
    
    public ResultMsg consumerMsg(String queueName){
        ResultMsg resultMsg = new ResultMsg(false);
        try {
            Connection connection=factory.newConnection();
            Channel channel=connection.createChannel();

            //创建正常队列-并绑定到死信路由
            Map map=new HashMap();
            map.put("x-dead-letter-exchange","e-"+queueName+"Dead");
            map.put("x-dead-letter-routing-key", "key");
            channel.queueDeclare(queueName,true,false,false,map);
            channel.queueBind(queueName,"e-"+queueName,"key");

            channel.basicQos(1);
            Consumer consumer=new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String msg="";
                    EEnum.MqAction mqAction=EEnum.MqAction.RETRY;
                    try {
                        msg=new String(body,"UTF-8");
                        mqAction=consumerQueueName(queueName,msg);
                    }
                    catch (Exception e){
                        LOGGER.error(e.toString());
                    }
                    finally {
                        switch (mqAction){
                            case ACCEPT:
                                channel.basicAck(envelope.getDeliveryTag(),false);//接受
                                break;
                            case RETRY:
                                channel.basicNack(envelope.getDeliveryTag(), false, true);//回退
                                break;
                            case REJECT:
                            case DEATH:
                                channel.basicReject(envelope.getDeliveryTag(), false);//拒绝进去死信
                                break;
                            default:
                                channel.basicNack(envelope.getDeliveryTag(), false, true);//回退
                                break;
                        }
                    }
                }
            };
            channel.basicConsume(queueName,false,consumer);
            resultMsg=new ResultMsg(true);
            resultMsg.setMsg("成功:连接成功!");
        }catch (Exception e){
            resultMsg=new ResultMsg(false);
            resultMsg.setMsg(e.toString());
            LOGGER.error(e.toString());
        }
        return resultMsg;
    }
    
    private EEnum.MqAction consumerQueueName(String queueName,String msg){
        EEnum.MqAction mqAction=EEnum.MqAction.RETRY;
        try {
            LOGGER.info("消费队列名称："+queueName+"发送队列信息"+msg);
            switch (queueName){
                case "hello":
                    mqAction=EEnum.MqAction.ACCEPT;
                    if(msg.contains("20") || msg.contains("50")){
                        mqAction=EEnum.MqAction.DEATH;
                    }
                    break;
                default:
                    break;
            }
        }catch (Exception e){
            LOGGER.error(e.toString());
            mqAction=EEnum.MqAction.DEATH;
        }
        return mqAction;
    }
}
