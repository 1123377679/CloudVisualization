package cn.lanqiao.service;

import cn.lanqiao.pojo.User;

import java.util.List;

public interface UserService {
    User login(User user);
    //查询所有用户信息
    List<User> selectAllUser();
}
