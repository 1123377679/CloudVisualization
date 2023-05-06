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
    //校验用户名是否存在
    int checkName(String name);
    //校验用户电话号码
    int checkPhone(String phone);
    //校验联系人
    int checkLiername(String liername);
    //供应商列表查询
    List<Supplier> selectAll(String name);


}
