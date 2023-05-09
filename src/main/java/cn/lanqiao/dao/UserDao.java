package cn.lanqiao.dao;

import cn.lanqiao.pojo.User;

import java.util.List;

public interface UserDao {
    /**
     * 登录查询
     * @param user
     * @return
     */
    User login(User user);
    //查询所有用户信息
    List<User> selectAllUser();
}
