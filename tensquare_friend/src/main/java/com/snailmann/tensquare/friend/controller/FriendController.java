package com.snailmann.tensquare.friend.controller;


import com.snailmann.tensquare.common.entity.Result;
import com.snailmann.tensquare.common.entity.StatusCode;
import com.snailmann.tensquare.friend.service.FriendService;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private FriendService friendService;

    /**
     * 添加好友或者非好友
     *
     * @return
     */
    @RequestMapping(value = "/like/{friendid}/{type}")
    public Result addFriend(@PathVariable String friendid, @PathVariable String type) {
        //验证是否登录，并且拿到当前登录的用户id
        Claims claims = (Claims) request.getAttribute("claim_user");
        if (claims == null) {
            return new Result(false, StatusCode.LOGIN_ERROR, "权限不足");
        }
        String userId = claims.getId();
        //判断添加好友，还是添加非好友
        if (type != null) {
            if ("1".equals(type)) {
                //添加好友
                int flag = friendService.addFriend(userId, friendid);
                if (flag == 0) {
                    return new Result(false, StatusCode.ERROR, "不能重复添加好友");
                }
                if (flag == 1) {
                    return new Result(false, StatusCode.ERROR, "添加好友成功");
                }
            } else if ("2".equals(type)) {
                //添加非好友
            } else {
                return new Result(false, StatusCode.ERROR, "参数异常");
            }
        }
        return new Result(true, StatusCode.OK, "操作成功");
    }
}
