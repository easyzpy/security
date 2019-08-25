package com.zhangpingyang.springsecurity.bean;

import com.zhangpingyang.springsecurity.entity.User;

public class UserVo {
    private String loginname;
    private String avatarUrl;

    public UserVo() {
    }

    public UserVo(User user) {
        this.loginname = user.getLoginname();
        this.avatarUrl = user.getAvatarUrl();
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
