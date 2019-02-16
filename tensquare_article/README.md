## tensquare-article

文章模块微服务

### 要完成的模块

- 文章审核 
- 文章点赞




### JPA


- 简单模块crud，可以使用JpaRepository提供的方法完成，复杂操作可以继承JpaSpecificationExecutor
- 稍微复杂的单表操作可以使用方法名称映射SQL语句
- 联表操作可以通过@Query来写原生SQL
- 所有可能会产生线程安全问题的@Query都需要加上@Modifying注解


### Redis

- Redis缓存热点数据
- Redis删除数据
- Redis给数据设置过期时间