package cn.lanqiao.service;

import cn.lanqiao.pojo.BehaviorLog;
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

    //查询行为日志信息
    List<BehaviorLog> BehaviorLogs();
    //查询行为日志
    List<BehaviorLog> queryBehaviorLogs(String username);
    //查询行为日志总数
    int getBehaviorTotalCount();
//    //查询行为日志每页数量
//    List<BehaviorLog> getBehaviorDepatrs(Integer pageStart , Integer pageSize);
    //查询行为日志每页数量
    List<BehaviorLog> getBehaviorDepatrs(String name,Integer pageStart , Integer pageSize);
}
