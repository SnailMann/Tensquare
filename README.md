## demo-tensquare

十次方项目的学习代码


### 项目模块介绍

- Tensquare子项目

|项目名称|描述|端口号|数据库|备注|
| --- | --- | --- | --- | ---| 
| tensquare_parent | 十次方项目的父工程
| tensquare_common | 十次方项目的公共包
| tensquare_base   | 标签基础微服务 | 9001 | MySQL
| tensquare_recruit| 招聘模块微服务 | 9002 | MySQL
| tensquare_qa | 问答模块微服务 | 9003 | MySQL
| tensquare_aritcle | 文章模块微服务 | 9004 | MySQL,Redis
| tensquare_gathering | 活动模块微服务 | 9005 | MySQL,Redis
| tensquare_spit | 吐槽模块微服务 | 9006 | MongoDB, Redis
| tensquare_search | 文章模块微服务 | 9007 | ElasticSearch,(Logstash,MySQL)
| tensquare_user| 用户模块微服务| 9008 | MySQL,Redis,RabbitMQ
| tensquare_sms| 消息模块微服务| 9008 | RabbitMQ
| tensquare_friend| 用户交友微服务| 9010 | MySQL


- Cloud子项目

|项目名称|描述|端口号|数据库|备注|
| --- | --- | --- | --- | ---| 
| cloud_eureka | 注册中心 | 6868,6869,6870
| cloud_zuul | 网关 | 9011
| cloud_hystrix_dashboard | hystrix dashboard | 9100



### 学习内容

- Docker的使用
- Spring Data JPA的使用
- Spring Cache的使用
- Redis的使用
- MongoDB的使用
- Elasticsearch搜索引擎的使用
- Logstash的使用
- RabbitMQ的使用
- Jwt的使用 | 无状态登录