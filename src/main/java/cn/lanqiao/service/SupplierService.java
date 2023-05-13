package cn.lanqiao.service;

import cn.lanqiao.pojo.Supplier;
import cn.lanqiao.pojo.User;

import java.util.List;
import java.util.Map;

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
    //校验用户名是否存在
    int checkName(String name);
    //校验用户电话号码
    int checkPhone(String phone);
    //校验联系人
    int checkLinkman(String linkman);
    //获取所有用户列表
    Supplier findById  (String id);
    //寻找数据库中所有信息的方法
    List<Supplier> selectAllSupplier();
    //查询供应商地下所有的账单信息(供应商姓名:账单数量)
    Map<String,Integer> getBillCountBySupplier();

}
