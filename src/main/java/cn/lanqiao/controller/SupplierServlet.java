package cn.lanqiao.controller;

import cn.lanqiao.pojo.Bill;
import cn.lanqiao.pojo.Supplier;
import cn.lanqiao.service.BillService;
import cn.lanqiao.service.SupplierService;
import cn.lanqiao.service.impl.BillServiceImpl;
import cn.lanqiao.service.impl.SupplierServiceImpl;
import cn.lanqiao.utils.DateUtils;
import cn.lanqiao.utils.ExprotCellStyle;
import cn.lanqiao.utils.PageUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;

@WebServlet("/SupplierServlet.do")
public class SupplierServlet extends HttpServlet {
    BillService billService = new BillServiceImpl();
    SupplierService supplierService=  new SupplierServiceImpl();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //处理请求和响应乱码
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        //用来获取前端的请求
        String value = req.getParameter("action");
        //分页查询

        if (value.equals("limit")){
            //前端发送当前页面的请求
            String pageIndex = req.getParameter("pageIndex");
            //前端发送的每页显示条数
            String pageSize = req.getParameter("pageSize");
            //前端发送过来的输入框发送的信息
            String  name  =req.getParameter("username");
            //总条数
            int totalCount =supplierService.getTotalCount();
            System.out.println("总条数："+totalCount);
            //每页的数
            List<Supplier> depatrs = supplierService.getDepatrs(name,(Integer.parseInt(pageIndex) - 1)*(Integer.parseInt(pageSize)),Integer.parseInt(pageSize));
            System.out.println("每页查询的数据"+depatrs);
            //存到工具类
            PageUtils pageUtils = new PageUtils<>(Integer.parseInt(pageIndex),Integer.parseInt(pageSize),totalCount,depatrs);
            //将pageUtils存储
            req.setAttribute("pageUtils",pageUtils);
            //将depatrs存储到页面上
            req.setAttribute("depatrs",depatrs);
            //将pageUtils传递到页面
            req.getRequestDispatcher("/supplier-list.jsp").forward(req,resp);
        }
        //添加功能
        if (value.equals("add")){
            //拿前端传过来的值
            String name= req.getParameter("name");
            String linkman= req.getParameter("linkman");
            String phone= req.getParameter("phone");
            String address= req.getParameter("address");
            String fax= req.getParameter("fax");
            String description= req.getParameter("description");
            //前端的值给实体类
            Supplier supplier=new Supplier(null,name,linkman,phone,address,fax,description,0);
            System.out.println(supplier);
            //存入数据库
            int i=supplierService.addSuppliers(supplier);
            PrintWriter writer = resp.getWriter();
            if (i>0){
                writer.print("<script>"+
                        "alert('用户添加成功');"+
                        "window.parent.location='/SupplierServlet.do?action=limit&pageIndex=1&pageSize=5';"+
                        "</script>");
            }else {
                writer.print("<script>"+
                        "alert('用户添加失败');"+
                        "location.href='/supplier-add.jsp';"+
                        "</script>");
            }

        }

        //跳转修改页面并回显
        if(value.equals("goUpdate")){
            //拿前端传过来的值
           String id = req.getParameter("id");
           //根据id找到对应的实体类对象
           Supplier supplierById = supplierService.getSupplierById(id);
            //先存储到页面上
            req.setAttribute("supplierById",supplierById);
            //把值发送到前端页面
            req.getRequestDispatcher("/supplier-edit.jsp").forward(req,resp);
        }
        //修改功能
        if (value.equals("update")){
           String id = req.getParameter("id");
            //拿前端传过来的值
            String name= req.getParameter("name");
            String linkman= req.getParameter("linkman");
            String phone= req.getParameter("phone");
            String address= req.getParameter("address");
            String fax= req.getParameter("fax");
            String description= req.getParameter("description");
            //前端的值给实体类
            Supplier supplier= new  Supplier(Integer.parseInt(id),name,linkman,phone,address,fax,description,0);
            int i = supplierService.updateById(supplier);
            PrintWriter writer = resp.getWriter();
            if (i>0){
                writer.print("<script>"+
                        "alert('用户修改成功');"+
                        "window.parent.location='/SupplierServlet.do?action=limit&pageIndex=1&pageSize=5';"+
                        "</script>");
            }else {
                writer.print("<script>"+
                        "alert('用户修改失败');"+
                        "location.href='/supplier-add.jsp';"+
                        "</script>");
            }
        }

        //查看详情
        if (value.equals("details")){
          String id = req.getParameter("id");
         //根据id查询用户信息
           Supplier supplierId= supplierService.getSupplierById(id);
           //先存储到页面上
            req.setAttribute("supplier",supplierId);
            //再存储到前端页面上
            req.getRequestDispatcher("/supplier-view.jsp").forward(req,resp);
        }
        //删除
        if (value.equals("delete")){
            String id1 = req.getParameter("id");
            int i = supplierService.deleteById(id1);
            PrintWriter writer = resp.getWriter();
            if (i>0){
                writer.print("<script>"+
                        "alert('用户删除成功');"+
                        "window.location.href='/SupplierServlet.do?action=limit&pageIndex=1&pageSize=5';"+
                        "</script>");
            }else {
                writer.print("<script>"+
                        "alert('用户删除失败');"+
                        "window.location.href='/SupplierServlet.do?action=limit&pageIndex=1&pageSize=5';"+
                        "</script>");
            }
        }
    }

}
