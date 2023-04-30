package cn.lanqiao.controller;
import cn.lanqiao.pojo.User;
import cn.lanqiao.service.MemberService;
import cn.lanqiao.service.impl.MemberServiceImpl;
import cn.lanqiao.utils.PageUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet ("/MemberServlet.do")
public class MemberServlet extends HttpServlet {
    MemberService memberService= new MemberServiceImpl();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //处理请求和响应乱码
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        //用来获取前端的请求
        String value = req.getParameter("action");
        //做分页页面查询功能和模糊查询功能
        if (value.equals("limit")){
            //前端发送当前页面的请求
            String pageIndex = req.getParameter("pageIndex");
            //前端发送的每页显示条数
            String pageSize = req.getParameter("pageSize");
            //前端发送过来的输入框发送的信息
            String  name  = req.getParameter("username");
            //总条数
            int totalCount =memberService.getTotalCount();
            System.out.println("总条数："+totalCount);
            //每页的数
            List<User> depatrs = memberService.getDepatrs(name,(Integer.parseInt(pageIndex) - 1)*(Integer.parseInt(pageSize)),Integer.parseInt(pageSize));
            System.out.println("每页查询的数据"+depatrs);
            //存到工具类
            PageUtils pageUtils = new PageUtils<>(Integer.parseInt(pageIndex),Integer.parseInt(pageSize),totalCount,depatrs);
            //将pageUtils存储
            req.setAttribute("pageUtils",pageUtils);
            //将depatrs存储到页面上
            req.setAttribute("depatrs",depatrs);
            //将pageUtils传递到页面
            req.getRequestDispatcher("/member-list.jsp").forward(req,resp);
        }
    }
}

