package com.snailmann.tensquare.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.snailmann.tensquare.user.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface UserDao extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    User findByMobile(String mobile);

    @Modifying
    @Query(value = "update tb_user set fanscount = fanscount + ? where id = ?", nativeQuery = true)
    void updateFansCount(int x, String userid);

    @Modifying
    @Query(value = "update tb_user set followcount = followcount + ? where id = ?", nativeQuery = true)
    void updateFollowCount(int x, String friendid);
}
