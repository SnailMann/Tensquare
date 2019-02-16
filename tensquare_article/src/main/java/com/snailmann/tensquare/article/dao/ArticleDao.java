package com.snailmann.tensquare.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.snailmann.tensquare.article.entity.Article;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface ArticleDao extends JpaRepository<Article, String>, JpaSpecificationExecutor<Article> {

    /**
     * 文章审核
     * 赠三改，所有可能会产生线程安全问题的都需要加@Modifying注解
     */
    @Modifying
    @Query(value = "update tb_article set state = 1 where id = ?1", nativeQuery = true)
    public void updateState(String id);


    /**
     * 文章点赞数，既点赞 + 1
     *
     * @param id
     */
    @Modifying
    @Query(value = "update tb_article set thumbup = thumbup + 1 where id = ?1", nativeQuery = true)
    public void addThumbup(String id);

}
