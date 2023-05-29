package cn.lanqiao.dao;


import cn.lanqiao.pojo.Commodity;

import java.util.List;

public interface CommodityDao {

    //查询总数
    int getTotalCount();
    //查询每页数量
    List<Commodity> getDepatrs(String name,Integer pageStart , Integer pageSize);

    //修改上架状态
    int changeStatus(String id);

    //添加商品
    int add(Commodity commodity);

    //查询所有商品
    List<Commodity> queryCommodity();
}
