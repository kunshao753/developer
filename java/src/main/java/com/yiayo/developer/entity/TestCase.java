package com.yiayo.developer.entity;

import java.util.Date;

/**
 * @ClassName TestCase
 * @Description
 * @author 李银
 * @date 2019-06-17 16:41:02
 */
public class TestCase {

    /**
     * @Description 主键
     */
    private Long id;


    /**
     * @Description 用例名称
     */
    private String name;
    /**
     * @Description 用户id（谁建的）
     */
    private Long fkUserId;
    /**
     * @Description 用户名称（谁建的）
     */
    private String userName;
    /**
     * @Description 备注
     */
    private String mark;
    /**
     * @Description 
     */
    private Date createTime;
    /**
     * @Description 
     */
    private Date modifyTime;

    public Long getId() {
       	return id;
    }

    public TestCase setId(Long id) {
        this.id = id;
        return this;
    }
    public String getName() {
        return name;
    }

    public TestCase setName(String name) {
        this.name = name;
        return this;
    }

    public Long getFkUserId() {
        return fkUserId;
    }

    public TestCase setFkUserId(Long fkUserId) {
        this.fkUserId = fkUserId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public TestCase setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getMark() {
        return mark;
    }

    public TestCase setMark(String mark) {
        this.mark = mark;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public TestCase setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public TestCase setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }

}
