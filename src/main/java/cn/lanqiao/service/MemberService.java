package cn.lanqiao.service;

import cn.lanqiao.pojo.User;

import java.util.ArrayList;
import java.util.List;

public interface MemberService {
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
    //查询年龄
    List<Integer>  selectAge();
    //查询总数
    ArrayList<Integer> getTotalCount(User user);
    //折线图查询tb_oldUser
    ArrayList<Integer> getCount(User user);
    //查询密码
    int checkPassword(Integer userId,String password);
    //查询手机号是否存在
    int checkUserPhone(String phone);
    //根据手机号查询用户名
    String getUserNameByPhone(String number);
}
