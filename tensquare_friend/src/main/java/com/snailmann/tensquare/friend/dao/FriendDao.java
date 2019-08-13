package com.snailmann.tensquare.friend.dao;

import com.snailmann.tensquare.friend.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FriendDao extends JpaRepository<Friend, String> {

    Friend findByUseridAndAndFriendid(String userid, String friendid);

    @Modifying
    @Query(value = "update tb_friend set islike = ? where userid = ? and friendid = ?",nativeQuery = true)
    void updateIslike(String islike , String userid, String friendid);

    @Modifying
    @Query(value = "delete tb_friend where userid = ? and friendid = ?",nativeQuery = true)
    void deleteFriend();
}
