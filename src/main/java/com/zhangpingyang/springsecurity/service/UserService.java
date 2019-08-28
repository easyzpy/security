package com.zhangpingyang.springsecurity.service;

import com.zhangpingyang.springsecurity.StatusException;
import com.zhangpingyang.springsecurity.dao.AuthorityDao;
import com.zhangpingyang.springsecurity.dao.UserDao;
import com.zhangpingyang.springsecurity.entity.Authority;
import com.zhangpingyang.springsecurity.entity.User;

import com.zhangpingyang.springsecurity.enumeration.AuthorityEnum;
import com.zhangpingyang.springsecurity.util.ZCommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private AuthorityDao authorityDao;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    /**
     * 用户注册方法
     * @param user
     * @return
     */
    public User register(User user) {
        if (!regexCheckUser(user)) {
            return null;
        }
        if (userDao.findByUsername(user.getUsername().trim()) != null) {
            throw new StatusException(-2, "用户名已经存在");
        }
        if (userDao.findByPhone(user.getPhone().trim()) != null) {
            throw new StatusException(-2, "电话号码已经存在");
        }
        if (userDao.findByEmail(user.getEmail().trim()) != null) {
            throw new StatusException(-2, "电子邮箱已经存在");
        }

        String encode = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encode);
        user.setEnable(true);
        Authority byAuthority = authorityDao.findByAuthority(AuthorityEnum.ROLE_USER);
        ArrayList<Authority> authorities = new ArrayList<>();
        authorities.add(byAuthority);
        user.setAuthorities(authorities);
        User save = userDao.save(user);
        return save;
    }
    private boolean regexCheckUser(User user) {
        String phone = user.getPhone();
        String email = user.getEmail();
        String username = user.getUsername();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        if (phone == null || email == null || password == null || username == null || firstName == null || lastName == null) {
            return false;
        }
        String regex = "[A-Za-z0-9_\\-\\u4e00-\\u9fa5]{4,16}";
        if (!ZCommonUtil.isMatch(regex, username)) {
            log.warn("username is not regex " +username);
            return false;
        }
        regex = "[a-zA-Z0-9\\-_]{4,16}";
        if (!ZCommonUtil.isMatch(regex, password)) {
            log.warn("password is not regex " +password);
            return false;
        }
        regex = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
        if (!ZCommonUtil.isMatch(regex, email)) {
            log.warn("email is not regex " +email);
            return false;
        }
        regex = "0?(13|14|15|17|18|19)[0-9]{9}";
        if (!ZCommonUtil.isMatch(regex, phone)) {
            log.warn("phone is not regex " +phone);
            return false;
        }
        return true;
    }

    public User getUserById(String id) {
        return userDao.findById(id).get();
    }

    public List<User> getUserList(int page, Integer size) {
//        return userDao.findAll(PageRequest.of(page, size != null?size:SecurityConstant.PAGE_SIZE, new Sort(Sort.Direction.DESC, SecurityConstant.SORT_STR)));
        return userDao.getAllUser();
    }

    /**
     *
     * @param user
     * @return
     * @throws DataIntegrityViolationException unique
     */
    public User insertUser(User user) throws DataIntegrityViolationException {
        return userDao.save(user);
    }

    public User getUserByUsername(String username) {
        if (username == null) {
            return null;
        }
//        Optional<User> byId = userDao.findById(userId);
        User user = userDao.findByUsername(username);
        return user;
    }

    public void deleteUser(long userId) {
//        userDao.deleteById(userId);
        User user = new User();
//        user.setUserId(userId);
        userDao.delete(user);
    }
}
