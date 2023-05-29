package cn.lanqiao.dao;

import cn.lanqiao.pojo.Merchan;
import cn.lanqiao.pojo.User;

import java.util.List;

public interface MerchandiseDao {
    //查询总数
    int getCount();
    //查询每页的数据
    List<Merchan> getAll(String name, Integer pageStart, Integer pageSize);
    //根据id查询用户信息
    Merchan getMerchanById(String id);
    //根据id修改用户信息
    int updateById(Merchan merch);
    //删除功能
    int deleteById(String id);
    //判断会员名字是否重复
    int checkName(String name);
}
