package com.snailmann.tensquare.friend.dao;

import com.snailmann.tensquare.friend.entity.Friend;
import com.snailmann.tensquare.friend.entity.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface NoFriendDao extends JpaRepository<NoFriend, String> {

    NoFriend findByUseridAndAndFriendid(String userid, String friendid);

}
