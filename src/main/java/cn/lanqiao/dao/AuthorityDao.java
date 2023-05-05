package cn.lanqiao.dao;

import cn.lanqiao.pojo.User;

import java.util.List;

public interface AuthorityDao {

    List<User> selectAll(String username);

    //查询总数
    int getTotalCount();
    //查询每页数据
    //name 就是用来做模糊查询
    List<User> getDepatrs(String name , Integer pageStart , Integer pageSize);
    //根据id查询用户信息
    User getUserById(String id);
    //修改操作
    int updateById(Integer id,String newType);
}
