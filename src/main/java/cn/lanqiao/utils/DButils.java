package cn.lanqiao.utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

public class DButils {
    private static final String url;
    private static final String username;
    private static final String password;

    static {
        //获取DBConfig.properties中的数据
        Properties p = new Properties();
        try {
            p.load(DButils.class.getClassLoader().getResourceAsStream("jdbc.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String driverName = p.getProperty("driverName");
        url = p.getProperty("url");
        username = p.getProperty("userName");
        password = p.getProperty("passWord");

        try {
            //加载驱动包
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //获取Connection对象
    public static Connection getConnection() throws SQLException {

        Connection connection = local.get();

        if (connection == null) {
            connection = DriverManager.getConnection(url, username, password);

            //设置事务隔离级别
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

            local.set(connection);
        }

        return connection;
    }

    //关闭资源
    public static void close(Connection connection, Statement statement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                //判断连接是否开启事务
                if (connection.getAutoCommit()) {//没有开启
                    connection.close();
                    local.set(null);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static final ThreadLocal<Connection> local = new ThreadLocal<>();

    // 开启事务
    public static void startTransaction() throws SQLException {
        //获取连接对象
        Connection connection = getConnection();
        //开启事务
        connection.setAutoCommit(false);

    }

    //提交事务
    public static void commit() throws SQLException {
        Connection connection = local.get();
        connection.commit();
        connection.close();
        local.set(null);
    }

    //回滚事务
    public static void rollback() throws SQLException {
        Connection connection = local.get();
        connection.rollback();
        connection.close();
        local.set(null);
    }


    /**
     * 根据条件查询总数
     * @param sql
     * @param param
     * @return
     */
    public static int commonQueryCount(String sql, Object... param) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int count = 0;
        try {
            connection = getConnection();
            //设置sql语句
            statement = connection.prepareStatement(sql);
            //设置参数
            for (int i = 0; i < param.length; i++) {
                statement.setObject(i + 1, param[i]);
            }
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DButils.close(connection, statement, resultSet);
        }
        return count;
    }


    //insert into student(name,sex,age,....) values(?,?,?,?,?)
    //inset into user(username,password) values(?,?)

    //主键回填
    public static int commonInsert(String sql, Object... param) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();

            //设置sql语句，设置主键回填
            statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            //设置参数
            for (int i = 0; i < param.length; i++) {
                statement.setObject(i + 1, param[i]);
            }
            statement.executeUpdate();

            //获取主键
            resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                int primaryKey = resultSet.getInt(1);
                return primaryKey;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DButils.close(connection, statement, null);
        }
        return -1;
    }

    //更新方法(添加、删除、修改)
    public static int commonUpdate(String sql, Object... param) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            for (int i = 0; i < param.length; i++) {
                statement.setObject(i + 1, param[i]);
            }
            int num = statement.executeUpdate();
            return num;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    //查询数据
    public static <T> ArrayList<T> commonQuery(Class<T> clazz, String sql, Object... param) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            //设置参数
            for (int i = 0; i < param.length; i++) {
                statement.setObject(i + 1, param[i]);
            }
            resultSet = statement.executeQuery();

            //获取表信息
            ResultSetMetaData metaData = resultSet.getMetaData();
            //获取字段个数
            int columnCount = metaData.getColumnCount();

            ArrayList<T> list = new ArrayList<>();

            while (resultSet.next()) {//判断是否有迭代的数据行 （5-周华健-40-13000-Java）

                //利用反射创建对象
                T t = clazz.newInstance();

                //遍历获取字段名
                for (int i = 1; i <= columnCount; i++) {
                    //获取字段名
                    String name = metaData.getColumnName(i);
                    //获取对应字段名上的数据
                    Object value = resultSet.getObject(name);

                    //获取对象中的属性对象
                    Field field = getField(clazz, name);
                    if (field != null) {
                        //设置修改权限
                        field.setAccessible(true);
                        //利用反射向对象设置属性
                        field.set(t, value);
                    }
                }

                list.add(t);
            }
            return list;
        } catch (SQLException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        } finally {
            //关闭
            DButils.close(connection, statement, resultSet);
        }
        return null;
    }
    //查询供应商底下的账单信息(供应商的名字和供应商对应的账单数量)
    public static Map<String,Integer> commonQueryCountMap(String sql){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        //创建一个map集合
        Map<String,Integer> map = new HashMap<>();
        try {
            connection = getConnection();//获取驱动连接
            statement = connection.prepareStatement(sql);//编译sql
            resultSet = statement.executeQuery();//执行sql
            //处理sql里面的值
            while (resultSet.next()){
                //供应商名字
                String name = resultSet.getString("name");
                //账单数量
                int billCount = resultSet.getInt("bill_count");
                //存入map集合中
                map.put(name,billCount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //关闭所有的流
            DButils.close(connection,statement,resultSet);
        }
        return map;
    }

    public static ArrayList<Integer> commonQueryOrder(String sql) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            ArrayList<Integer> list = new ArrayList<>();

            while (resultSet.next()) {
                int count = resultSet.getInt(1);
                list.add(count);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DButils.close(connection, statement, resultSet);
        }
        return null;
    }

    //查询会员列表的生日信息
    public static ArrayList<Integer> commonQueryCountAll(String sql, Object... param) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int count = 0;
        //int month = 0;
        ArrayList<Integer> list = new ArrayList<>();
        try {
            connection = getConnection();
            Statement stmt = connection.createStatement();
            resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                //count = resultSet.getInt(1);
                count = resultSet.getInt("count");
                list.add(count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DButils.close(connection, statement, resultSet);
        }
        return list;
    }



    //查询会员底下的年龄
    public static ArrayList<Integer> countUsersByAgeGroup(String sql) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<Integer> arrayList = new ArrayList<>();
        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                int userCount = resultSet.getInt("user_count");
                arrayList.add(userCount);
            }
            return arrayList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭
            DButils.close(connection, statement, resultSet);
        }
        return null;
    }

