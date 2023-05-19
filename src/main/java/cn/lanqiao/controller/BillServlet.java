package cn.lanqiao.controller;


import cn.lanqiao.pojo.Bill;


import cn.lanqiao.pojo.JsonResult;
import cn.lanqiao.pojo.Supplier;
import cn.lanqiao.service.BillService;
import cn.lanqiao.service.SupplierService;
import cn.lanqiao.service.impl.BillServiceImpl;
import cn.lanqiao.service.impl.SupplierServiceImpl;
import cn.lanqiao.utils.DateUtils;
import cn.lanqiao.utils.ExprotCellStyle;
import cn.lanqiao.utils.ImportExcelUtils;
import cn.lanqiao.utils.PageUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@MultipartConfig //标注当前servlet支持文件上传
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
//            System.out.println("总条数：" + totalCount);
            //供应商数据回显
            List<Supplier> suppliersList = supplierService.selectAllSupplier();
            //供应商的集合存储转发
            req.setAttribute("suppliersList",suppliersList);//
            //每页的数据
            //起始索引从0开始，起始索引=（查询页码-1）*每页显示记录数
            List<Bill> depatrs = billService.getDepatrs(name,providerid,ispay, (Integer.parseInt(pageIndex) - 1 )* (Integer.parseInt(pageSize)), Integer.parseInt(pageSize));
