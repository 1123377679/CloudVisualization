package cn.lanqiao.service;

import cn.lanqiao.pojo.Merchan;

import java.util.List;
import java.util.Map;

public interface OrderService {
    //查询总数
    int getCountm();
    int getCounto();
    //柱状图
    List<Integer> getCountByOrder();
    //折线图
    List<Integer> getCountByOrdert();
}
