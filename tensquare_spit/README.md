## tensquare-spit

吐槽模块微服务


## 要做的功能

- 吐槽相关
- 查看评论
- 点赞 


Redis用于控制不能重复点赞，其余数据全部存储到MongoDB中

## 为什么使用MongoDB?

比如吐槽和评论两项功能，它们存在以下特点：

- 数据量大
- 写入操作频繁
- 价值较低

对于这样的数据，我们更适合使用MongoDB来实现数据的存储
## Spring Data MongoDB

Spring Data MongoDB 提供了多种访问数据的方式： 
- 使用Query和Criteria类 | MongoTemplate
- MongoRepository自动生成的查询方法
- 方法名称映射MongoDB语法
- 使用@Query 注解基于JSON查询