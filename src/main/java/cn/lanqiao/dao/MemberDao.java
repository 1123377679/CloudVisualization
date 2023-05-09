package cn.lanqiao.dao;

import cn.lanqiao.pojo.User;

import java.util.List;

public interface MemberDao {
    //查询总数
    int getTotalCount();
    //查询每页的数据
    List<User> getDepatrs(String name , Integer pageStart , Integer pageSize);
    //添加用户信息
    int addUser(User user);
    //根据id查询用户信息
    User getUserById(String id);
    //根据id修改用户信息
    int updateById(User user);
    //修改当前用户的密码
    int updatePwd(Integer userId,String newPwd);
    //删除功能
    int deleteById(String id);
    //判断添加用户
    int checkName(String name);
    //查询所有用户信息
    List<User> selectAllUser();
}
