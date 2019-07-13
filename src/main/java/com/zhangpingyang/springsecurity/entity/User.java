package com.zhangpingyang.springsecurity.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "jwt_user")
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column
    @CreatedDate
    private Date createTime;

    @Column(length = 50, unique = true, nullable = false)
    @Size(min = 4, max = 50)
    private String username;
    @Column(length = 11, unique = true)
    private String phone;

    @Column(length = 50, nullable = false)
    @Size(min = 3, max = 100)
    private String password;
    @Column(length = 50, nullable = false)
    @Size(min = 3, max = 50)
    private String firstName;
    @Column(length = 50, nullable = false)
    @Size(min = 3, max = 50)
    private String lastName;
    @Column(length = 50, nullable = false, unique = true)
    @Size(min = 3, max = 50)
    private String email;
    @Column(nullable = false)
    private Boolean enable;

    @Column(nullable = false)
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifyTm;
    @ManyToMany
    @JoinTable(
            name = "user_authority"
            , joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "userId")}
            , inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "authorityId")}
    )
    private List<Authority> authorities;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getUserId() {
        return userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Date getLastModifyTm() {
        return lastModifyTm;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public void setLastModifyTm(Date lastModifyTm) {

        this.lastModifyTm = lastModifyTm;
    }
}
