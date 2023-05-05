package cn.lanqiao.service;

import cn.lanqiao.pojo.User;

import java.util.List;

public interface AuthorityService {
    List<User> selectAll(String username);

    //查询总数
    int getTotalCount();
    //查询每页数据
    //name 就是用来做模糊查询
    List<User> getDepatrs(String name , Integer pageStart , Integer pageSize);
    User getUserById(String id);
    //修改操作
    int updateById(Integer id,String newType);
}
