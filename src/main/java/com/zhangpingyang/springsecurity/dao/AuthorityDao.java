package com.zhangpingyang.springsecurity.dao;

import com.zhangpingyang.springsecurity.entity.Authority;
import com.zhangpingyang.springsecurity.enumeration.AuthorityEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthorityDao extends JpaRepository<Authority, Integer> {
    @Query(value = "from Authority where authority = ?1")
    Authority findByAuthority(AuthorityEnum authority);
}
