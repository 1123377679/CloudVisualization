package cn.lanqiao.controller;

import cn.lanqiao.pojo.User;
import cn.lanqiao.service.UserService;
import cn.lanqiao.service.impl.UserServiceImpl;

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
        if (usercode.equalsIgnoreCase(syscode)){
            //登录操作
            //equalsIgnoreCase:不区分大小写
            if(value.equals("login")) {
                String username = req.getParameter("username");
                String password = req.getParameter("password");
                User user = new User(username, password);
                User login = userService.login(user);
                if (login != null) {
                    //登录成功
                    //跳转到Supermarket-index页面
                    resp.sendRedirect("/Supermarket-index.jsp");
                } else {
//            System.out.println("登录失败.");
                    //存值并且赋值
                    req.setAttribute("message","账号或密码错误");
                    //转页面
                    req.getRequestDispatcher("/login.jsp").forward(req,resp);
                    }
                }
            } else {
            req.setAttribute("message","验证码错误");
            req.getRequestDispatcher("/login.jsp").forward(req,resp);
        }
    }
}
