package com.libertad.demo.sys.entity;

import com.libertad.demo.common.pojo.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class UserInfo extends BaseEntity{
    /**
     * 
     */
    @ApiModelProperty(value="",name="id")
    private Long id;

    /**
     * 
     */
    @ApiModelProperty(value="用户名",name="userName")
    private String userName;

    /**
     * 
     */
    @ApiModelProperty(value="密码",name="password")
    private String password;

    /**
     * 用户登录状态0暂未登录。1登录
     */
    @ApiModelProperty(value="用户登录状态0暂未登录。1登录",name="start")
    private String start;

    /**
     * 头像
     */
    @ApiModelProperty(value="头像",name="img")
    private String img;

    /**
     * 新增人
     */
    @ApiModelProperty(value="新增人",name="createUserid")
    private Long createUserid;

    /**
     * 更新人
     */
    @ApiModelProperty(value="更新人",name="updateUserid")
    private Long updateUserid;

    /**
     * 新增时间
     */
    @ApiModelProperty(value="新增时间",name="createTime")
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间",name="updateTime")
    private Date updateTime;

    /**
     * 邮箱
     */
    @ApiModelProperty(value="邮箱",name="email")
    private String email;

    /**
     * 手机号
     */
    @ApiModelProperty(value="手机号",name="phone")
    private Long phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start == null ? null : start.trim();
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    public Long getCreateUserid() {
        return createUserid;
    }

    public void setCreateUserid(Long createUserid) {
        this.createUserid = createUserid;
    }

    public Long getUpdateUserid() {
        return updateUserid;
    }

    public void setUpdateUserid(Long updateUserid) {
        this.updateUserid = updateUserid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }
}