package cn.lanqiao.service;

import cn.lanqiao.pojo.Supplier;

import java.util.List;

public interface SupplierService {
    int getTotalCount();
    //查询每页数据
    //neme做模糊查询
    List<Supplier> getDepatrs(String name, Integer pageStart, Integer pageSize);
    int addSuppliers(Supplier supplier);
    //根据id查询用户信息
    Supplier getSupplierById(String id);
    //修改信息
    int updateById(Supplier supplier);
    //删除
    int deleteById(String id);

    Supplier findById(String id);

    //供应商列表查询
    List<Supplier> selectAll(String name);
}
