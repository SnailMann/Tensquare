package com.snailmann.tensquare.spit.controller;

import com.snailmann.tensquare.common.entity.PageResult;
import com.snailmann.tensquare.common.entity.Result;
import com.snailmann.tensquare.common.entity.StatusCode;
import com.snailmann.tensquare.spit.entity.Spit;
import com.snailmann.tensquare.spit.service.SpitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    private SpitService spitService;

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 根据父Id来查询吐槽数据 | 分页查询
     * 相当于查询某个吐槽下面的评论（评论又是一个吐槽）
     *
     * @param parentId
     * @param page     当前页
     * @param size     每页多少数据
     * @return
     */
    @GetMapping("/comment/{parentId}/{page}/{size}")
    public Result findByParentid(@PathVariable String parentId, @PathVariable int page, @PathVariable int size) {
        Page<Spit> pageData = spitService.findByParentid(parentId, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageData.getTotalElements(), pageData.getContent()));
    }


    /**
     * 为某个吐槽点赞
     *
     * @param spitId
     * @return
     */
    @PutMapping("/thumbup/{spitId}")
    public Result thumbup(@PathVariable String spitId) {
        //判断当前用户是否已经点过赞，但是现在我们没有做认证，暂且先把userid先写死
        String userid = "1001";
        //判断当前用户是否已经点赞

        if (redisTemplate.opsForValue().get("spit_thumbup" + userid) != null) {
            return new Result(false, StatusCode.REPERT_ERROR, "不能重复点赞");
        }
        spitService.thumbup(spitId);
        redisTemplate.opsForValue().set("spit_thumbup" + userid, 1);
        return new Result(true, StatusCode.OK, "点赞成功");
    }

    /**
     * 查询所有吐槽列表
     *
     * @return
     */
    @GetMapping
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", spitService.findAll());
    }


    /**
     * 根据id来查询吐槽列表
     *
     * @param spitId
     * @return
     */
    @GetMapping("/{spitId}")
    public Result findById(@PathVariable String spitId) {
        return new Result(true, StatusCode.OK, "查询成功", spitService.findById(spitId));
    }

    /**
     * 新增一个吐槽
     *
     * @param spit
     * @return
     */
    @PostMapping
    public Result save(@RequestBody Spit spit) {
        spitService.save(spit);
        return new Result(true, StatusCode.OK, "插入成功");
    }


    /**
     * 更新某个吐槽
     *
     * @param spit
     * @return
     */
    @PutMapping("/{spitId}")
    public Result update(@PathVariable String spitId, @RequestBody Spit spit) {
        spit.set_id(spitId);
        spitService.update(spit);
        return new Result(true, StatusCode.OK, "更新成功");
    }

    /**
     * 删除某个吐槽
     *
     * @param spitId
     * @return
     */
    @DeleteMapping("/{spitId}")
    public Result deleteById(@PathVariable String spitId) {
        spitService.deleteById(spitId);
        return new Result(true, StatusCode.OK, "删除成功");
    }


}
