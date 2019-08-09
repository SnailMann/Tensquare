package com.snailmann.tensquare.friend.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * 喜欢表,用户关注表
 * userid喜欢friendid,单向关注，islike = 0 , 双向关注islike = 1
 */
@Data
@Entity
@Table(name = "tb_friend")
@IdClass(Friend.class) //联合主键的声明
public class Friend implements Serializable {
    @Id
    private String userid;
    @Id
    private String friendid;

    /**
     * 单向关注，islike = 0 , 双向关注islike = 1
     */
    private String islike;
}
