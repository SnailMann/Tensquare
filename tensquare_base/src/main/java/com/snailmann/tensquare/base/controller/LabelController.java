package com.snailmann.tensquare.base.controller;


import com.snailmann.tensquare.common.entity.Result;
import com.snailmann.tensquare.common.entity.StatusCode;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SnailMann
 * 标签Controller
 */
@RestController
@CrossOrigin
@RequestMapping("/label")
public class LabelController {

    @GetMapping()
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功");
    }
}
