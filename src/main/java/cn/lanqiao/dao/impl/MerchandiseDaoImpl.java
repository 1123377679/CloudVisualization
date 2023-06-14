package cn.lanqiao.dao.impl;

import cn.lanqiao.dao.MerchandiseDao;
import cn.lanqiao.pojo.Merchan;
import cn.lanqiao.pojo.User;
import cn.lanqiao.utils.DButils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MerchandiseDaoImpl implements MerchandiseDao {
    @Override
    public int getCount() {
        return DButils.commonQueryCount("select count(*) from tb_order where isdelete = 0");
    }

    @Override
    public List<Merchan> getAll(String name, Integer pageStart, Integer pageSize) {
        //模糊查询预以及分页查询
        if (name != null && !"".equals(name)) {
            //模糊查询,查询出与此name有关的所有数据
            return DButils.commonQuery(Merchan.class, "select * from tb_order where isdelete = 0 and orderNo like ? limit ?,?", "%" + name + "%", pageStart, pageSize);
        } else {
            //查询出所有数据,进行分页
            return DButils.commonQuery(Merchan.class, "select * from tb_order where isdelete = 0 limit ?,?", pageStart, pageSize);
        }
    }

    @Override
    public Merchan getMerchanById(String id) {
        ArrayList<Merchan> merchans = DButils.commonQuery(Merchan.class, "select * from tb_order where isdelete = 0 and id =?", id);
        if (merchans.size() > 0) {
            return merchans.get(0);
        } else {
            return null;
        }
    }

    @Override
    public int updateById(Merchan merch) {
        return DButils.commonUpdate("update tb_order set orderNo= ? ,memberName = ?,orderType = ?,totalAmount = ?,paymentStatus = ?,createdAt = ?,paymentAmount = ? where id=?",
                merch.getOrderNo(),
                merch.getMemberName(),
                merch.getOrderType(),
                merch.getTotalAmount(),
                merch.getPaymentStatus(),
                merch.getCreatedAt(),
                merch.getPaymentAmount(),
                merch.getId());
    }

    @Override
    public int deleteById(String id) {
        return DButils.commonUpdate("update tb_order set isdelete = 1 where id =?", id);
    }

    @Override
    public int checkName(String name) {
        return DButils.commonQueryCount( "select count(*) from tb_order where memberName =?",name);
    }

    @Override
    public List<Merchan> getOrders() {
        return DButils.commonQuery(Merchan.class, "select orderNo,totalAmount,orderType,paymentStatus FROM tb_order");
    }
}
