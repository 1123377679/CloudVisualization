package cn.lanqiao.service.impl;

import cn.lanqiao.dao.SupplierDao;
import cn.lanqiao.dao.UserDao;
import cn.lanqiao.dao.impl.SupplierDaoImpl;
import cn.lanqiao.pojo.Supplier;
import cn.lanqiao.pojo.User;
import cn.lanqiao.service.SupplierService;

import java.util.List;
import java.util.Map;

public class SupplierServiceImpl implements SupplierService {
    SupplierDao supplierDao=new SupplierDaoImpl();

    @Override
    public int getTotalCount() {
        return supplierDao.getTotoCount();
    }

    @Override
    public List<Supplier> getDepatrs(String name, Integer pageStart, Integer pageSize) {
        return supplierDao.getDepatrs(name,pageStart,pageSize);
    }

    @Override
    public int addSuppliers(Supplier supplier) {
        return supplierDao.addSupplier(supplier);
    }

    @Override
    public Supplier getSupplierById(String id) {

        return supplierDao.getSupplierById(id);
    }

    @Override
    public int updateById(Supplier supplier) {
        return supplierDao.updateById(supplier);
    }

    @Override
    public int deleteById(String id) {
        return supplierDao.deleteById(id);
    }

    @Override
    public int checkName(String name) {
        return supplierDao.checkName(name);
    }

    @Override
    public int checkPhone(String phone) {
        return supplierDao.checkPhone(phone);
    }

    @Override
    public int checkLinkman(String linkman) {
        return supplierDao.checkLinkman(linkman);
    }

    @Override
    public Supplier findById(String id) {
        return supplierDao.findById(id);
    }

    @Override
    public List<Supplier> selectAllSupplier() {
        return supplierDao.selectAllSupplier();
    }

    @Override
    public Map<String, Integer> getBillCountBySupplier() {
        return supplierDao.getBillCountBySupplier();
    }




}
