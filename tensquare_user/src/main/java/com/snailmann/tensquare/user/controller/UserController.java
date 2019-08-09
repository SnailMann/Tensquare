package com.snailmann.tensquare.user.controller;

import com.snailmann.tensquare.common.entity.PageResult;
import com.snailmann.tensquare.common.entity.Result;
import com.snailmann.tensquare.common.entity.StatusCode;
import com.snailmann.tensquare.common.util.JwtUtil;
import com.snailmann.tensquare.user.entity.User;
import com.snailmann.tensquare.user.service.UserService;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    JwtUtil jwtUtil;

    /**
     * 更新用户的关注数和对应好友的粉丝数
     * 既当前userid关注了friendid用户，所以userid用户的关注数+1， friendid的粉丝数也+1 ，此时x就是1
     * 如果userid取关了friendid用户，所以userid用户的关注数+（-1），friendid粉丝数也+（-1）， 此时的x就是-1
     *
     * @param userid
     * @param friendid
     * @param x        数量
     */
    @RequestMapping(value = "/{userid}/{friendid}/{x}", method = RequestMethod.PUT)
    public void updateFansCountAndFollowcCount(@PathVariable String userid, @PathVariable String friendid, @PathVariable int x) {
        userService.updateFansCountAndFollowCount(x, userid, friendid);
    }

    /**
     * 普通用户登录
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody User user) {
        user = userService.login(user.getMobile(), user.getPassword());
        if (user == null) {
            return new Result(false, StatusCode.LOGIN_ERROR, "登录失败");
        }
        //登录成功，生成Token
        String token = jwtUtil.createJWT(user.getId(), user.getMobile(), "user");
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("token", token);
        resultMap.put("role", "user");

        return new Result(true, StatusCode.OK, "登录成功", resultMap);
    }

    /**
     * 注册用户
     * 只需要传重要的信息，比如用户名，密码，手机号，其他都可以不填
     *
     * @param code
     * @param user
     * @return
     */
    @PostMapping("/register/{code}")
    public Result register(@PathVariable String code, @RequestBody User user) {
        //得到缓存中的验证码
        String checkcodeFromRedis = redisTemplate.opsForValue().get("checkcode_" + user.getMobile());

        //如果没有验证码或者验证码已过期
        if (checkcodeFromRedis == null || checkcodeFromRedis.isEmpty()) {
            return new Result(false, StatusCode.ERROR, "请先获取手机验证码");
        }
        //验证码不匹配
        if (!checkcodeFromRedis.equals(code)) {
            return new Result(false, StatusCode.ERROR, "请输入正确的验证码");
        }

        //注册用户
        userService.add(user);
        return new Result(true, StatusCode.OK, "注册成功");

    }

    /**
     * 发送短信验证码
     *
     * @return
     */
    @PostMapping("sendsms/{mobile}")
    public Result sendSms(@PathVariable String mobile) {
        userService.sendSms(mobile);
        return new Result(true, StatusCode.OK, "发送成功");
    }


    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", userService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", userService.findById(id));
    }


    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<User> pageList = userService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<User>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", userService.findSearch(searchMap));
    }

    /**
     * 增加一个用户
     *
     * @param user
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody User user) {
        userService.add(user);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param user
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody User user, @PathVariable String id) {
        user.setId(id);
        userService.update(user);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        String token = (String) request.getAttribute("claims_admin");
        if (StringUtils.isBlank(token)) {
            return new Result(false, StatusCode.OK, "权限不足");
        }
        userService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

}
