package com.snailmann.tensquare.base.controller;


import com.snailmann.tensquare.base.entity.Label;
import com.snailmann.tensquare.base.service.LabelService;
import com.snailmann.tensquare.common.entity.PageResult;
import com.snailmann.tensquare.common.entity.Result;
import com.snailmann.tensquare.common.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author SnailMann
 * 标签Controller
 */
@RestController
@CrossOrigin
@RequestMapping("/label")
public class LabelController {

    private AtomicInteger count = new AtomicInteger(0);
    @Autowired
    private LabelService labelService;

    /**
     * 查询所有
     *
     * @return
     */
    @GetMapping()
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功",labelService.findAll());
    }

    /**
     * 根据标签Id查询所有
     *
     * @param labelId
     * @return
     */
    @GetMapping("/{labelId}")
    public Result findById(@PathVariable("labelId") String labelId) throws InterruptedException {
        System.out.println(count.incrementAndGet());
        return new Result(true, StatusCode.OK, "查询成功",labelService.findById(labelId));

    }

    /**
     * 添加标签
     *
     * @return
     */
    @PostMapping()
    public Result save(@RequestBody Label label) {
        labelService.save(label);
        return new Result(true, StatusCode.OK, "插入成功");
    }

    /**
     * 添加标签
     *
     * @return
     */
    @PutMapping("/{labelId}")
    public Result update(@PathVariable("labelId") String labelId, @RequestBody Label label) {
        label.setId(labelId);
        labelService.update(label);
        return new Result(true, StatusCode.OK, "更新成功");
    }


    /**
     * 根据Id删除标签
     *
     * @return
     */
    @DeleteMapping("/{labelId}")
    public Result deleteById(@PathVariable("labelId") String labelId) {
        labelService.deleteById(labelId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 根据条件查询标签类别
     */
    @PostMapping("/search")
    public Result findSearch(@RequestBody Label label){
        List<Label> labelList = labelService.findSearch(label);
        return new Result(true,StatusCode.OK,"查询成功",labelList);
    }

    /**
     * 根据条件查询城市列表
     */
    @PostMapping("/search/{page}/{size}")
    public Result pageQuery(@RequestBody Label label, @PathVariable int page, @PathVariable int size){
        Page<Label> pageDate = labelService.pageQuery(label,page,size);
        //getTotalElements是总记录数，getTotalPages是总页数
        return new Result(true,StatusCode.OK,"查询成功"
                ,new PageResult<>(pageDate.getTotalElements(),pageDate.getContent()));
    }

}
