package cn.lanqiao.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.lanqiao.pojo.LoginLog;
import cn.lanqiao.pojo.User;
import cn.lanqiao.service.LogService;
import cn.lanqiao.service.UserService;
import cn.lanqiao.service.impl.LogServiceImpl;
import cn.lanqiao.service.impl.UserServiceImpl;
import cn.lanqiao.utils.Address;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/userServlet.do")
public class UserServlet extends HttpServlet {
    UserService userService = new UserServiceImpl();
    //添加用户登录的ip地址
    //引入业务层
    LogService logService = new LogServiceImpl();
    //创建ip工具类
    Address address = new Address();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //处理请求和响应乱码
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        String value = req.getParameter("action");
        //验证码操作
        //获取前端验证码内容
        String usercode = req.getParameter("usercode");
        HttpSession session = req.getSession();
        String syscode = String.valueOf(session.getAttribute("syscode"));

        //登录功能
        if (value.equals("login" )) {
                //equalsIgnoreCase:不区分大小写
                if (usercode.equalsIgnoreCase(syscode)){
                String username = req.getParameter("username");
                String password = req.getParameter("password");
                User user = new User(username, password);
                User login = userService.login(user);
                if (login != null) {
                    //登录成功，记录日志到数据库
                    //获取登录时间
                    DateTime date = DateUtil.date();
                    LoginLog log = new LoginLog(null,login.getUsername(),address.getAddress(),address.getIp(),String.valueOf(date),0);
                    int i = logService.addLogs(log);
                    //登录成功
                    //跳转到Supermarket-index页面
                    resp.sendRedirect("/Supermarket-index.jsp");
                    //个人信息名字：
                    session.setAttribute("loginUser",login);
                    //管理员名字：
                    session.setAttribute("ManageUser",login);
                } else {
//            System.out.println("登录失败.");
                    //存值并且赋值
                    req.setAttribute("message","账号或密码错误");
                    //转页面
                    req.getRequestDispatcher("/login.jsp").forward(req,resp);
                    }
                }else {
                req.setAttribute("message","验证码错误");
                req.getRequestDispatcher("/login.jsp").forward(req,resp);
            }
        }
        //退出功能
        if (value.equals("logout")) {
            //取消session
            session.invalidate();
            //返回到登录页面
            resp.sendRedirect("/login.jsp");
        }
    }
}
