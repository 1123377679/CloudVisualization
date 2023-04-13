package cn.lanqiao.service.impl;

import cn.lanqiao.dao.UserDao;
import cn.lanqiao.dao.impl.UserDaoImpl;
import cn.lanqiao.pojo.User;
import cn.lanqiao.service.UserService;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();
    @Override
    public User login(User user) {
        return userDao.login(user);
    }
}
