package cn.lanqiao.dao;

import cn.lanqiao.pojo.User;

public interface UserDao {
    /**
     * 登录查询
     * @param user
     * @return
     */
    User login(User user);
}
