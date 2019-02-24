package com.snailmann.tensquare.search.controller;

import com.snailmann.tensquare.common.entity.PageResult;
import com.snailmann.tensquare.common.entity.Result;
import com.snailmann.tensquare.common.entity.StatusCode;
import com.snailmann.tensquare.search.entity.Article;
import com.snailmann.tensquare.search.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
@CrossOrigin
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 新增一篇文章
     *
     * @param article
     * @return
     */
    @PostMapping
    public Result save(@RequestBody Article article) {
        articleService.save(article);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    /**
     * 根据关键字查询ES中的内容，可能要匹配标题，也可能要匹配内容 | 查询数据分页
     *
     * @param keyword
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/{keyword}/{page}/{size}")
    public Result findByKeyWord(@PathVariable String keyword, @PathVariable int page, @PathVariable int size) {
        Page<Article> pageData = articleService.findByKeyWord(keyword, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageData.getTotalElements(), pageData.getContent()));
    }

}
