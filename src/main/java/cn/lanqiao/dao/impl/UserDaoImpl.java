package cn.lanqiao.dao.impl;

import cn.lanqiao.dao.UserDao;
import cn.lanqiao.pojo.User;
import cn.lanqiao.utils.DButils;

import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    @Override
    public User login(User user) {
        //查询并且判断登录还是注册
        ArrayList<User> users = DButils.commonQuery(User.class, "select * from tb_user where username=? and password=?",user.getUsername(),user.getPassword());
        if (users.size() > 0 && users != null) {
            return  users.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<User> selectAllUser() {
        return DButils.commonQuery(User.class, "select * from tb_user");
    }
}
