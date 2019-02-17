## tensquare-spit

吐槽模块微服务


## 要做的功能

- 吐槽相关 | 吐槽可以有评论，评论属于子吐槽
- 查看评论 | 评论也是一种吐槽，同时也可以由子吐槽
- 点赞 | 不能重复点赞


Redis用于控制不能重复点赞，其余数据全部存储到MongoDB中

## 吐槽表

|字段名|字段含义| 字段类型 | 备注|
| --- |  --- | --- | --- |
|_id|ID|文本|
|content|吐槽内容|文本|
|publishtime|发布日期|日期|
|userid|发布人Id|文本|
|nickname|发布人昵称|文本|
|visits|浏览量|整型|
|thumbup|点赞数|整型|
|share|分享数|整型|
|comment|回复数|整型|
|state|是否可见|文本| 1：可见|
|parentid|父级Id|文本| 父吐槽层级的Id


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

