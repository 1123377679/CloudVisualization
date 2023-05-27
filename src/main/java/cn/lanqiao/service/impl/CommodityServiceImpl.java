package cn.lanqiao.service.impl;

import cn.lanqiao.dao.CommodityDao;
import cn.lanqiao.dao.impl.CommodityDaoImpl;
import cn.lanqiao.pojo.Commodity;
import cn.lanqiao.service.CommodityService;

import java.util.List;

public class CommodityServiceImpl implements CommodityService {
    CommodityDao commodityDao = new CommodityDaoImpl();
    @Override
    public int getTotalCount() {
        return commodityDao.getTotalCount();
    }

    @Override
    public List<Commodity> getDepatrs(String name,Integer pageStart, Integer pageSize) {
        return commodityDao.getDepatrs(name,pageStart,pageSize);
    }

    @Override
    public int changeStatus(String id) {
        return commodityDao.changeStatus(id);
    }

    @Override
    public int add(Commodity commodity) {
        return commodityDao.add(commodity);
    }


}
