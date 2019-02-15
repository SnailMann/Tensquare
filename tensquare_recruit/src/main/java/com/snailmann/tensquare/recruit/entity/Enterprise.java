package com.snailmann.tensquare.recruit.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
/**
 * 实体类
 * @author Administrator
 *
 */
@Data
@Entity
@Table(name="tb_enterprise")
public class Enterprise implements Serializable{

	@Id
	private String id;//ID
	private String name;//企业名称
	private String summary;//企业简介
	private String address;//企业地址
	private String labels;//标签列表
	private String coordinate;//坐标
	private String ishot;//是否热门
	private String logo;//LOGO
	private Integer jobcount;//职位数
	private String url;//URL

	
	public String getId() {		
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {		
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getSummary() {		
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getAddress() {		
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}



	
}
