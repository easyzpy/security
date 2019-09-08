package com.zhangpingyang.springsecurity.entity;

import com.zhangpingyang.springsecurity.entity.react.Topic;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

@Entity
@Table(name = "jwt_user")
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable {

    @Id
        @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 40)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;

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
    @Column(nullable = false, length = 50)
    private String loginname;
    @Column(length = 200)
    private String avatarUrl;
    @ManyToMany
    @JoinTable(
            name = "user_authority"
            , joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "userId")}
            , inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "authorityId")}
    )
    private List<Authority> authorities;
    /**
     * 用户的收藏
     */
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(
            name = "collect_topic"
            ,joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "userId")}
            ,inverseJoinColumns = {@JoinColumn (name= "id", referencedColumnName = "id")}
    )
    private List<Topic> collections;

    public User() {
    }

    public User(String userId) {
        this.userId = userId;
    }

    public List<Topic> getCollections() {
        return collections;
    }

    public void setCollections(List<Topic> collections) {
        this.collections = collections;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
