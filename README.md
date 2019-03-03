## demo-tensquare

十次方项目的学习代码


### 项目模块介绍

|项目名称|描述|端口号|数据库|备注|
| --- | --- | --- | --- | ---| 
| tensquare_parent | 十次方项目的父工程
| tensquare_common | 十次方项目的公共包
| tensquare_base   | 标签基础微服务 | 9001 | Mysql
| tensquare_recruit| 招聘模块微服务 | 9002 | Mysql
| tensquare_qa | 问答模块微服务 | 9003 | Mysql
| tensquare_aritcle | 文章模块微服务 | 9004 | Mysql,Redis
| tensquare_gathering | 活动模块微服务 | 9005 | Mysql,Redis
| tensquare_spit | 吐槽模块微服务 | 9006 | MongoDB, Redis
| tensquare_search | 文章模块微服务 | 9007 | ElasticSearch,(Logstash,Mysql)
| tensquare_user| 用户模块微服务| 9008 | Mysql,Redis,RabbitMQ


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