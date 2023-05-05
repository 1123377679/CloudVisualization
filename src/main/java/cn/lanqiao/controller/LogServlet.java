package cn.lanqiao.controller;

import cn.lanqiao.pojo.LoginLog;
import cn.lanqiao.service.LogService;
import cn.lanqiao.service.impl.LogServiceImpl;
import cn.lanqiao.utils.PageUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/LogServlet.do")
public class LogServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LogService logService = new LogServiceImpl();
        //处理请求和响应乱码
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        //获取前端的请求
        String value = req.getParameter("action");
        if (value.equals("mylogs")){
            //前端发送的当前页
            String pageIndex = req.getParameter("pageIndex");
            //前端发送的每页显示条数
            String pageSize = req.getParameter("pageSize");
            //总条数
            int totalCount = logService.getTotalCount();
            //每页的数据
            List<LoginLog> depatrs = logService.getDepatrs((Integer.parseInt(pageIndex) - 1) * (Integer.parseInt(pageSize)), Integer.parseInt(pageSize));
            //存到工具类中
            PageUtils pageUtils = new PageUtils<>(Integer.parseInt(pageIndex),Integer.parseInt(pageSize),totalCount,depatrs);
            //存储
            req.setAttribute("pageUtils",pageUtils);
            req.setAttribute("depatrs",depatrs);
            req.getRequestDispatcher("/login-log.jsp").forward(req,resp);


//            //将信息存在session中
//            HttpSession session = req.getSession();
//            User loginUser = (User) session.getAttribute("loginUser");
//            //查询登录日志
//            List<LoginLog> Logs = logService.queryMyLogs(loginUser.getUsername());
//            //转发数据到页面上
//            req.setAttribute("Logs",Logs);
//            req.getRequestDispatcher("/login-log.jsp").forward(req,resp);
        }

        //删除操作
        if (value.equals("delete")){
            //获取前端发送过来的id
            String id = req.getParameter("id");
            int i = logService.deleteLogs(id);
            PrintWriter writer = resp.getWriter();
            if (i>0){
                writer.print("<script>"+
                        "alert('日志删除成功');"+
                        "window.location.href = '/LogServlet.do?action=mylogs&pageIndex=1&pageSize=20'"+
                        "</script>");
            }else {
                writer.print("<script>"+
                        "alert('日志删除失败');"+
                        "window.location.href = '/LogServlet.do?action=mylogs&pageIndex=1&pageSize=20'"+
                        "</script>");
            }
        }
    }
}
