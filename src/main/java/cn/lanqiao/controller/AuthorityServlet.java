package cn.lanqiao.controller;

import cn.lanqiao.pojo.User;
import cn.lanqiao.service.AuthorityService;
import cn.lanqiao.service.impl.AuthorityServiceImpl;
import cn.lanqiao.utils.PageUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/AuthorityServlet.do")
public class AuthorityServlet extends HttpServlet {
    AuthorityService authorityService = new AuthorityServiceImpl();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //处理请求和响应乱码
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        String value = req.getParameter("action");
        //查询
        if (value.equals("limit")){
            //前端传过来的数据
            String username = req.getParameter("username");
            //前端发送的当前页
            String pageIndex = req.getParameter("pageIndex");
            //前端发送的每页显示条数
            String pageSize = req.getParameter("pageSize");
            //通过数据库查询所有的数据
            List<User> usersList = authorityService.selectAll(username);
            //将数据都保存起来
            req.setAttribute("usersList",usersList);
            //通过数据库查询数据
            int totalCount = authorityService.getTotalCount();//总条数
            //每页的数据
            List<User> depatrs = authorityService.getDepatrs(username, (Integer.parseInt(pageIndex) - 1) * (Integer.parseInt(pageSize)), Integer.parseInt(pageSize));
            //存到工具类去
            PageUtils pageUtils = new PageUtils<>(Integer.parseInt(pageIndex),Integer.parseInt(pageSize),totalCount,depatrs);
            //将pageUtils存储起来
            req.setAttribute("pageUtils",pageUtils);
            //将departs存储起来
            req.setAttribute("depatrs",depatrs);
            //将pageUtils和departs传递给页面
            req.getRequestDispatcher("/Authority-list.jsp").forward(req,resp);
        }
        //跳转修改页面并回显
        if (value.equals("goUpdate")){
            //前端发送携带过来的id
            String id = req.getParameter("id");
            //System.out.println(id);
            //根据id找到对应的实体类对象
            User userById = authorityService.getUserById(id);
            req.setAttribute("userById",userById);
            //把值发送到前端页面
            req.getRequestDispatcher("/Authority-edit.jsp").forward(req,resp);
        }
        //修改操作
        if (value.equals("update")){
            String id = req.getParameter("id");
//            System.out.println("当前用户id:"+id);
            String userlei = req.getParameter("userlei");
            int i = authorityService.updateById(Integer.parseInt(id),userlei);
//            System.out.println(i);
            PrintWriter writer = resp.getWriter();
            if (i > 0){
                //添加成功
//                System.out.println("添加成功");
                writer.print("<script>" +
                        "alert('修改成功');" +
                        "window.location='/AuthorityServlet.do?action=limit&pageIndex=1&pageSize=5'" +
                        "</script>");
            }else {
                //添加失败
                writer.print("<script>" +
                        "alert('修改失败');location.href='/Authority-edit.jsp'" +
                        "</script>");
            }
        }
    }
}
