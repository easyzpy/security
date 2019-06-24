package com.zhangpingyang.springsecurity.entity;

import com.zhangpingyang.springsecurity.enumeration.AuthorityEnum;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "jwt_authority")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer authorityId;

    @Column(length = 20)
    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthorityEnum authorityName;

    @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
    private List<User> users;

    public Integer getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Integer authorityId) {
        this.authorityId = authorityId;
    }

    public AuthorityEnum getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(AuthorityEnum authorityName) {
        this.authorityName = authorityName;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
