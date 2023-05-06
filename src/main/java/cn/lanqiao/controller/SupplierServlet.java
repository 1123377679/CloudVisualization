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
        if (value.equals("exportException")){
            String name = req.getParameter("name");
            //System.out.println("要进行Excel导出操作...查询"+name);
            //获取数据库中的数据
            List<Bill> bills = billService.selectAll();
            //1.创建工作簿
            HSSFWorkbook workbook = new HSSFWorkbook();
            //2.创建工作表
            HSSFSheet sheet = workbook.createSheet("供应商信息表");
            sheet.setDefaultColumnWidth(20);//设置所有列的列宽
            //3.首行合并
            CellRangeAddress region1 = new CellRangeAddress(0, 0, 0, 6);
            CellRangeAddress region2 = new CellRangeAddress(1, 1, 0, 6);
            sheet.addMergedRegion(region1);
            sheet.addMergedRegion(region2);

            //的sheet上创建行
            int rownum = 0;
            HSSFRow row01 = sheet.createRow(rownum);
            //在row01上创建单元格
            HSSFCell cell_row01 = row01.createCell(0);
            //向cell_row01写东西
            cell_row01.setCellValue("供应商数据");
            //设置标题样式
            HSSFCellStyle titleStyle = ExprotCellStyle.createTitleCellStyle(workbook);
            cell_row01.setCellStyle(titleStyle);

            //第二行
            rownum++;
            HSSFRow row02 = sheet.createRow(rownum);
            HSSFCell cell_row02 = row02.createCell(0);
            cell_row02.setCellValue("总数:" + bills.size() + "，导出时间:" + DateUtils.nowTime());
            //设置小标题样式
            HSSFCellStyle secondTitleStyle = ExprotCellStyle.createSecondTitleStyle(workbook);
            cell_row02.setCellStyle(secondTitleStyle);

            //第三行
            rownum++;
            HSSFRow row03 = sheet.createRow(rownum);
            String[] titles = {"ID", "商品名称", "商品单位", "商品数量", "总金额", "供应商", "是否付款"};
            //得到表头的样式
            HSSFCellStyle tableTitleStyle = ExprotCellStyle.createTableTitleStyle(workbook);
            for (int i = 0; i < titles.length; i++) {
                HSSFCell cell = row03.createCell(i);
                cell.setCellValue(titles[i]);
                cell.setCellStyle(tableTitleStyle);
            }

            //表格主题样式
            HSSFCellStyle tableBodyStyle = ExprotCellStyle.setRowCellCenter(workbook);
            for (int i = 0; i < bills.size(); i++) {
                rownum++;

                HSSFRow row = sheet.createRow(rownum);
                Bill p = bills.get(i);

                //创建idcell
                HSSFCell idCell = row.createCell(0);
                idCell.setCellValue(p.getId());
                idCell.setCellStyle(tableBodyStyle);

                //创建namecell
                HSSFCell nameCell = row.createCell(1);
                nameCell.setCellValue(p.getTitle());
                nameCell.setCellStyle(tableBodyStyle);

                //创建linkmaneCell
                HSSFCell linkmaneCell = row.createCell(2);
                linkmaneCell.setCellValue(p.getUnit());
                linkmaneCell.setCellStyle(tableBodyStyle);

                //创建phoneCell
                HSSFCell phoneCell = row.createCell(3);
                phoneCell.setCellValue(p.getNum());
                phoneCell.setCellStyle(tableBodyStyle);

                //创建addressCell
                HSSFCell addressCell = row.createCell(4);
                addressCell.setCellValue(p.getMoney());
                addressCell.setCellStyle(tableBodyStyle);

                //创建faxCell
                HSSFCell faxCell = row.createCell(5);
                Integer providerid = p.getProviderid();
                Supplier byId = supplierService.findById(String.valueOf(providerid));
                faxCell.setCellValue(byId.getName());
                faxCell.setCellStyle(tableBodyStyle);

                //创建descriptionCell
                HSSFCell descriptionCell = row.createCell(6);
                if (p.getIspay() == 1){
                    descriptionCell.setCellValue("已付款");
                    descriptionCell.setCellStyle(tableBodyStyle);
                }else if (p.getIspay() == 0){
                    descriptionCell.setCellValue("未付款");
                    descriptionCell.setCellStyle(tableBodyStyle);
                }

            }

            //3.设置content-disposition响应头控制浏览器以下载的形式打开文件
            //下载中文文件时，需要注意的地方就是中文文件名要使用
            String fileName = "供应商数据信息表.xls";
            // URLEncoder.encode方法进行编码(URLEncoder.encode(fileName, "字符编码"))，否则会出现文件名乱码。
            resp.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

            //输出字节流生成的Excel文件作为浏览器的一个输出流--进行输出
            ServletOutputStream outputStream = resp.getOutputStream();
            //把Workbook作为浏览响应字节流进行附件下载
            workbook.write(outputStream);
            System.out.println("数据导出完毕");
            //关闭流
            outputStream.close();

        }
    }

}
