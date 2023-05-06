package cn.lanqiao.service;

import cn.lanqiao.pojo.LoginLog;

import java.util.List;

public interface LogService {
    //新增登陆日志
    int addLogs(LoginLog log);

    //删除登陆日志
    int deleteLogs(String id);

    //查询日志信息
    List<LoginLog> queryMyLogs();

    //查询总数
    int getTotalCount();
    //查询每页数量
    List<LoginLog> getDepatrs(Integer pageStart , Integer pageSize);
}
