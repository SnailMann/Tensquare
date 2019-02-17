package com.snailmann.tensquare.spit.dao;

import com.snailmann.tensquare.spit.entity.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 类似jpa一样，Dao的接口需要继承一个Repository，但是不像JPA一样复杂操作还需要继承一个JpaSpecificationExecutor
 * 因为mongodb本身就是非关系型数据库，复杂能复杂不了那里去
 * 一样，第一个泛型是要操作的实体类，第二个泛型是主键的类型
 */
public interface SpitDao extends MongoRepository<Spit,String> {

    /**
     * 根据上级Id查询吐槽数据 | 分页查询
     * @param parentid
     * @param pageable
     * @return
     */
    Page<Spit> findByParentid(String parentid, Pageable pageable);
}
