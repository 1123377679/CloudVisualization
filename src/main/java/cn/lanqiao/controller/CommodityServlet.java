package cn.lanqiao.controller;

import cn.lanqiao.pojo.Commodity;
import cn.lanqiao.service.CommodityService;
import cn.lanqiao.service.impl.CommodityServiceImpl;
import cn.lanqiao.utils.Ean13Validator;
import cn.lanqiao.utils.PageUtils;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/CommodityServlet.do")
public class CommodityServlet extends HttpServlet {
    CommodityService commodityService = new CommodityServiceImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //处理请求和响应乱码
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        //获取前端的请求
        String value = req.getParameter("action");
        //分页
        if (value.equals("limit")){
            //前端发送的当前页
            String pageIndex = req.getParameter("pageIndex");
            //前端发送的每页显示条数
            String pageSize = req.getParameter("pageSize");
            //获取输入框信息
            String name = req.getParameter("name");
            //总条数
            int totalCount = commodityService.getTotalCount();
            //每页的数据
            List<Commodity> depatrs = commodityService.getDepatrs(name,(Integer.parseInt(pageIndex) - 1) * (Integer.parseInt(pageSize)), Integer.parseInt(pageSize));
            //存到工具类中
            PageUtils pageUtils = new PageUtils<>(Integer.parseInt(pageIndex),Integer.parseInt(pageSize),totalCount,depatrs);
            //存储
            req.setAttribute("pageUtils",pageUtils);
            req.setAttribute("depatrs",depatrs);
            req.getRequestDispatcher("/commodity-management.jsp").forward(req,resp);
        }
        //修改上架状态
        if (value.equals("change")){
            //获取前端发送过来的id
            String id = req.getParameter("id");
            int i = commodityService.changeStatus(id);
            PrintWriter writer = resp.getWriter();
            if (i>0){
                writer.print("<script>"+
                        "alert('状态修改成功');"+
                        "window.location.href = '/CommodityServlet.do?action=limit&pageIndex=1&pageSize=5'"+
                        "</script>");
            }else {
                writer.print("<script>"+
                        "alert('状态修改失败');"+
                        "window.location.href = '/CommodityServlet.do?action=limit&pageIndex=1&pageSize=5'"+
                        "</script>");
            }
        }
        if (value.equals("add")){
            String name = req.getParameter("name");
            String barcode = req.getParameter("barcode");
            String price = req.getParameter("price");
            Commodity commodity = new Commodity(null,name,barcode, new BigDecimal(price));
            int i = commodityService.add(commodity);
            PrintWriter writer = resp.getWriter();
            if (i > 0) {
                //添加成功
                writer.println("<script>" +
                        "alert('添加成功');" +
                        "window.parent.location='/CommodityServlet.do?action=limit&pageIndex=1&pageSize=5'"
                        + "</script>");
            } else {
                //添加失败
                writer.println("<script>" +
                        "alert('添加失败');location='/commodity-add.jsp'"
                        + "</script>");
            }
        }
        //商品条码校验
        if (value.equals("checkBarcode")){
            try {
                String barcode = req.getParameter("barcode");
                PrintWriter writer = resp.getWriter();
                List<Commodity> commodities = commodityService.queryCommodity();
                List<String> list = new ArrayList<String>();
                for (Commodity c:commodities) {
                    list.add(c.getBarcode());
                }
                if (list.contains(barcode)){
                    writer.print(2);
                }else {
                    if (new Ean13Validator().validateEan13(barcode)) {
                        writer.print(1);
                    } else {
                        writer.print(0);
                    }
                }
            } catch (Exception e) {
                PrintWriter writer = resp.getWriter();
                writer.print(0);
            }
        }
        //商品名称校验
        if (value.equals("checkName")){
            String name = req.getParameter("name");
            PrintWriter writer = resp.getWriter();
            List<Commodity> commodities = commodityService.queryCommodity();
            List<String> list = new ArrayList<String>();
            for (Commodity c:commodities) {
                list.add(c.getName());
            }
            if (list.contains(name)){
                writer.print(0);
            }else {
                writer.print(1);
            }
        }



        //扫描或添加条码
        if(value.equals("addBill")){
            String val = req.getParameter("val");
            System.out.println(val);
            //查询条码是否存在
            int i1 = commodityService.queryBarcodeExist(val);
            if (i1 == 0){
                resp.getWriter().write("0");
            }else if (i1 >= 1){
                //根据条码查询判断是否存在
                Commodity i = commodityService.queryCommodityByCode(val);
                //判断查询的条码是否存在
                if (i != null) {
                    // 将商品信息封装到 Map 对象中
                    Map<String, Object> resultMap = new HashMap<>();
                    resultMap.put("name", i.getName());
                    resultMap.put("price", i.getPrice());
                    // 其他属性依此类推
                    // 将 Map 对象转换为 JSON 字符串
                    String jsonResult = JSON.toJSONString(resultMap);
                    // 发送响应
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    resp.getWriter().write(jsonResult);
                }
            }
            //查询这个条码的商品名称和商品价格
            //通过JSON的方式发送给前端


        }
    }
}
