package com.snailmann.tensquare.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("tensquare-user")
public interface UserClient {

    /**
     * 更新用户的关注数和对应好友的粉丝数
     * 既当前userid关注了friendid用户，所以userid用户的关注数+1， friendid的粉丝数也+1 ，此时x就是1
     * 如果userid取关了friendid用户，所以userid用户的关注数+（-1），friendid粉丝数也+（-1）， 此时的x就是-1
     *
     * @param userid
     * @param friendid
     * @param x        数量
     */
    @RequestMapping(value = "/user/{userid}/{friendid}/{x}", method = RequestMethod.PUT)
    void updateFansCountAndFollowcCount(@PathVariable("userid") String userid, @PathVariable("friendid") String friendid, @PathVariable("x") int x);
}
