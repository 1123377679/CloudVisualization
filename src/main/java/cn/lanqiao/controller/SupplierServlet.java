package cn.lanqiao.controller;

import cn.lanqiao.pojo.Supplier;
import cn.lanqiao.pojo.JsonResult;
import cn.lanqiao.service.BillService;
import cn.lanqiao.service.MemberService;
import cn.lanqiao.service.SupplierService;
import cn.lanqiao.service.impl.BillServiceImpl;
import cn.lanqiao.service.impl.MemberServiceImpl;
import cn.lanqiao.service.impl.SupplierServiceImpl;
import cn.lanqiao.utils.DateUtils;
import cn.lanqiao.utils.ImportExcelUtils;
import cn.lanqiao.utils.ExprotCellStyle;
import cn.lanqiao.utils.PageUtils;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
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
import java.util.Map;

@MultipartConfig //标注当前servlet支持文件上传
@WebServlet("/SupplierServlet.do")
public class SupplierServlet extends HttpServlet {
    SupplierService supplierService=  new SupplierServiceImpl();
    MemberService memberService=new MemberServiceImpl();
    BillService billService = new BillServiceImpl();

    public SupplierServlet() {
    }

    public SupplierServlet(SupplierService supplierService, MemberService memberService) {
        this.supplierService = supplierService;
        this.memberService = memberService;
    }

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
            String name =req.getParameter("username");
//            System.out.println(name);
            //总条数
            int totalCount =supplierService.getTotalCount();
//            System.out.println("总条数："+totalCount);
            //每页的数
            List<Supplier> depatrs = supplierService.getDepatrs(name,(Integer.parseInt(pageIndex) - 1)*(Integer.parseInt(pageSize)),Integer.parseInt(pageSize));
//            System.out.println("每页查询的数据"+depatrs);
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
//            System.out.println(supplier);
            //存入数据库
            int i=supplierService.addSuppliers(supplier);
            PrintWriter writer = resp.getWriter();
            if (i > 0) {
                writer.print("<script>" +
                        "alert('用户添加成功');" +
                        "window.parent.location='/SupplierServlet.do?action=update&id=${id}" +
                        "</script>");
            } else {
                writer.print("<script>" +
                        "alert('用户添加失败');" +
                        "location.href='/supplier-add.jsp';" +
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
            if (i > 0) {
                writer.print("<script>" +
                        "alert('用户修改成功');" +
                        "window.parent.location='/SupplierServlet.do?action=limit&pageIndex=1&pageSize=5';" +
                        "</script>");
            } else {
                writer.print("<script>" +
                        "alert('用户修改失败');" +
                        "location.href='/supplier-edit.jsp';" +
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
        //检查用户名是否重复
        if (value.equals("checkSupplierName")){
            //前端通过Ajax携带过来的值
            String suppliername = req.getParameter("suppliername");
            String suppliername1= "^[\\u4E00-\\u9FA5]{1,8}$";
            //存储用户输入的名称
            int i =supplierService.checkName(suppliername);
            //判断
            PrintWriter writer = resp.getWriter();
            if (suppliername.matches(suppliername1)){
                if (i>0){
                    writer.print(1);
                }else {
                    writer.print(0);
                }
            }else {
                writer.print(2);
            }
        }
        //校验用户的电话
        if (value.equals("checkSupplierPhone")){
            //前端通过Ajax携带过来的值
            String supplierphone = req.getParameter("supplierphone");
            String supplierphone1= "1[0-9]{10}";
            //存储用户输入的名称
            //判断
            PrintWriter writer = resp.getWriter();
            if (supplierphone.matches(supplierphone1)){
                if (supplierphone.matches(supplierphone1)){
                    writer.print(1);
                }
            }else {
                writer.print(0);
            }
        }
        //校验联系人
        if (value.equals("checkSupplierLinkman")){
            //前端通过Ajax携带过来的值
            String supplierlinkman = req.getParameter("supplierlinkman");
            String supplierlinkman1= "^[\\u4E00-\\u9FA5]{1,8}$";
            //存储用户输入的名称
            //判断
            PrintWriter writer = resp.getWriter();
            if (supplierlinkman.matches(supplierlinkman1)){
                if (supplierlinkman.matches(supplierlinkman1)){
                    writer.print(0);
                }else {
                    writer.print(0);
                }
            }else {
                writer.print(2);
            }
        }
        if (value.equals("exportException")){
            //System.out.println("要进行Excel导出操作...查询"+name);
            //获取数据库中的数据
            List<Supplier> suppliers = supplierService.selectAllSupplier();
            //1.创建工作簿
            HSSFWorkbook workbook = new HSSFWorkbook();
            //2.创建工作表
            HSSFSheet sheet = workbook.createSheet("供应商信息表");
            sheet.setDefaultColumnWidth(20);//设置所有列的列宽
            //3.首行合并
            CellRangeAddress region1 = new CellRangeAddress(0, 0, 0,6);
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
            cell_row02.setCellValue("总数:" + suppliers.size() + "，导出时间:" + DateUtils.nowTime());
            //设置小标题样式
            HSSFCellStyle secondTitleStyle = ExprotCellStyle.createSecondTitleStyle(workbook);
            cell_row02.setCellStyle(secondTitleStyle);

            //第三行
            rownum++;
            HSSFRow row03 = sheet.createRow(rownum);
            String[] titles = {"供应商ID", "供应商名称", "供应商联系人", "供应商电话", "供应商地址", "供应商传真", "供应商描述"};
            //得到表头的样式
            HSSFCellStyle tableTitleStyle = ExprotCellStyle.createTableTitleStyle(workbook);
            for (int i = 0; i < titles.length; i++) {
                HSSFCell cell = row03.createCell(i);
                cell.setCellValue(titles[i]);
                cell.setCellStyle(tableTitleStyle);
            }

            //表格主题样式
            HSSFCellStyle tableBodyStyle = ExprotCellStyle.setRowCellCenter(workbook);
            for (int i = 0; i < suppliers.size(); i++) {
                rownum++;

                HSSFRow row = sheet.createRow(rownum);
                Supplier p = suppliers.get(i);

                //创建idcell
                HSSFCell idCell = row.createCell(0);
                idCell.setCellValue(p.getId());
                idCell.setCellStyle(tableBodyStyle);

                //创建namecell
                HSSFCell nameCell = row.createCell(1);
                nameCell.setCellValue(p.getName());
                nameCell.setCellStyle(tableBodyStyle);

                //创建linkmaneCell
                HSSFCell linkmaneCell = row.createCell(2);
                linkmaneCell.setCellValue(p.getLinkman());
                linkmaneCell.setCellStyle(tableBodyStyle);

                //创建phoneCell
                HSSFCell phoneCell = row.createCell(3);
                phoneCell.setCellValue(p.getPhone());
                phoneCell.setCellStyle(tableBodyStyle);

                //创建addressCell
                HSSFCell addressCell = row.createCell(4);
                addressCell.setCellValue(p.getAddress());
                addressCell.setCellStyle(tableBodyStyle);

                //创建faxCell
                HSSFCell faxCell = row.createCell(5);
                faxCell.setCellValue(p.getFax());
                faxCell.setCellStyle(tableBodyStyle);

                //创建descriptionCell
                HSSFCell descriptionCell = row.createCell(6);
                descriptionCell.setCellValue(p.getDescription());
                descriptionCell.setCellStyle(tableBodyStyle);
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
            outputStream.close();}
        if (value.equals("downloadExcelModel")){
            //1.获取要下载的文件的绝对路径
            //在resources目录放入QQ.png，注意项目导出后resource中的文件被打包到/WEB-INF/classes下,服务器的真实路径
            String realPath = this.getServletContext().getRealPath("/WEB-INF/classes/供应商数据文件模板.xls");
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
        //实现文件上传到数据库
        if (value.equals("excelImport")){
            extracted(req, resp);
        }
        //批量删除
        if (value.equals("delAll")){
            String checkId = req.getParameter("id");
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
                    supplierService.deleteById(id);
                }
                writer.print("<script>"+
                        "alert('日志批量删除成功');"+
                        "window.location.href = '/SupplierServlet.do?action=limit&pageIndex=1&pageSize=5'"+
                        "</script>");
            }catch (Exception e){
                writer.print("<script>"+
                        "alert('日志删除失败');"+
                        "window.location.href = '/SupplierServlet.do?action=limit&pageIndex=1&pageSize=5'"+
                        "</script>");
            }
        }

        //供应商柱状图
        if (value.equals("supplierEchars")){
//             System.out.println("前端发送请求过来了");
            Map<String, Integer> billCountBySupplier = supplierService.getBillCountBySupplier();
            //规定用JSON两个流
            JSONObject jsonObject;
            JSONArray jsonArray = new JSONArray();
            for(Map.Entry<String, Integer> entry : billCountBySupplier.entrySet()){
                jsonObject = new JSONObject();
                //获取供应商的名字
                String name = entry.getKey();
                //获取账单数量
                Integer billCount = entry.getValue();
                //通过JSONObject存数据
                jsonObject.put("name",name);
                jsonObject.put("billCount",billCount);
                //通过JSONArray发送数据到前端页面
                jsonArray.add(jsonObject);
            }
            //将JSONArray这个流存储到页面上去，转成JSON格式
            String s = JSON.toJSONString(jsonArray);
            //发送一个编码格式
            resp.setContentType("text/html;charset=UTF-8");
            //try...catch
            try(PrintWriter writer = resp.getWriter()){
                writer.print(s);
                writer.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //饼图
        if (value.equals("userEchars")){
//            System.out.println("前端发送请求过来了");
            List<Integer> list = memberService.selectAge();
            JSONObject jsonObject;
            JSONArray jsonArray=new JSONArray();
         //转成JSON格式，把他发送到前端
            for (Integer age : list) {
                jsonObject = new JSONObject();
                jsonObject.put("age", age);
                jsonArray.add(jsonObject);
            }
            String s = JSON.toJSONString(jsonArray);
            //编码格式
            resp.setContentType("text/html;charset=UTF-8");
            try(PrintWriter writer = resp.getWriter()){
                writer.print(s);
                writer.flush();
            }catch (Exception e){
                e.printStackTrace();
            }
    }

        //饼图-地区分布
        if (value.equals("supplierAera")){
//            System.out.println("前端发送请求过来了");
            Map<String, Integer> getsupplierarea = billService.getsupplierarea();
            //规定用JSON
            JSONObject jsonObject;
            JSONArray jsonArray = new JSONArray();

            //转成JSON格式，把他发送到前端
            for(Map.Entry<String, Integer> entry : getsupplierarea.entrySet()){
                jsonObject = new JSONObject();
                //获取供应商的名字
                String supplierArea = entry.getKey();
                //获取账单数量
                Integer supplierCount = entry.getValue();
                //存数据
                jsonObject.put("supplierArea", supplierArea);
                jsonObject.put("supplierCount",supplierCount);
                //通过jsonArray发送数据到前端页面
                jsonArray.add(jsonObject);
            }
            //将jsonArray这个流存储到页面上
            String s = JSON.toJSONString(jsonArray);
            //发送一个编码格式
            resp.setContentType("text/html;charset=UTF-8");
            try(PrintWriter writer = resp.getWriter()){
                writer.print(s);
                writer.flush();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    private void extracted(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //执行excel文件导入操作
        List<Supplier> supplierList = new ArrayList<>();
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
                    colNum = 1;
//                        User user = new User();
                    Supplier supplier = new  Supplier();
                    //验证Excel文件字段，这里根据表格内名称
                    ImportExcelUtils.validCellValue(sheet,row,colNum,"供应商");
                    supplier.setName(ImportExcelUtils.getCellValue(sheet,row,colNum-1));

                    //列号需要自加获取
                    ImportExcelUtils.validCellValue(sheet,row,++colNum,"联系人");
                    supplier.setLinkman(ImportExcelUtils.getCellValue(sheet,row,colNum-1));

//                        ImportExcelUtils.validCellValue(sheet,row,++colNum,"");
//                        user.setSex("男".equals(ImportExcelUtils.getCellValue(sheet,row,colNum-1))?1:0);

                    ImportExcelUtils.validCellValue(sheet,row,++colNum,"联系电话");
                    supplier.setPhone(ImportExcelUtils.getCellValue(sheet,row,colNum-1));

                    ImportExcelUtils.validCellValue(sheet,row,++colNum,"地址");
                    supplier.setAddress(ImportExcelUtils.getCellValue(sheet,row,colNum-1));

                    ImportExcelUtils.validCellValue(sheet,row,++colNum,"传真");
                    supplier.setFax(ImportExcelUtils.getCellValue(sheet,row,colNum-1));

                    ImportExcelUtils.validCellValue(sheet,row,++colNum,"描述");
                    supplier.setDescription(ImportExcelUtils.getCellValue(sheet,row,colNum-1));

//                        ImportExcelUtils.validCellValue(sheet,row,++colNum,"描述");
//                        String cellValue = ImportExcelUtils.getCellValue(sheet, row, colNum - 1);
//                        if ("管理员".equals(cellValue)){
//                            user.setType(1);
//                        }else if ("经理".equals(cellValue)){
//                            user.setType(2);
//                        }else {
//                            user.setType(3);
//                        }
                    //存储对象到list集合中
                    supplierList.add(supplier);
                }
                System.out.println("===================导入的数据是=================");
                for (Supplier supplier: supplierList) {
                    System.out.println(supplier);
                    //对象添加到数据库
                    supplierService.addSuppliers(supplier);
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


    /**
     * 获取
     * @return supplierService
     */
    public SupplierService getSupplierService() {
        return supplierService;
    }

    /**
     * 设置
     * @param supplierService
     */
    public void setSupplierService(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    /**
     * 获取
     * @return memberService
     */
    public MemberService getMemberService() {
        return memberService;
    }

    /**
     * 设置
     * @param memberService
     */
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    public String toString() {
        return "SupplierServlet{supplierService = " + supplierService + ", memberService = " + memberService + "}";
    }
}
