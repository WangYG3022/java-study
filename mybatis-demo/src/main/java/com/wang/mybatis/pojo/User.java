package com.wang.mybatis.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDate;

public class User implements Serializable {
    private Integer userId;

    private String userName;

    private Integer gender;

    private Integer age;

    private String mobile;

    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("userId=").append(userId);
        sb.append(", userName=").append(userName);
        sb.append(", gender=").append(gender);
        sb.append(", age=").append(age);
        sb.append(", mobile=").append(mobile);
        sb.append(", email=").append(email);
        sb.append(", birthday=").append(birthday);
        sb.append(", remark=").append(remark);
        sb.append("]");
        return sb.toString();
    }
}