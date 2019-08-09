package com.snailmann.tensquare.friend.service;

import com.snailmann.tensquare.friend.dao.FriendDao;
import com.snailmann.tensquare.friend.entity.Friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FriendService {

    @Autowired
    FriendDao friendDao;

    /**
     * userid用户喜欢/关注friendid用户
     * 开启事务
     *
     * @param userId
     * @param friendid
     * @return
     */
    public int addFriend(String userId, String friendid) {
        //判断user是否已经有该好友了
        Friend friend = friendDao.findByUseridAndAndFriendid(userId, friendid);
        if (friend != null) {
            //返回0，代表已经有该记录了，此操作属于业务重复
            return 0;
        }
        //直接添加好友
        friend = new Friend();
        friend.setUserid(userId);
        friend.setFriendid(friendid);
        friend.setFriendid("0");
        friendDao.save(friend);

        //判断friendid用户是否也对userid用户是喜欢状态，如果是，这把双方的喜欢状态都改为1，代表互相关注
        if (friendDao.findByUseridAndAndFriendid(friendid, userId) != null) {
            friendDao.updateIslike("1", userId, friendid);
            friendDao.updateIslike("1", friendid, userId);
        }

        return 1;
    }
}
