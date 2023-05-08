package cn.lanqiao.dao;

import cn.lanqiao.pojo.Supplier;
import cn.lanqiao.pojo.User;

import java.util.List;

public interface SupplierDao {
    //查询总数
    int getTotoCount();
    //查询每页数据
    List<Supplier> getDepatrs(String name,Integer pageStart,Integer pageSize);
    //添加会员
    int addSupplier(Supplier supplier);
    //根据id查询用户信息
    Supplier getSupplierById(String id);
    //根据id修改信息
    int updateById(Supplier supplier);
    //删除
    int deleteById(String id);
    //校验用户名是否存在
    int checkName(String name);
    //校验用户电话号码
    int checkPhone(String phone);
    //校验联系人
    int checkLinkman(String linkman);
    Supplier findById  (String id);
    //寻找数据库中所有信息的方法
    List<Supplier> selectAllSupplier();
}
