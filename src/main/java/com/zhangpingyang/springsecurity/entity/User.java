package com.zhangpingyang.springsecurity.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "jwt_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(length = 50, unique = true)
    @NotNull
    @Size(min = 4, max = 50)
    private String username;

    @Column(length = 50)
    @NotNull
    @Size(min = 3, max = 50)
    private String password;
    @Column(length = 50)
    @NotNull
    @Size(min = 3, max = 50)
    private String firstName;
    @Column(length = 50)
    @NotNull
    @Size(min = 3, max = 50)
    private String lastName;
    @Column(length = 50)
    @NotNull
    @Size(min = 3, max = 50)
    private String email;
    @Column
    @NotNull
    @Size(min = 3, max = 50)
    private Boolean enable;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date lastModifyTm;
    @ManyToMany
    @JoinTable(
            name = "user_authority"
            , joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "userId")}
            , inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "authorityId")}
    )
    private List<Authority> authorities;

    public Long getUserId() {
        return userId;
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
