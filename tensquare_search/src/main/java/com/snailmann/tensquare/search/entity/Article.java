package com.snailmann.tensquare.search.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

/**
 * Document注解是映射到ElasticSearch中的
 * Java实体肯定是一个文档，但文档是属于ES概念中的那个索引的那个类型？
 * indexName标明属于什么索引，type表示属于什么类型
 * <p>
 * Article实际对应tensqure_article数据库的tb_article表，但是ES中不需要数据库中这么完整的数据
 * 只需要向用户展示部分数据即可
 */
@Document(indexName = "tensquare_article", type = "article")
@Data
public class Article implements Serializable {

    /**
     * id
     */
    @Id
    private String id;

    /**
     * 标题
     * Field在SQL中是列的意思，在ES是文档的某个字段属性，其实差不多
     * index=true参数，给该属性索引
     * 是否索引：代表着该域是否能被搜索
     * 是否分词：就表示搜索的时候是整体匹配还是单词匹配
     * 是否存储：是否在页面上显示 | 比如搜索引擎中的搜索出来的东西中，内容太多，肯定是无法显示全的，所以有时候我们只想存储一个概要描述，而非存整个内容
     */
    @Field(index = true, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String title;

    /**
     * 内容
     * analyzer代表存储时使用该分词器，searchAnalyzer代表搜索使用该分词器，所以一定要一致
     */
    @Field(index = true, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String content;

    /**
     * 审核状态
     */
    private String state;


}
