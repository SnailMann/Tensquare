package com.snailmann.tensquare.search.service;

import com.snailmann.tensquare.common.util.IdWorker;
import com.snailmann.tensquare.search.dao.ArticleDao;
import com.snailmann.tensquare.search.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private IdWorker idWorker;

    /**
     * 新增一篇文章
     *
     * @param article
     */
    public void save(Article article) {
        /*  article.setId(idWorker.nextId() + "");*/
        articleDao.save(article);
    }

    /**
     * 根据关键字查询ES中的内容，可能要匹配标题，也可能要匹配内容 | 查询数据分页
     *
     * @param key
     * @param page
     * @param size
     * @return
     */
    public Page<Article> findByKeyWord(String key, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return articleDao.findByTitleOrContentLike(key, key, pageable);
    }
}
