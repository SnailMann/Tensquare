package com.snailmann.tensquare.recruit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.snailmann.tensquare.recruit.entity.Recruit;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface RecruitDao extends JpaRepository<Recruit,String>,JpaSpecificationExecutor<Recruit>{

    /**
     * 查询前六个最热门的职位信息
     * where state = ? order by createtime limit 6
     * @return
     */
     List<Recruit> findTop6ByStateOrderByCreatetimeDesc(String state);

    /**
     * 查询前六个最新的职位信息
     * where state != 0 order by createtime limit 6
     * @param state
     * @return
     */
     List<Recruit> findTop6ByStateNotOrderByCreatetimeDesc(String state);
}
