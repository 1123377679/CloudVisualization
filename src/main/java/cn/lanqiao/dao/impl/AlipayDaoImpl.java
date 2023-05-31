package cn.lanqiao.dao.impl;

import cn.lanqiao.dao.AlipayDao;
import cn.lanqiao.pojo.Merchan;
import cn.lanqiao.pojo.User;
import cn.lanqiao.utils.DButils;

public class AlipayDaoImpl implements AlipayDao {

    @Override
    public int add(Merchan merch) {
        String sql = "insert into tb_order(id,orderNo,memberName,orderType,totalAmount,paymentStatus,createdAt,paymentAmount,isdelete) values(?,?,?,?,?,?,?,?,?)";
        return DButils.commonInsert(sql,
                merch.getId(),
                merch.getOrderNo(),
                merch.getMemberName(),
                merch.getOrderType(),
                merch.getTotalAmount(),
                merch.getPaymentStatus(),
                merch.getCreatedAt(),
                merch.getPaymentAmount(),
                merch.getIsdelete());
    }
}
