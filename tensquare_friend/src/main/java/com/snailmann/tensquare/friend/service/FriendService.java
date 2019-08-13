package com.snailmann.tensquare.friend.service;

import com.snailmann.tensquare.friend.dao.FriendDao;
import com.snailmann.tensquare.friend.dao.NoFriendDao;
import com.snailmann.tensquare.friend.entity.Friend;
import com.snailmann.tensquare.friend.entity.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FriendService {

    @Autowired
    FriendDao friendDao;

    @Autowired
    NoFriendDao noFriendDao;

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
        friend.setIslike("0");
        friendDao.save(friend);

        //判断friendid用户是否也对userid用户是喜欢状态，如果是，这把双方的喜欢状态都改为1，代表互相关注
        if (friendDao.findByUseridAndAndFriendid(friendid, userId) != null) {
            friendDao.updateIslike("1", userId, friendid);
            friendDao.updateIslike("1", friendid, userId);
        }

        return 1;
    }

    /**
     * 对于userid用户，将friendid用户设置为不喜欢的用户，类似黑名单，但又更轻微
     *
     * @param userid
     * @param friendid
     * @return
     */
    public int addNorFriend(String userid, String friendid) {
        NoFriend noFriend = noFriendDao.findByUseridAndAndFriendid(userid, friendid);
        if (noFriend != null) {
            return 0;
        }

        noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
        return 1;
    }

    /**
     * 取消关注，删除好友,将删除好友加入不关注列表（既不想在发现好友列表中看见）
     *
     * @param userId
     * @param friendid
     */
    public void deleteFriend(String userId, String friendid) {
        //删除好友表中userid对friendid的记录
        friendDao.deleteFriend();
        //更新friendid对userid的islike状态为0
        friendDao.updateIslike("0", friendid, userId);
        //插入userid对friendid的记录到非好友表
        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userId);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);


    }
}
