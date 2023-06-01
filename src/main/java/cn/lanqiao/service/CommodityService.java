package cn.lanqiao.service;

import cn.lanqiao.pojo.Commodity;

import java.util.List;

public interface CommodityService {

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

    //查询条码
    Commodity queryCommodityByCode(String barcode);

    //查询该条码是否存在
    int queryBarcodeExist(String barcode);

    //删除功能
    int deleteById(String id);
}