//            System.out.println("每页查询的数据："+depatrs);//值能查询到了
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
//            System.out.println(bill);
            //保存到数据库中
            int i =  billService.add(bill);
            //后端往前端写js代码
            PrintWriter writer = resp.getWriter();
            if (i>0){
                writer.print("<script>" +
                        "alert('账单新增成功');" +
                        "window.parent.location='/BillServlet.do?action=limit&pageIndex=1&pageSize=5'" +
                        "</script>");
            }else {
                writer.print("<script>" +
                        "alert('账单新增失败');location.href = '/bill-add.jsp'" +
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
            List<Supplier> suppliersList = supplierService.selectAllSupplier();

            //供应商的集合存储转发
            req.setAttribute("suppliersList",suppliersList);//

//            System.out.println(bliiById);
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
//            System.out.println(id);
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
            List<Supplier> suppliersList = supplierService.selectAllSupplier();
            //通过fastJson转换字符串
            String jsonString = JSONObject.toJSONString(suppliersList);
            //System.out.println(jsonString);
            PrintWriter writer = resp.getWriter();
            //响应浏览器一段JSON数据
            writer.print(jsonString);
        }
        //导出数据
        if (value.equals("exportException")){
            String name = req.getParameter("name");
            System.out.println("要进行Excel导出操作...查询"+name);
            //获取数据库中的数据
            List<Bill> bills = billService.selectAll();
            //1.创建工作簿
            HSSFWorkbook workbook = new HSSFWorkbook();
            //2.创建工作表
            HSSFSheet sheet = workbook.createSheet("账单管理信息表");
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
            cell_row01.setCellValue("账单管理数据");
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
            String fileName = "账单管理数据信息表.xls";
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
        //下载模板
        if(value.equals("downloadExcelModel")){
            //1.获取要下载的文件的绝对路径
            //在resources目录放入QQ.png，注意项目导出后resource中的文件被打包到/WEB-INF/classes下,服务器的真实路径
            String realPath = this.getServletContext().getRealPath("/WEB-INF/classes/账单管理数据文件模板.xls");
            //2.获取要下载的文件名
            String fileName = realPath.substring(realPath.lastIndexOf("\\") + 1);
            System.out.println("要下载的文件名:" + fileName);
            //字节输入流
            FileInputStream inputStream = new FileInputStream(realPath);
            //浏览器输出流
            ServletOutputStream outputStream = resp.getOutputStream();

            //3.设置content-disposition响应头控制浏览器以下载的形式打开文件
            // URLEncoder.encode方法进行编码(URLEncoder.encode(fileName, "字符编码"))，否则会出现文件名乱码。
            resp.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

            //5.准备数据缓冲区
            int len = 0;
            byte[] buffer = new byte[1024]; // 1KB
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.close();
            inputStream.close();

        }


        //上传文件
        if (value.equals("excelImport")){
            extracted(req, resp);
        }

        //批量删除
        if (value.equals("delAll")){
            String checkId = req.getParameter("checkId");
//            System.out.println(checkId);
            String[] split = checkId.split(",");
//            int[] ints = new int[split.length];
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
                    billService.deleteById(id);
                }
                writer.print("<script>"+
                        "alert('账单批量删除成功');"+
                        "window.location.href = '/BillServlet.do?action=limit&pageIndex=1&pageSize=20'"+
                        "</script>");
            }catch (Exception e){
                writer.print("<script>"+
                        "alert('账单删除失败');"+
                        "window.location.href = '/BillServlet.do?action=limit&pageIndex=1&pageSize=20'"+
                        "</script>");
            }
        }



        //判断不能英文且不能超过8个字符！
        if (value.equals("checkBillName")){
            //前端通过Ajax携带过来的值
            String billName = req.getParameter("billName");
            //定义中文正则表达式
            String regex = "^[\u4e00-\u9fa5]{0,8}$";
            //编译正则表达式
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(billName);
            //判断输入是否为空或者超过8个中文字符
            if (billName == null || !matcher.matches()) {
                //如果不符合规定则返回错误信息，这里返回1
                resp.getWriter().write("1");
            } else {
                //如果符合规定则通过判断输入的供应商名称是否已存在来返回相应的结果
                resp.getWriter().write("0");
            }
        }

        //判断不能英文且不能超过1个字符！
        if (value.equals("checkBillPhone")){
            //前端通过Ajax携带过来的值
            String billPhone = req.getParameter("billPhone");
            //定义中文正则表达式
            String regexPhone = "^[\u4e00-\u9fa5]{1}$";
            //编译正则表达式
            Pattern pattern = Pattern.compile(regexPhone);
            Matcher matcher = pattern.matcher(billPhone);
            //判断输入是否为空或者超过2个中文字符
            if (billPhone == null || !matcher.matches()) {
                //如果不符合规定则返回错误信息，这里返回1
                resp.getWriter().write("1");
            } else {
                //如果符合规定则通过判断输入的供应商名称是否已存在来返回相应的结果
                resp.getWriter().write("0");
            }
        }

        //判断必须是数字并且为整数！
        if (value.equals("checkBillNum")){
            //前端通过Ajax携带过来的值
            String billAddress = req.getParameter("billAddress");
            //定义中文正则表达式
            String regexAddress = "^[1-9]\\d*$";
            //编译正则表达式
            Pattern pattern = Pattern.compile(regexAddress);
            Matcher matcher = pattern.matcher(billAddress);
            //判断输入是否为空或者不是一个整数或者是负数
            if (billAddress == null || !matcher.matches() || Integer.parseInt(billAddress) < 0) {
                //如果不符合规定则返回错误信息，这里返回1
                resp.getWriter().write("1");
            } else {
                //如果符合规定则通过判断输入的供应商名称是否已存在来返回相应的结果
                resp.getWriter().write("0");
            }
        }

        //判断必须是数字并且为整数！
        if (value.equals("checkBillFax")){
            //前端通过Ajax携带过来的值
            String billFax = req.getParameter("billFax");
            //定义中文正则表达式
            String regexFax  = "^([1-9]\\d*\\.?\\d*)|(0\\.\\d*[1-9])$";
            //编译正则表达式
            Pattern pattern = Pattern.compile(regexFax);
            Matcher matcher = pattern.matcher(billFax);
            //判断输入是否为空或者不是一个正数
            if (billFax == null || !matcher.matches() || Integer.parseInt(billFax) < 0) {
                //如果不符合规定则返回错误信息，这里返回1
                resp.getWriter().write("1");
            } else {
                //如果符合规定则通过判断输入的供应商名称是否已存在来返回相应的结果
                resp.getWriter().write("0");
            }
        }

    }
    private void extracted(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //执行excel文件导入操作
        List<Bill> billList = new ArrayList<>();
        //取上传的文件数量
        Collection<Part> parts = req.getParts();
        PrintWriter writer = resp.getWriter();
        System.out.println("文件上传的个数:"+parts.size());
        try {
            if (parts.size()>0){
                Part part = req.getPart("file");
                //取文件名
                String submittedFileName = part.getSubmittedFileName();
                System.out.println("上传的文件名:"+submittedFileName);
                //上传的文件对应输入流
                InputStream inputStream = part.getInputStream();
                //读取excel中的数据
                Workbook workbook = ImportExcelUtils.getWorkbookByInputStream(inputStream, submittedFileName);
                //得到工作表
                Sheet sheet = ImportExcelUtils.getSheetByWorkbook(workbook,0);
                //根据需要是否加条数限制
                if (sheet.getRow(1000) != null){
                    throw new RuntimeException("系统已限制单批次导入必须小于或等于1000笔!");
                }
                int rowNum = 0;//已取值的行数
                int colNum = 0;//列号
                //获取数据行数
                int realRowCount = sheet.getPhysicalNumberOfRows();
                //处理表格数据
                for (Row row : sheet){
                    if (realRowCount == rowNum){
                        //excel文件所有的行读取完毕，结束循环
                        break;
                    }
                    if (ImportExcelUtils.isBlankRow(row)){//空行跳过
                        continue;
                    }
                    if (row.getRowNum() == -1){
                        continue;
                    }else {
                        if (row.getRowNum() == 0 || row.getRowNum() == 1){//第一行，第二行表头跳过
                            continue;
                        }
                    }
                    rowNum++;
                    colNum = 0;
                    Bill bill = new Bill();

                    //列号需要自加获取
                    ImportExcelUtils.validCellValue(sheet,row,++colNum,"商品名称");
                    bill.setTitle(ImportExcelUtils.getCellValue(sheet,row,colNum-1));

                    ImportExcelUtils.validCellValue(sheet,row,++colNum,"商品单位");
                    bill.setUnit(ImportExcelUtils.getCellValue(sheet,row,colNum-1));

                    ImportExcelUtils.validCellValue(sheet,row,++colNum,"商品数量");
                    String num = ImportExcelUtils.getCellValue(sheet, row, colNum-1);
                    bill.setNum(Integer.parseInt(num));

                    ImportExcelUtils.validCellValue(sheet,row,++colNum,"总金额");
                    String money = ImportExcelUtils.getCellValue(sheet, row, colNum-1);
                    bill.setMoney(Integer.parseInt(money));

                    ImportExcelUtils.validCellValue(sheet,row,++colNum,"供应商");
                    //发送进来的supplierName值一定是供应商的名字
                    String supplierName = ImportExcelUtils.getCellValue(sheet, row, colNum-1);
                    //拿着供应商的名字去获取账单id，然后把他存入到setProviderid中
                    // bill.setProviderid(Integer.parseInt(providerid));
                    int getbillsum = billService.getbillsum(supplierName);
                    bill.setProviderid(getbillsum);

                    ImportExcelUtils.validCellValue(sheet,row,++colNum,"是否付款");
                    String ispay = ImportExcelUtils.getCellValue(sheet, row, colNum-1);
                    if ("未付款".equals(ispay)){
                        bill.setIspay(0);
                    } else if ("已付款".equals(ispay)){
                        bill.setIspay(1);
                    }
                    //存储对象到list集合中
                    billList.add(bill);
                }
                System.out.println("===================导入的数据是=================");
                for (Bill bill: billList) {
                    System.out.println(bill);
                    //对象添加到数据库
                    billService.add(bill);
                }
                //到这里添加数据成功
                JsonResult jsonResult = new JsonResult<>("200","导入数据成功");
                String jsonString = JSONObject.toJSONString(jsonResult);
                writer.print(jsonString);
            }
        }catch (Exception e){
            e.printStackTrace();
            JsonResult jsonResult = new JsonResult<>("500","导入数据失败");
            String jsonString = JSONObject.toJSONString(jsonResult);
            writer.print(jsonString);
        }
    }



}
