package com.snailmann.tensquare.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.snailmann.tensquare.qa.entity.Problem;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 回答 | 数据访问接口
 * 使用原始的SQL语句书写，所以nativeQuery = true
 *
 * @author Administrator
 */
public interface ProblemDao extends JpaRepository<Problem, String>, JpaSpecificationExecutor<Problem> {

    /**
     * 某个标签的最新回答列表 | 分页查询
     *
     * @return
     */
    @Query(value = "select * from tb_problem problem,tb_pl pl where problem.id = pl.problemid and pl.labelid = ?1 order by problem.replytime desc"
            , nativeQuery = true)
    Page<Problem> newList(String lableId, Pageable pageable);

    /**
     * 某个标签的热门回答列表 | 分页查询
     * 最热门的回答就是回复最多的回答
     *
     * @return
     */
    @Query(value = "select * from tb_problem problem,tb_pl pl where problem.id = pl.problemid and pl.labelid = ?1 order by problem.reply desc"
            , nativeQuery = true)
    Page<Problem> hotList(String lableId, Pageable pageable);


    /**
     * 某个标签的等待回答列表 | 分页查询
     * 等待的回答就是回复数为0的回答
     *
     * @return
     */
    @Query(value = "select * from tb_problem problem,tb_pl pl where problem.id = pl.problemid and pl.labelid = ?1 and problem.reply = 0 order by problem.createtime desc"
            , nativeQuery = true)
    Page<Problem> waitList(String lableId, Pageable pageable);


}
