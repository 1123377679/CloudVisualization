package cn.lanqiao.controller;

import cn.lanqiao.pojo.Merchan;
import cn.lanqiao.pojo.User;
import cn.lanqiao.service.MerchanService;

import cn.lanqiao.service.impl.MerchanServiceImpl;
import cn.lanqiao.utils.PageUtils;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet("/Merchandise.do")
public class Merchandise extends HttpServlet {
    MerchanService merchanService = new MerchanServiceImpl();

    @SneakyThrows
    @Override
    public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
        //处理请求和响应乱码
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        //用来获取前端的请求
        String value = req.getParameter("action");
        //做分页页面查询功能和模糊查询功能
        if (value.equals("limit")) {
            //前端发送当前页面的请求
            String pageIndex = req.getParameter("pageIndex");
            //前端发送的每页显示条数
            String pageSize = req.getParameter("pageSize");
            //前端发送过来的输入框发送的信息
            String name = req.getParameter("username");
            //总条数
            int totalCount = merchanService.getCount();
//            System.out.println("总条数：" + totalCount);
            //每页的数
            List<Merchan> depatrs = merchanService.getAll(name, (Integer.parseInt(pageIndex) - 1) * (Integer.parseInt(pageSize)), Integer.parseInt(pageSize));
//            System.out.println("每页查询的数据" + depatrs);
            //存到工具类
            PageUtils pageUtils = new PageUtils<>(Integer.parseInt(pageIndex), Integer.parseInt(pageSize), totalCount, depatrs);
            //将pageUtils存储
            req.setAttribute("pageUtils", pageUtils);
            //将depatrs存储到页面上
            req.setAttribute("depatrs", depatrs);
            //将pageUtils传递到页面
            req.getRequestDispatcher("/Merchandise.jsp").forward(req, resp);
        }
        //跳转修改页面并且回显
        if (value.equals("goUpdate")) {
            String id = req.getParameter("id");
            //根据id找到实体类对象
            Merchan merchanById = merchanService.getMerchanById(id);
            //存值，好让前端获取内容
            req.setAttribute("merchanById", merchanById);
            //将查询的id信息发送到页面
            req.getRequestDispatcher("/Merchandise-edit.jsp").forward(req, resp);
        }
        //编辑功能
        if (value.equals("update")) {
            String id = req.getParameter("id");
            String orderno = req.getParameter("orderNo");
            String memberName = req.getParameter("userName");
            String ordertype = req.getParameter("orderType");
            String totalamount = req.getParameter("totalAmount");
            String paymentstatus = req.getParameter("userlei");
            String createdat = req.getParameter("createdAt");
            String paymentamount = req.getParameter("paymentAmount");
            System.out.println(id);
            System.out.println(orderno);
            System.out.println(memberName);
            System.out.println(ordertype);
            System.out.println(totalamount);
            System.out.println(createdat);
            System.out.println(paymentamount);
            //转换数据类型让它们匹配
            BigDecimal totalAmount = new BigDecimal(totalamount);
            Timestamp createdAt = Timestamp.valueOf(createdat);
            BigDecimal paymentAmount = new BigDecimal(paymentamount);
            //将值赋值给实体类
            Merchan merchan = new Merchan(Integer.parseInt(id),orderno, memberName, ordertype,totalAmount,Integer.parseInt(paymentstatus),createdAt,paymentAmount,0);
            System.out.println(merchan);
            int i = merchanService.updateById(merchan);
            //后端王前端里面写代码
            PrintWriter writer = resp.getWriter();
            if (i > 0) {
                writer.print("<script>" +
                        "alert('订单信息修改成功');" +
                        "window.location='/Merchandise.do?action=limit&pageIndex=1&pageSize=5'"
                        + "</script>");
            } else {
                writer.print("<script>" +
                        "alert('订单信息修改失败');" +
                        "location.href='/Merchandise-edit.jsp'"
                        + "</script>");
            }
        }
        //回显功能
        if (value.equals("details")) {
            String id = req.getParameter("id");
            Merchan merchanById = merchanService.getMerchanById(id);
            req.setAttribute("merchanById", merchanById);
            req.getRequestDispatcher("/Merchandise-view.jsp").forward(req, resp);
        }
        //删除功能
        if (value.equals("delete")) {
            String id = req.getParameter("id");
            int i = merchanService.deleteById(id);
            PrintWriter writer = resp.getWriter();
            if (i > 0) {
                //用户删除成功
                writer.println("<script>" +
                        "alert('订单信息删除成功');" +
                        "window.location.href='/Merchandise.do?action=limit&pageIndex=1&pageSize=5'"
                        + "</script>");
            } else {
                //用户删除失败
                writer.println("<script>" +
                        "alert('订单信息删除失败');" +
                        "window.location.href='/Merchandise.do?action=limit&pageIndex=1&pageSize=5'"
                        + "</script>");
            }
        }
        //批量删除
        if (value.equals("delAll")){
            String checkId = req.getParameter("id");
            System.out.println(checkId);
            String[] split = checkId.split(",");
            String[] ids = new String[split.length];
            PrintWriter writer = resp.getWriter();
            if (split.length>0){
                for (int i = 0; i < split.length; i++) {
                    ids[i] = split[i];
                }
            }
            try {
                for (String id : ids){
                    //循环删除
                    merchanService.deleteById(id);
                }
                writer.print("<script>"+
                        "alert('订单信息删除成功');"+
                        "window.location.href = '/Merchandise.do?action=limit&pageIndex=1&pageSize=5'"+
                        "</script>");
            }catch (Exception e){
                writer.print("<script>"+
                        "alert('订单信息删除失败');"+
                        "window.location.href = '/Merchandise.do?action=limit&pageIndex=1&pageSize=5'"+
                        "</script>");
            }
        }
        //判断用户输入会员姓名格式是否正确
        if (value.equals("checkUserName")) {
            //用户输入的用户名
            String username = req.getParameter("username");
            String username2 = "[\\u4e00-\\u9fa5]{1,6}";
            //判断
            PrintWriter writer = resp.getWriter();
            if (username.matches(username2)) {
                    //格式正确
                    writer.print(0);
                } else {
                writer.print(2);
            }
        }
        //判断订单类型格式是否正确
        if (value.equals("checkOrderType")) {
            String orderType = req.getParameter("orderType");
            String orderType2 = "[\\u4e00-\\u9fa5]{1,6}";
            //判断
            PrintWriter writer = resp.getWriter();
            if (orderType.matches(orderType2)) {
                //格式正确
                writer.print(0);
            } else {
                writer.print(2);
            }
        }

    }
}
