package com.snailmann.tensquare.recruit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.snailmann.tensquare.recruit.entity.Enterprise;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface EnterpriseDao extends JpaRepository<Enterprise,String>,JpaSpecificationExecutor<Enterprise>{

    /**
     * 查询热门企业列表
     * 这里使用jpa的方法命名映射SQL来实现,名字有代码提示，所以也非常的方便
     * @param isHot
     * @return
     */
     List<Enterprise> findByIshot(String isHot);
	
}
