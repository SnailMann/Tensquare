package com.snailmann.tensquare.base.dao;

import com.snailmann.tensquare.base.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 标签Dao层
 * JpaRepository的泛型第一个是实体类类型，第二个实体类主键的类型
 * JpaSpecificationExecutor是复杂操作，比如条件查询，分页查询等
 * @author SnailMann
 */
public interface LabelDao extends JpaRepository<Label,String>, JpaSpecificationExecutor<Label> {
}
