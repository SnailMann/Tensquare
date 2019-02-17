package com.snailmann.tensquare.spit.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * 吐槽实体
 * 这里不像JPA有@Entity，@Table等注解，因为这些是Java JPA的规范
 * 但是还是要有@ID注解，因为这是Spring Data的规范
 */
@Data
public class Spit implements Serializable {

    /**
     * mongodb文档唯一标识，Id
     */
    @Id
    private String _id;

    /**
     * 吐槽内容
     */
    private String content;

    /**
     * 吐槽发布时间
     */
    private Date publishtime;

    /**
     * 用户id
     */
    private String userid;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 浏览数
     */
    private Integer visits;

    /**
     * 点赞数
     */
    private Integer thumbup;

    /**
     * 分享次数
     */
    private Integer share;

    /**
     * 评论数
     */
    private Integer comment;

    /**
     * 状态
     */
    private String state;

    /**
     * 父吐槽的id | 比如有一个吐槽是在别人吐槽下面评论的，所以子吐槽要记住父吐槽的id
     * MongoDB中就是嵌套文档
     */
    private String parentid;


}
