package cn.lanqiao.controller;


import cn.lanqiao.pojo.Bill;


import cn.lanqiao.pojo.Supplier;
import cn.lanqiao.service.BillService;
import cn.lanqiao.service.SupplierService;
import cn.lanqiao.service.impl.BillServiceImpl;
import cn.lanqiao.service.impl.SupplierServiceImpl;
import cn.lanqiao.utils.PageUtils;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/BillServlet.do")
public class BillServlet extends HttpServlet {
    BillService billService = new BillServiceImpl();
    SupplierService supplierService = new SupplierServiceImpl();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //处理请求和响应乱码
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        String value = req.getParameter("action");
        //做分页查询功能和模糊查询功能
        if (value.equals("limit")) {
            //前端发送的当前页数
            String pageIndex = req.getParameter("pageIndex");
            //前端发送的每页显示条数
            String pageSize = req.getParameter("pageSize");
            //前端发送过来的输入框是否有信息
            String name = req.getParameter("username");
            String providerid = req.getParameter("providerid");
            String ispay = req.getParameter("ispay");
            //总条数
            int totalCount = billService.getTotalCount();
            System.out.println("总条数：" + totalCount);
            //供应商数据回显
            List<Supplier> suppliersList = supplierService.selectAll(null);
            //供应商的集合存储转发
            req.setAttribute("suppliersList",suppliersList);//
            //每页的数据
            //起始索引从0开始，起始索引=（查询页码-1）*每页显示记录数
            List<Bill> depatrs = billService.getDepatrs(name,providerid,ispay, (Integer.parseInt(pageIndex) - 1 )* (Integer.parseInt(pageSize)), Integer.parseInt(pageSize));
            System.out.println("每页查询的数据："+depatrs);//值能查询到了
            //存到工具类去
            PageUtils pageUtils = new PageUtils<>(Integer.parseInt(pageIndex),Integer.parseInt(pageSize),totalCount,depatrs);
            //将pageUtils存储起来
            req.setAttribute("pageUtils",pageUtils);
            //将departs存储起来
            req.setAttribute("departs",depatrs);
            //将pageUtils和departs传递给页面
            req.getRequestDispatcher("/bill-list.jsp").forward(req, resp);//等我捋一下思路
        }

        //新增
        if (value.equals("add")){
            //获取前端的数据
            String title = req.getParameter("title");
            String unit = req.getParameter("unit");
            String num = req.getParameter("num");
            String money = req.getParameter("money");
            String providerid = req.getParameter("providerid");
            String ispay = req.getParameter("ispay");
            //前端赋值给实体类
            Bill bill = new Bill(null,title,unit,Integer.parseInt(num),Integer.parseInt(money),Integer.parseInt(providerid),Integer.parseInt(ispay),0);
            System.out.println(bill);
            //保存到数据库中
            int i =  billService.add(bill);
            //后端往前端写js代码
            PrintWriter writer = resp.getWriter();
            if (i>0){
                writer.print("<script>" +
                        "alert('用户新增成功');" +
                        "window.parent.location='/BillServlet.do?action=limit&pageIndex=1&pageSize=5'" +
                        "</script>");
            }else {
                writer.print("<script>" +
                        "alert('用户新增失败');location.href = '/bill-add.jsp'" +
                        "</script>");
            }
        }
        //跳转修改页面
        if (value.equals("goUpdate")){
            //获取页面传过来的ID
            String id = req.getParameter("id");
            //根据id找到相应的实体类对象
            Bill bliiById = billService.getBillById(id);

            //供应商数据回显
            List<Supplier> suppliersList = supplierService.selectAll(null);

            //供应商的集合存储转发
            req.setAttribute("suppliersList",suppliersList);//

            System.out.println(bliiById);
            //存值
            req.setAttribute("bliiById",bliiById);
            //把值发送到前端页面
            req.getRequestDispatcher("/bill-edit.jsp").forward(req, resp);


    }
        //修改页面
        if(value.equals("update")){
            //获取前端的数据
            String id = req.getParameter("id");
            String title = req.getParameter("title");
            String unit = req.getParameter("unit");
            String num = req.getParameter("num");
            String money = req.getParameter("money");
            String providerid = req.getParameter("providerid");
            String ispay = req.getParameter("ispay");




            //前端赋值给实体类
            //System.out.println(providerid);
            Bill bill = new Bill(Integer.parseInt(id),title,unit,Integer.parseInt(num),Integer.parseInt(money),Integer.parseInt(providerid),Integer.parseInt(ispay),0);
            int i = billService.updaeteById(bill);
            PrintWriter writer = resp.getWriter();
            if (i>0){
                writer.print("<script>" +
                        "alert('修改成功');" +
                        "window.parent.location='/BillServlet.do?action=limit&pageIndex=1&pageSize=5'" +
                        "</script>");
            }else {
                writer.print("<script>" +
                        "alert('修改失败');location.href = '/bill-edit.jsp'" +
                        "</script>");
            }


        }
        //显示详情页面
        if(value.equals("details")){

            String id = req.getParameter("id");
            System.out.println(id);
            //根据id查询用户信息
            Bill billById = billService.getBillById(id);
            //存储起来
            req.setAttribute("bills",billById);
            //发送到前端页面
            req.getRequestDispatcher("/bill-view.jsp").forward(req, resp);

        }
        //删除账单信息
        if(value.equals("delete")){
            //获取页面传过来的ID
            String id = req.getParameter("id");
            int i = billService.deleteById(id);
            PrintWriter writer = resp.getWriter();
            if (i>0){
                writer.print("<script>" +
                        "alert('账单列表删除成功');" +
                        "window.location='/BillServlet.do?action=limit&pageIndex=1&pageSize=5'" +
                        "</script>");
            }else {
                writer.print("<script>" +
                        "alert('账单列表删除失败');location.href = '/BillServlet.do?action=limit&pageIndex=1&pageSize=5'" +
                        "</script>");
            }
 

        }
        //获取下拉框的数据
        if (value.equals("loadSupplier")){
            //前端发送请求过来
            //System.out.println("前端发送了下拉框请求");
            //查询供应商
            //这是一个Java对象
            List<Supplier> suppliersList = supplierService.selectAll(null);
            //通过fastJson转换字符串
            String jsonString = JSONObject.toJSONString(suppliersList);
            //System.out.println(jsonString);
            PrintWriter writer = resp.getWriter();
            //响应浏览器一段JSON数据
            writer.print(jsonString);
        }
    }
}
