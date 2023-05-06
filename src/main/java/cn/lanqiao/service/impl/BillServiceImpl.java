package cn.lanqiao.service.impl;

import cn.lanqiao.dao.BillDao;
import cn.lanqiao.dao.SupplierDao;
import cn.lanqiao.dao.impl.BillDaoImpl;
import cn.lanqiao.dao.impl.SupplierDaoImpl;
import cn.lanqiao.pojo.Bill;
import cn.lanqiao.pojo.Supplier;
import cn.lanqiao.service.BillService;

import java.util.List;

public class BillServiceImpl implements BillService {
    BillDao billDao = new BillDaoImpl();
    SupplierDao SupplierDao = new SupplierDaoImpl();
    @Override
    public int getTotalCount() {
        return billDao.getTotalCount();
    }

    @Override
    public List<Bill> getDepatrs(String name, String providerId, String ispay, Integer pageStart, Integer pageSize) {
        List<Bill> depatrs = billDao.getDepatrs(name, providerId, ispay, pageStart, pageSize);
        for (Bill b: depatrs) {
            Integer providerid = b.getProviderid();
            Supplier byId = SupplierDao.findById(String.valueOf(providerid));
            b.setProviderName(byId.getName());
        }
        return depatrs;
    }


    @Override
    public int add(Bill bill) {
        return  billDao.add(bill);
    }

    @Override
    public Bill getBillById(String id) {
        return billDao.getBillById(id);
    }

    @Override
    public int updaeteById(Bill bill) {
        return billDao.updaeteById(bill);
    }

    @Override
    public int deleteById(String id) {
        return billDao.deleteById(id);
    }

    @Override
    public List<Bill> selectAll() {
        return billDao.selectAll();
    }


}