    //查询供应商地区
    public static Map<String,Integer> commonQueryArea(String sql) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        //创建一个map集合
        Map<String,Integer> map = new HashMap<>();
        try {
            connection = getConnection();//获取驱动连接
            statement = connection.prepareStatement(sql);//编译sql
            resultSet = statement.executeQuery();//执行sql
            //处理sql里面的值
            while (resultSet.next()){
                //供应商地区
                String supplierArea = resultSet.getString("supplierArea");
                //供应商地区数量
                int supplierCount = resultSet.getInt("supplierCount");
                //存入map集合中
                map.put(supplierArea,supplierCount);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭
            DButils.close(connection, statement, resultSet);
        }
        return map;
    }
    //根据供应商姓名查询账单外键id
    public static int getOrderIdBySupplierName(String sql,Object... param) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();//获取驱动连接
            statement = connection.prepareStatement(sql);//编译sql

            //设置参数值
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    statement.setObject(i + 1, param[i]);
                }
            }

            resultSet = statement.executeQuery();//执行sql
            //处理sql里面的值
            while (resultSet.next()){
                int providerid = resultSet.getInt("providerid");
                return providerid;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭
            DButils.close(connection, statement, resultSet);
        }
        return -1;
    }


    //获取类上的属性对象
    public static Field getField(Class<?> clazz, String name) {
        Field field = null;
        for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
            try {
                field = c.getDeclaredField(name);
            } catch (NoSuchFieldException e) {
            }
        }
        return field;
    }
    //根据手机号查找用户名
    // 根据手机号查询用户名，并返回 String 类型结果
    public static String getUserNameByPhone(String sql, Object... params) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // 获取数据库连接
            conn = getConnection();
            // 编译 SQL 语句
            stmt = conn.prepareStatement(sql);

            // 设置参数值
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    stmt.setObject(i + 1, params[i]);
                }
            }

            // 执行查询
            rs = stmt.executeQuery();

            // 处理查询结果
            if (rs.next()) {
                return rs.getString("username");
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            DButils.close(conn, stmt, rs);
        }

        // 查询失败或出现异常情况时返回 null
        return null;
    }

}
