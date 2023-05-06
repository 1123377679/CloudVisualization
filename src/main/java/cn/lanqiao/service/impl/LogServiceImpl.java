package cn.lanqiao.service.impl;

import cn.lanqiao.dao.LogDao;
import cn.lanqiao.dao.impl.LogDaoImpl;
import cn.lanqiao.pojo.LoginLog;
import cn.lanqiao.service.LogService;

import java.util.List;

public class LogServiceImpl implements LogService {
    LogDao logDao = new LogDaoImpl();

    @Override
    public int addLogs(LoginLog log) {
        return logDao.addLogs(log);
    }

    @Override
    public int deleteLogs(String id) {
        return logDao.deleteLogs(id);
    }

    @Override
    public List<LoginLog> queryMyLogs() {
        return logDao.queryMyLogs();
    }

    @Override
    public int getTotalCount() {
        return logDao.getTotalCount();
    }

    @Override
    public List<LoginLog> getDepatrs(Integer pageStart, Integer pageSize) {
        return logDao.getDepatrs(pageStart,pageSize);
    }
}
