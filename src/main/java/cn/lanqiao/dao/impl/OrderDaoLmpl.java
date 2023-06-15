package cn.lanqiao.dao.impl;
import cn.lanqiao.dao.OrderDao;
import cn.lanqiao.pojo.Merchan;
import cn.lanqiao.utils.DButils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderDaoLmpl  implements OrderDao {
    @Override
    public int getCountm() {
        return DButils.commonQueryCount("SELECT COUNT(*) AS count FROM tb_order WHERE YEAR(createdAt) = 2023 AND MONTH(createdAt) = 6");
    }

    @Override
    public int getCounto() {
        return DButils.commonQueryCount("SELECT SUM(paymentAmount) FROM tb_order WHERE DATE_FORMAT(createdAt, '%Y-%m') = DATE_FORMAT(CURDATE(), '%Y-%m')");
    }

    @Override
    public List<Integer> getCountByOrder() {
        String sql = "SELECT COUNT(*) AS count,MONTH(createdAt) AS groupField FROM tb_order WHERE YEAR(createdAt) = YEAR(CURDATE()) GROUP BY MONTH(createdAt) ORDER BY MONTH(createdAt)";
        ArrayList<Integer> integers = DButils.commonQueryOrder(sql);
        return integers;

    }

    @Override
    public List<Integer> getCountByOrdert() {
        String sql = "SELECT (ROUND(SUM(CASE WHEN paymentStatus = 'PAID' THEN 1 ELSE 0 END) / COUNT(*), 1))*10 AS settlement_rate FROM tb_order WHERE YEAR(createdAt) = YEAR(CURDATE()) GROUP BY MONTH(createdAt) ORDER BY MONTH(createdAt)";
        ArrayList<Integer> integerst= DButils.commonQueryOrder(sql);
        return integerst;
    }


}
