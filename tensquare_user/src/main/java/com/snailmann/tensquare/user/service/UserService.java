package com.snailmann.tensquare.user.service;

import com.snailmann.tensquare.common.util.IdWorker;
import com.snailmann.tensquare.user.dao.UserDao;
import com.snailmann.tensquare.user.entity.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public void sendSms(String mobile) {
        //生成6位随机数作为短信验证码
        String checkcode = RandomStringUtils.randomNumeric(6);
        //向缓存中放一份,1分钟后过期
        int ttl = 60;
        redisTemplate.opsForValue().set("checkcode_" + mobile, checkcode, ttl, TimeUnit.SECONDS);
        //给用户发一份，rabbitmq发送
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("checkcode", checkcode);
        map.put("ttl", ttl + "");
        rabbitTemplate.convertAndSend("sms", map);

        //在控制台输出一份(为了测试)
        System.out.println("checkcode : " + checkcode);
    }

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<User> findAll() {
        return userDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<User> findSearch(Map whereMap, int page, int size) {
        Specification<User> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return userDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<User> findSearch(Map whereMap) {
        Specification<User> specification = createSpecification(whereMap);
        return userDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public User findById(String id) {
        return userDao.findById(id).get();
    }

    /**
     * 增加一个用户
     *
     * @param user
     */
    public void add(User user) {

        //密码BCrypt加密
        String password = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(password);

        //初始化其他用户数据
        user.setId(idWorker.nextId() + "");
        user.setFollowcount(0);//初始化关注数
        user.setFanscount(0);//初始化粉丝数
        user.setOnline(0L);//在线时长
        user.setRegdate(new Date()); //注册时间
        user.setUpdatedate(new Date());//信息更新时间
        user.setLastdate(new Date());//最后登录时间


        userDao.save(user);
    }

    /**
     * 修改
     *
     * @param user
     */
    public void update(User user) {
        userDao.save(user);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        userDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<User> createSpecification(Map searchMap) {

        return new Specification<User>() {

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                    predicateList.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
                }
                // 密码
                if (searchMap.get("password") != null && !"".equals(searchMap.get("password"))) {
                    predicateList.add(cb.like(root.get("password").as(String.class), "%" + (String) searchMap.get("password") + "%"));
                }
                // 昵称
                if (searchMap.get("nickname") != null && !"".equals(searchMap.get("nickname"))) {
                    predicateList.add(cb.like(root.get("nickname").as(String.class), "%" + (String) searchMap.get("nickname") + "%"));
                }
                // 性别
                if (searchMap.get("sex") != null && !"".equals(searchMap.get("sex"))) {
                    predicateList.add(cb.like(root.get("sex").as(String.class), "%" + (String) searchMap.get("sex") + "%"));
                }
                // 头像
                if (searchMap.get("avatar") != null && !"".equals(searchMap.get("avatar"))) {
                    predicateList.add(cb.like(root.get("avatar").as(String.class), "%" + (String) searchMap.get("avatar") + "%"));
                }
                // E-Mail
                if (searchMap.get("email") != null && !"".equals(searchMap.get("email"))) {
                    predicateList.add(cb.like(root.get("email").as(String.class), "%" + (String) searchMap.get("email") + "%"));
                }
                // 兴趣
                if (searchMap.get("interest") != null && !"".equals(searchMap.get("interest"))) {
                    predicateList.add(cb.like(root.get("interest").as(String.class), "%" + (String) searchMap.get("interest") + "%"));
                }
                // 个性
                if (searchMap.get("personality") != null && !"".equals(searchMap.get("personality"))) {
                    predicateList.add(cb.like(root.get("personality").as(String.class), "%" + (String) searchMap.get("personality") + "%"));
                }

                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }


    /**
     * 普通用户登录判断
     * 通过用户手机号来判断
     *
     * @param mobile
     * @param password
     * @return
     */
    public User login(String mobile, String password) {
        User userdb = userDao.findByMobile(mobile);

        //如果数据存在该用户数据，且与数据库密文匹配成功，那么就登录成功
        if (userdb != null) {
            if (bCryptPasswordEncoder.matches(password, userdb.getPassword())) {
                return userdb;
            }
        }

        return null;
    }

    /**
     * 更新粉丝数和关注数
     *
     * @param userid
     * @param friendid
     * @param x
     */
    @Transactional
    public void updateFansCountAndFollowCount(int x, String userid, String friendid) {
        userDao.updateFansCount(x, userid);
        userDao.updateFollowCount(x, friendid);
    }
}
