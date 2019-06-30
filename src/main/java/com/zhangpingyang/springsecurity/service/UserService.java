package com.zhangpingyang.springsecurity.service;

import com.zhangpingyang.springsecurity.dao.UserDao;
import com.zhangpingyang.springsecurity.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public User getUserById(long id) {
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
        user.setUserId(userId);
        userDao.delete(user);
    }
}
