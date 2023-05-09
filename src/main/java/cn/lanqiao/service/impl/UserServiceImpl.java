package cn.lanqiao.service.impl;

import cn.lanqiao.dao.UserDao;
import cn.lanqiao.dao.impl.UserDaoImpl;
import cn.lanqiao.pojo.User;
import cn.lanqiao.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();
    @Override
    public User login(User user) {
        return userDao.login(user);
    }

    @Override
    public List<User> selectAllUser() {
        return userDao.selectAllUser();
    }
}
