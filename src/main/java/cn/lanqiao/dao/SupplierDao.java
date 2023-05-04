package cn.lanqiao.dao;

import cn.lanqiao.pojo.Supplier;

import java.util.List;

public interface SupplierDao {
    //查询总数
    int getTotoCount();
    //查询每页数据
    List<Supplier> getDepatrs(String name, Integer pageStart, Integer pageSize);
    //添加会员
    int addSupplier(Supplier supplier);
    //根据id查询用户信息
    Supplier getSupplierById(String id);
    //根据id修改信息
    int updateById(Supplier supplier);
    //删除
    int deleteById(String id);

    Supplier findById(String id);

}
