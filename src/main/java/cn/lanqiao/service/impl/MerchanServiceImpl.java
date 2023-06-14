package cn.lanqiao.service.impl;

import cn.lanqiao.dao.MerchandiseDao;
import cn.lanqiao.dao.impl.MerchandiseDaoImpl;
import cn.lanqiao.pojo.Merchan;
import cn.lanqiao.service.MerchanService;

import java.math.BigDecimal;
import java.util.List;

public class MerchanServiceImpl implements MerchanService {
    MerchandiseDao merchandiseDao = new MerchandiseDaoImpl();
    @Override
    public int getCount() {
        return merchandiseDao.getCount();
    }

    @Override
    public List<Merchan> getAll(String name, Integer pageStart, Integer pageSize) {
        return merchandiseDao.getAll(name,pageStart,pageSize);
    }

    @Override
    public Merchan getMerchanById(String id) {
        return merchandiseDao.getMerchanById(id);
    }

    @Override
    public int updateById(Merchan merch) {
        return merchandiseDao.updateById(merch);
    }

    @Override
    public int deleteById(String id) {
        return merchandiseDao.deleteById(id);
    }

    @Override
    public int checkName(String name) {
        return merchandiseDao.checkName(name);
    }

    @Override
    public List<Merchan> getOrders() {
        return merchandiseDao.getOrders();
    }
}
