## tensquare-search

文章微服务模块



### ElasticSearch的三个概念
- 是否索引：代表着该域是否能被搜索
- 是否分词：就表示搜索的时候是整体匹配还是单词匹配
- 是否存储：是否在页面上显示 | 比如搜索引擎中的搜索出来的东西中，内容太多，肯定是无法显示全的，所以有时候我们只想存储一个概要描述，而非存整个内容


### Logstash同步数据

- 使用Logstash去同步MySQL中的数据到ElasticSearch
- 有logstash模板，在项目的Logstash目录中

