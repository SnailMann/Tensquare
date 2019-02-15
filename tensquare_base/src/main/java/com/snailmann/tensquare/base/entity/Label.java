package com.snailmann.tensquare.base.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author SnailMann
 * Entity和Table是JPA要求
 */
@Entity
@Table(name = "tb_label")
@Data
public class Label implements Serializable {

    @Id
    private String id;
    /**
     * 标签名称
     */
    private String labelname;
    /**
     * 状态
     */
    private String state;
    /**
     * 使用数量
     */
    private Long count;
    /**
     * 关注数
     */
    private Long fans;
    /**
     * 是否推荐
     */
    private String recommend;



    public final static String ID = "id";
    public final static String LABEL_NAME = "labelname";
    public final static String STATE = "state";
    public final static String COUNT = "count";
    public final static String FANS = "fans";
    public final static String RECOMMEND = "recommend";


}
