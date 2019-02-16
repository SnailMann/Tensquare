package com.snailmann.tensquare.qa.controller;

import java.util.Map;

import com.snailmann.tensquare.common.entity.PageResult;
import com.snailmann.tensquare.common.entity.Result;
import com.snailmann.tensquare.common.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.snailmann.tensquare.qa.entity.Problem;
import com.snailmann.tensquare.qa.service.ProblemService;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    /**
     * 查询某个标签的最新回答列表
     * @param labelId
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/newlist/{labelId}/{page}/{size}")
    public Result newList(@PathVariable String labelId, @PathVariable int page, @PathVariable int size) {
        Page<Problem> pageData = problemService.newList(labelId, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageData.getTotalElements(),pageData.getContent()));
    }

    /**
     * 查询某个标签的热门回答列表
     * @param labelId
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/hotlist/{labelId}/{page}/{size}")
    public Result hotList(@PathVariable String labelId, @PathVariable int page, @PathVariable int size) {
        Page<Problem> pageData = problemService.hotList(labelId,page,size);
        return new Result(true, StatusCode.OK, "查询成功",  new PageResult<>(pageData.getTotalElements(),pageData.getContent()));
    }

    /**
     * 查询某个标签的等待回答列表
     * @param labelId
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/waitlist/{labelId}/{page}/{size}")
    public Result waitList(@PathVariable String labelId, @PathVariable int page, @PathVariable int size) {
        Page<Problem> pageData = problemService.waitList(labelId,page,size);
        return new Result(true, StatusCode.OK, "查询成功",  new PageResult<>(pageData.getTotalElements(),pageData.getContent()));
    }

    /**
     * 查询全部数据 | 查询全部Problem
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findAll());
    }

    /**
     * 根据ID查询 | 根据ID查询指定Problem
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findById(id));
    }


    /**
     * 分页+多条件查询 | 问题列表分页显示
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Problem> pageList = problemService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询 | 根据条件查询problem列表
     *
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findSearch(searchMap));
    }

    /**
     * 增加 | 新增加一个Problem
     *
     * @param problem
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Problem problem) {
        problemService.add(problem);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改 | 修改一个Problem
     *
     * @param problem
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Problem problem, @PathVariable String id) {
        problem.setId(id);
        problemService.update(problem);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除 | 删除一个Problem
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        problemService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

}
