package com.snailmann.tensquare.search.dao;

import com.snailmann.tensquare.search.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * article的dao层
 */
public interface ArticleDao extends ElasticsearchRepository<Article, String> {

    /**
     * es依然可以使用sql的方式去查询，这是Spring Data做的统一适配
     *
     * @param title
     * @param content
     * @param pageable
     * @return
     */
    public Page<Article> findByTitleOrContentLike(String title, String content, Pageable pageable);
}
