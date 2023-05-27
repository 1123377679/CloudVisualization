package cn.lanqiao.dao.impl;

import cn.lanqiao.dao.LogDao;
import cn.lanqiao.pojo.BehaviorLog;
import cn.lanqiao.pojo.LoginLog;
import cn.lanqiao.utils.DButils;

import java.util.ArrayList;
import java.util.List;

public class LogDaoImpl implements LogDao {
    @Override
    public int addLogs(LoginLog log) {
        return DButils.commonUpdate("Insert into tb_loginlog values (null,?,?,?,?,?)",
                log.getUsername(),
                log.getAddress(),
                log.getIp(),
                log.getLogintime(),
                0);
    }

    @Override
    public int deleteLogs(String id) {
        return DButils.commonUpdate("Delete from tb_loginlog where id = ?",
                id);
    }

    @Override
    public List<LoginLog> queryMyLogs() {
        return DButils.commonQuery(LoginLog.class,"select * from tb_loginlog");
    }

    @Override
    public int getTotalCount() {
        return DButils.commonQueryCount("select count(*) from tb_loginlog");
    }

    @Override
    public List<LoginLog> getDepatrs(Integer pageStart, Integer pageSize) {
        return DButils.commonQuery(LoginLog.class,"select * from tb_loginlog order by id desc limit ?,?",pageStart,pageSize);
    }

    @Override
    public List<BehaviorLog> BehaviorLogs() {
        return DButils.commonQuery(BehaviorLog.class,"select * from operation_log");
    }

    @Override
    public List<BehaviorLog> queryBehaviorLogs(String username) {
        return DButils.commonQuery(BehaviorLog.class,"select * from operation_log where username = ?",username);
    }

    @Override
    public int getBehaviorTotalCount() {
        return DButils.commonQueryCount("select count(*) from operation_log");
    }

    @Override
    public List<BehaviorLog> getBehaviorDepatrs(String name,Integer pageStart, Integer pageSize) {
        StringBuilder stringBuilder = new StringBuilder("select * from operation_log");
        List<Object> parms = new ArrayList<Object>();
        if (name != null && !"".equals(name)){
            stringBuilder.append(" where username like ?");
            parms.add("%"+name+"%");
        }
        stringBuilder.append(" order by id desc limit ?,?");
        parms.add(pageStart);
        parms.add(pageSize);
        return DButils.commonQuery(BehaviorLog.class,stringBuilder.toString(),parms.toArray());
    }
}
