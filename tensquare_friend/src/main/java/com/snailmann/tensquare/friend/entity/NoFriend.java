package com.snailmann.tensquare.friend.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * 不喜欢表，相当于拉黑表
 * userid不喜欢friendid
 */
@Data
@Entity
@Table(name = "tb_nofriend")
@IdClass(NoFriend.class) //联合主键的声明
public class NoFriend implements Serializable {
    @Id
    private String userid;
    @Id
    private String friendid;
}
