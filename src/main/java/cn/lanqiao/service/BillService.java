package cn.lanqiao.service;

import cn.lanqiao.pojo.Bill;
import cn.lanqiao.pojo.Supplier;
import cn.lanqiao.pojo.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface BillService {
    int getTotalCount();
    //查询每页数据
    List<Bill> getDepatrs(String name ,String providerId, String ispay, Integer pageStart, Integer pageSize);
    //添加商品
    int add(Bill bill);
    //根据id查询账单信息
    Bill getBillById(String id);
    //根据id修改
    int updaeteById(Bill bill);
    //根据id删除
    int deleteById(String id);

    //查询所有的数据
    List<Bill> selectAll();

    //查询供应商地区
    Map<String ,Integer > getsupplierarea();
    //根据供应商名字查询账单编号
    int  getbillsum(String name);
    //查询年份和销售额
    List<Integer> getSalesComparison();


}
