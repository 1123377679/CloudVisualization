package cn.lanqiao.dao.impl;

import cn.lanqiao.dao.LogDao;
import cn.lanqiao.pojo.LoginLog;
import cn.lanqiao.utils.DButils;

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
//
//    @Override
//    public List<LoginLog> queryMyLogs(String username) {
//        return DButils.commonQuery(LoginLog.class,"select * from tb_loginlog where isdelete = 0 and username = ?",username);
//    }

    @Override
    public int getTotalCount() {
        return DButils.commonQueryCount("select count(*) from tb_loginlog");
    }

    @Override
    public List<LoginLog> getDepatrs(Integer pageStart, Integer pageSize) {
        return DButils.commonQuery(LoginLog.class,"select * from tb_loginlog limit ?,?",pageStart,pageSize);
    }
}
