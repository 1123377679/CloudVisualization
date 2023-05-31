package cn.lanqiao.service.impl;

import cn.lanqiao.dao.AlipayDao;
import cn.lanqiao.dao.impl.AlipayDaoImpl;
import cn.lanqiao.pojo.Merchan;
import cn.lanqiao.service.AlipayService;

public class AlipayServiceImpl implements AlipayService {
    AlipayDao alipayDao = new AlipayDaoImpl();
    @Override
    public int add(Merchan merch) {
        return alipayDao.add(merch);
    }
}
