package cn.lanqiao.dao;

import cn.lanqiao.pojo.User;

import java.util.List;

public interface MemberDao {
    //查询总数
    int getTotalCount();
    //查询每页的数据
    List<User> getDepatrs(String name , Integer pageStart , Integer pageSize);
}
