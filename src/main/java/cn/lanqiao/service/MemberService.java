package cn.lanqiao.service;

import cn.lanqiao.pojo.User;

import java.util.List;

public interface MemberService {
    //查询总数
    int getTotalCount();
    //查询每页的数据
    List<User> getDepatrs(String name , Integer pageStart , Integer pageSize);
}
