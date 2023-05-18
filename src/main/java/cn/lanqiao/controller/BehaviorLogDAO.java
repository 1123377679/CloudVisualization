package cn.lanqiao.controller;

import cn.lanqiao.pojo.BehaviorLog;
import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BehaviorLogDAO {
    private static final String DB_URL = "jdbc:mysql://47.120.39.138/supermarket_db?characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai&allowMultiQueries=true&zeroDateTimeBehavior=CONVERT_TO_NULL";  // 替换为你的数据库连接地址
    private static final String DB_USERNAME = "lhl";  // 替换为你的数据库用户名
    private static final String DB_PASSWORD = "Ab123456";  // 替换为你的数据库密码

    public void insertBehaviorLog(BehaviorLog behaviorLog) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            DriverManager.registerDriver(new Driver());
            String sql = "INSERT INTO operation_log (username, type, description, model, ip, operationtime,result ) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, behaviorLog.getUsername());
            stmt.setString(2, behaviorLog.getType());
            stmt.setString(3, behaviorLog.getDescription());
            stmt.setString(4, behaviorLog.getModel());
            stmt.setString(5, behaviorLog.getIp());
            stmt.setString(6, behaviorLog.getOperationtime());
            stmt.setString(7, behaviorLog.getResult());
            stmt.executeUpdate();
            System.out.println("行为日志记录成功");
        } catch (SQLException e) {
            e.printStackTrace();
            // 处理数据库异常
            System.out.println("行为日志记录失败");
        }
    }
}
