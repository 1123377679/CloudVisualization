package cn.lanqiao.dao.impl;

import cn.lanqiao.dao.BillDao;
import cn.lanqiao.pojo.Bill;
import cn.lanqiao.pojo.User;
import cn.lanqiao.utils.DButils;

import java.util.ArrayList;
import java.util.List;
//记得把BillDaoImpl写对，你写成Imol了
public class BillDaoImpl implements BillDao {
    @Override
    public int getTotalCount() {
        return DButils.commonQueryCount("select count(*) from tb_bills where isdelete = 0");
    }

    @Override
    public List<Bill> getDepatrs(String name,String providerId, String ispay, Integer pageStart, Integer pageSize) {
        //首先要做模糊查询以及分页
        StringBuilder stringBuilder = new StringBuilder("select * from tb_bills where isdelete = 0");
        List<Object> parms = new ArrayList<Object>();
        if (name!=null&&!"".equals(name)){
            stringBuilder.append(" and title like ?");
            parms.add("%"+name+"%");
        }
        if (providerId!=null&&!"-1".equals(providerId)){
            stringBuilder.append(" and providerId=?");
            parms.add(providerId);
        }
        if (ispay!=null&&!"-1".equals(ispay)){
            stringBuilder.append(" and ispay=?");
            parms.add(ispay);
        }
        stringBuilder.append(" limit ?,?");
        parms.add(pageStart);
        parms.add(pageSize);
        return DButils.commonQuery(Bill.class,stringBuilder.toString(),parms.toArray());
//        if (name != null && !"".equals(name)) {
//            //前端有条件需要查询
//            return DButils.commonQuery(Bill.class, "select * from tb_bills where isdelete = 0 and title like ? and limit ?,?", "%" + name + "%", pageStart, pageSize);
//        } else {
//            //如果没有输入条件，则按照所有条件进行分页
//            return DButils.commonQuery(Bill.class, "select * from tb_bills where isdelete = 0 limit ?,?", pageStart, pageSize);//这里少传了一个参数，这里有两个问号
//        }
    }

    @Override
    public int add(Bill bill) {
        return DButils.commonInsert("insert into tb_bills values(null,?,?,?,?,?,?,0)",
                bill.getTitle(),
                bill.getUnit(),
                bill.getNum(),
                bill.getMoney(),
                bill.getProviderid(),
                bill.getIspay());
    }

    @Override
    public Bill getBillById(String id) {
        ArrayList<Bill> bills = DButils.commonQuery(Bill.class, "select * from tb_bills where isdelete = 0 and id =?", id);
        if (bills.size() > 0) {
            return bills.get(0);
        } else {
            return null;
        }
    }

    @Override
    public int updaeteById(Bill bill) {
        return DButils.commonUpdate("update tb_bills set title=?,unit=?,num=?,money=?,providerid=?,ispay=? where id=?",
                bill.getTitle(),
                bill.getUnit(),
                bill.getNum(),
                bill.getMoney(),
                bill.getProviderid(),
                bill.getIspay(),
                bill.getId());
    }

    @Override
    public int deleteById(String id) {
        return DButils.commonUpdate("update tb_bills set isdelete = 1 where id = ? ",id);
    }
}