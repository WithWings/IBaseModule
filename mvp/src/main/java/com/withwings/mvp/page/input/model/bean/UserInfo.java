package com.withwings.mvp.page.input.model.bean;

/**
 * 用户的信息类
 * 创建：WithWings 时间：2017/11/27.
 * Email:wangtong1175@sina.com
 */
public class UserInfo {

    private String userName;

    private String password;

    private String remark;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
