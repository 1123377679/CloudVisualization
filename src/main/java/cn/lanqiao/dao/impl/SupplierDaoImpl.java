package cn.lanqiao.dao.impl;

import cn.lanqiao.dao.SupplierDao;
import cn.lanqiao.pojo.Supplier;
import cn.lanqiao.utils.DButils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SupplierDaoImpl implements SupplierDao {

    @Override
    public int getTotoCount() {
        return DButils.commonQueryCount("select count(*) from tb_supplier where isdelete=0");
    }

    @Override
    public List<Supplier> getDepatrs(String name, Integer pageStart, Integer pageSize) {
        //模糊查询和分页查询
        if (name != null && !"".equals(name)) {
            return DButils.commonQuery(Supplier.class, "select * from tb_supplier where isdelete=0 and name like ? limit ?,?", "%" + name + "%", pageStart, pageSize);
        } else {
            return DButils.commonQuery(Supplier.class, "select * from tb_supplier where isdelete=0  limit ?,?", pageStart, pageSize);
        }
    }

    @Override
    public int addSupplier(Supplier supplier) {
        return DButils.commonInsert("insert into tb_supplier values(null,?,?,?,?,?,?,0)",
                supplier.getName(),
                supplier.getLinkman(),
                supplier.getPhone(),
                supplier.getAddress(),
                supplier.getFax(),
                supplier.getDescription());
    }

    @Override
    public Supplier getSupplierById(String id) {
        ArrayList<Supplier> suppliers = DButils.commonQuery(Supplier.class, "select * from tb_supplier where isdelete=0 and id=?", id);
        if (suppliers.size()>0){
            return suppliers.get(0);
        }else {
            return null;
        }
    }

    @Override
    public int updateById(Supplier supplier) {
        return DButils.commonUpdate("update tb_supplier set name=?,linkman=?,phone=?,address=?,fax=?,description=? where id=? ",
                supplier.getName(),
                supplier.getLinkman(),
                supplier.getPhone(),
                supplier.getAddress(),
                supplier.getFax(),
                supplier.getDescription(),
                supplier.getId());
    }

    @Override
    public int deleteById(String id) {
        return DButils.commonUpdate("update tb_supplier set isdelete=1 where id=?", id);
    }

    @Override
    public int checkName(String name) {
        return DButils.commonQueryCount("select  count(*) from tb_supplier where name=?", name);
    }

    @Override
    public int checkPhone(String phone) {
        return DButils.commonQueryCount("select  count(*) from tb_supplier where phone=?", phone);
    }

    @Override
    public int checkLinkman(String linkman) {
        return DButils.commonQueryCount("select  count(*) from tb_supplier where linkman=?", linkman);
    }

    @Override
    public Supplier findById(String id) {
        ArrayList<Supplier> suppliers = DButils.commonQuery(Supplier.class, "select * from tb_supplier where id=?", id);
        if (suppliers.size()>0){
            return suppliers.get(0);
        }else {
            return null;
        }
    }

    @Override
    public List<Supplier> selectAllSupplier() {
        return DButils.commonQuery(Supplier.class ,"select * from tb_supplier");
    }

    @Override
    public Map<String, Integer> getBillCountBySupplier() {
        String sql = "select tb_supplier.name , COUNT(tb_bills.providerid) as bill_count from tb_supplier LEFT JOIN tb_bills ON tb_supplier.id = tb_bills.providerid GROUP BY tb_supplier.name";
        return DButils.commonQueryCountMap(sql);
    }
}
