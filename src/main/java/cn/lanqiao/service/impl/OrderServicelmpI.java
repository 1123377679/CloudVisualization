package cn.lanqiao.service.impl;

import cn.lanqiao.dao.OrderDao;
import cn.lanqiao.dao.impl.OrderDaoLmpl;
import cn.lanqiao.pojo.Merchan;
import cn.lanqiao.service.OrderService;

import java.util.List;
import java.util.Map;

public class OrderServicelmpI implements OrderService {
    OrderDao orderdao =new OrderDaoLmpl();
    @Override
    public int getCountm() {
        return orderdao.getCountm();
    }

    @Override
    public int getCounto() {
        return orderdao.getCounto();
    }

    @Override
    public List<Integer> getCountByOrder() {
        return orderdao.getCountByOrder();
    }

    @Override
    public List<Integer> getCountByOrdert() {
        return orderdao.getCountByOrdert();
    }


}
