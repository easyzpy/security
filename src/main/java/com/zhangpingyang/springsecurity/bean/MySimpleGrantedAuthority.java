package com.zhangpingyang.springsecurity.bean;

import org.springframework.security.core.GrantedAuthority;

public class MySimpleGrantedAuthority implements GrantedAuthority {

    private String authority;
    private String role;

    public MySimpleGrantedAuthority() {
    }

    public MySimpleGrantedAuthority(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof MySimpleGrantedAuthority) {
            return role.equals(((MySimpleGrantedAuthority) obj).role);
        }

        return false;
    }
    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return null;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    @Override
    public int hashCode() {
        return this.role.hashCode();
    }

    @Override
    public String toString() {
        return this.role;
    }
}
