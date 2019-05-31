
为现实秒杀场景，搭建后端spring cloud微服务架构，前端可以使用vue或者其他框架。

1.后端框架包括：2个注册中心eureka，2个api网关zuul，客户端负载均衡ribbon，声明式服务调用feign，断路器hystrix，同时还包括两个后端业务服务，一个用户服务，一个订单服务。

2.流程说明：用户从浏览器或者app等终端发起http请求，通过cdn加速和dns域名解析后，请求到达Nginx后，Nginx对网关层进行负载，因为网关也需要多HA。
  此时网关Gateway接收到请求后，根据请求路径进行动态路由，通过注册中心eureka服务发现是订单中的服务，则从ribbon中选择一台订单的实例进行调用，订单实例中如需要其他服务信息(例如用户服务信息)，
  再通过声明式服务调用feign调用其他服务接口信息，将数据业务处理后，反馈给前端，流程结束。

3.用到相关服务和技术：redis缓存，mysql数据库，rabbitMQ队列，Nginx，redisson分布式锁，Mybatis持久层框架，Mybatis的分页插件pagehelper，mybatis-generator代码自动生成工具，spring cloud微服务组件。JDK1.8,maven-3.3.9。

4.系统服务配置：
  操作系统hosts文件配置内容：127.0.0.1    www.buba.com
  Nginx路径下conf目录下nginx.conf文件配置内容：
    
    http {
         upstream  backServer {
        	server 127.0.0.1:7000 weight=1;
        	server 127.0.0.1:8000 weight=1;
         }         
         server {
            server_name  www.buba.com;
            location / {
                proxy_pass http://backServer;
            }
         }
    }
    
5.post请求：请求url：http://www.buba.com/orderservice/order/pay
  Headers内容：Content-Type：application/json  和  userToken：zhangsan
  请求入参json参数：{"userName":"zhangsan"}
  如果下单成功请求出参json参数：{"code":"0","msg":"下单成功","timestamp":1557127591533,"data":{"id":"9f361c558cb340f0aa76449fa0c4a8f0","userid":"1"}}

6.运行微服务应用前必须启动redis服务，rabbitMQ服务，Nginx服务和mysql服务：业务应用按顺序启动itcast-platform-eureka，itcast-platform-zuul，itcast-platform-order和itcast-platform-user

7.架构模块介绍：从名称中即可理解每个module的作用。其中itcast-common是一个公共基础包，方便给后台服务引用。itcast-parent为各个module的父pom，统一对各子模块依赖版本进行管理。

8.itcast-platform-eureka和itcast-platform-zuul模块中application-prod.yml有两个profiles: peer1和peer2，双实例部署测试高并发高可用。两个后端业务服务根据测试和生产需求，进行多实例部署测试。

9.启动多实例多profiles指令：java -jar itcast-platform-zuul-1.0.0.jar --spring.profiles.active=prod,peer1 或 java -jar itcast-platform-zuul-1.0.0.jar --spring.profiles.active=prod,peer2

10.数据库表初始化脚本：
    
    CREATE TABLE
        USER
        (
            ID VARCHAR(128) COLLATE utf8_general_ci NOT NULL,
            USER_NAME VARCHAR(120) COLLATE utf8_general_ci,
            PASSWORD VARCHAR(64) COLLATE utf8_general_ci,
            PRIMARY KEY (ID)
        )
        ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
      
    CREATE TABLE
        TDORDER
        (
            ID VARCHAR(128) COLLATE utf8_general_ci NOT NULL,
            USER_ID VARCHAR(128) COLLATE utf8_general_ci,
            GOODS_ID VARCHAR(128) COLLATE utf8_general_ci,
            CAMPAIGN_ID VARCHAR(128) COLLATE utf8_general_ci,
            PRIMARY KEY (ID)
        )
        ENGINE=InnoDB DEFAULT CHARSET=utf8mb4; 
        
    INSERT INTO `user` (`ID`, `USER_NAME`, `PASSWORD`) VALUES('1','zhangsan','123456');
    
    
    
    
    
    
         
      












