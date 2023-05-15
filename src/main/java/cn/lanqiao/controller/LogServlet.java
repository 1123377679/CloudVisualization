package cn.lanqiao.controller;

import cn.hutool.core.date.DateUtil;
import cn.lanqiao.pojo.BehaviorLog;
import cn.lanqiao.pojo.JsonResult;
import cn.lanqiao.pojo.LoginLog;
import cn.lanqiao.service.LogService;
import cn.lanqiao.service.impl.LogServiceImpl;
import cn.lanqiao.utils.ExprotCellStyle;
import cn.lanqiao.utils.ImportExcelUtils;
import cn.lanqiao.utils.PageUtils;
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

@MultipartConfig //标注当前servlet支持文件上传
@WebServlet("/LogServlet.do")
public class LogServlet extends HttpServlet {
    LogService logService = new LogServiceImpl();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //处理请求和响应乱码
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        //获取前端的请求
        String value = req.getParameter("action");
        //行为日志分页
        if (value.equals("Behaviors")){
            //前端发送的当前页
            String pageIndex = req.getParameter("pageIndex");
            //前端发送的每页显示条数
            String pageSize = req.getParameter("pageSize");
            //获取输入框信息
            String name = req.getParameter("username");
            //总条数
            int totalCount = logService.getBehaviorTotalCount();
            //每页的数据
            List<BehaviorLog> depatrs = logService.getBehaviorDepatrs(name,(Integer.parseInt(pageIndex) - 1) * (Integer.parseInt(pageSize)), Integer.parseInt(pageSize));
            //存到工具类中
            PageUtils pageUtils = new PageUtils<>(Integer.parseInt(pageIndex),Integer.parseInt(pageSize),totalCount,depatrs);
            //存储
            req.setAttribute("pageUtils",pageUtils);
            req.setAttribute("depatrs",depatrs);
            req.getRequestDispatcher("/behavior-log.jsp").forward(req,resp);
        }
        //登录分页
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

        //导出数据
        if (value.equals("exportExcel")){
//            //计算导出功能运行时间
//            //开始时间
//            long Start = System.currentTimeMillis();
//            String name = req.getParameter("name");
            //System.out.println("要进行Excel导出操作...查询"+name);
            //获取数据库中的数据
            List<LoginLog> loginLogs = logService.queryMyLogs();
//            List<Supplier> suppliers = supplierService.selectAll(name);
            //1.创建工作簿
            HSSFWorkbook workbook = new HSSFWorkbook();
            //2.创建工作表
            HSSFSheet sheet = workbook.createSheet("登陆日志信息表");
            sheet.setDefaultColumnWidth(20);//设置所有列的列宽
            //3.首行合并
            CellRangeAddress region1 = new CellRangeAddress(0, 0, 0, 5);
            CellRangeAddress region2 = new CellRangeAddress(1, 1, 0, 5);
            sheet.addMergedRegion(region1);
            sheet.addMergedRegion(region2);

            //的sheet上创建行
            int rownum = 0;
            HSSFRow row01 = sheet.createRow(rownum);
            //在row01上创建单元格
            HSSFCell cell_row01 = row01.createCell(0);
            //向cell_row01写东西
            cell_row01.setCellValue("登录日志");
            //设置标题样式
            HSSFCellStyle titleStyle = ExprotCellStyle.createTitleCellStyle(workbook);
            cell_row01.setCellStyle(titleStyle);

            //第二行
            rownum++;
            HSSFRow row02 = sheet.createRow(rownum);
            HSSFCell cell_row02 = row02.createCell(0);
            cell_row02.setCellValue("总数:" + loginLogs.size() + "，导出时间:" + DateUtil.date());
            //设置小标题样式
            HSSFCellStyle secondTitleStyle = ExprotCellStyle.createSecondTitleStyle(workbook);
            cell_row02.setCellStyle(secondTitleStyle);

            //第三行
            rownum++;
            HSSFRow row03 = sheet.createRow(rownum);
            String[] titles = {"ID", "用户名", "登陆城市", "登录Ip", "登录时间", "状态"};

            //得到表头的样式
            HSSFCellStyle tableTitleStyle = ExprotCellStyle.createTableTitleStyle(workbook);
            for (int i = 0; i < titles.length; i++) {
                HSSFCell cell = row03.createCell(i);
                cell.setCellValue(titles[i]);
                cell.setCellStyle(tableTitleStyle);
            }

            //表格主题样式
            HSSFCellStyle tableBodyStyle = ExprotCellStyle.setRowCellCenter(workbook);
            for (int i = 0; i < loginLogs.size(); i++) {
                rownum++;
                HSSFRow row = sheet.createRow(rownum);
                LoginLog p = loginLogs.get(i);

                //创建Idcell
                HSSFCell idCell = row.createCell(0);
                idCell.setCellValue(p.getId());
                idCell.setCellStyle(tableBodyStyle);

                //创建usernamecell
                HSSFCell nameCell = row.createCell(1);
                nameCell.setCellValue(p.getUsername());
                nameCell.setCellStyle(tableBodyStyle);

                //创建AddressCell
                HSSFCell linkmaneCell = row.createCell(2);
                linkmaneCell.setCellValue(p.getAddress());
                linkmaneCell.setCellStyle(tableBodyStyle);

                //创建IpCell
                HSSFCell phoneCell = row.createCell(3);
                phoneCell.setCellValue(p.getIp());
                phoneCell.setCellStyle(tableBodyStyle);

                //创建LogintimeCell
                HSSFCell addressCell = row.createCell(4);
                addressCell.setCellValue(p.getLogintime());
                addressCell.setCellStyle(tableBodyStyle);

                //创建faxCell
//                HSSFCell faxCell = row.createCell(5);
//                faxCell.setCellValue(p.getFax());
//                faxCell.setCellStyle(tableBodyStyle);

                //创建IsdeleteCell
                HSSFCell descriptionCell = row.createCell(5);
                descriptionCell.setCellValue(p.getIsdelete());
                descriptionCell.setCellStyle(tableBodyStyle);
            }

            //3.设置content-disposition响应头控制浏览器以下载的形式打开文件
            //下载中文文件时，需要注意的地方就是中文文件名要使用
            String fileName = "登陆日志信息表.xls";
            // URLEncoder.encode方法进行编码(URLEncoder.encode(fileName, "字符编码"))，否则会出现文件名乱码。
            resp.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

            //输出字节流生成的Excel文件作为浏览器的一个输出流--进行输出
            ServletOutputStream outputStream = resp.getOutputStream();
            //把Workbook作为浏览响应字节流进行附件下载
            workbook.write(outputStream);
            System.out.println("数据导出完毕");
            //关闭流
            outputStream.close();
//            //结束时间
//            long End = System.currentTimeMillis();
//            System.out.printf("执行时长：%d 毫秒",(End-Start));
        }

        //下载模板
        if (value.equals("downloadExcelModel")){
            //1.获取要下载的文件的绝对路径
            //在resources目录放入QQ.png，注意项目导出后resource中的文件被打包到/WEB-INF/classes下,服务器的真实路径
            String realPath = this.getServletContext().getRealPath("/WEB-INF/classes/登陆日志文件模板.xls");
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
        //实现文件上传到数据库
        if (value.equals("excelImport")){
            extracted(req, resp);
        }

        //批量删除
        if (value.equals("delAll")){
            String checkId = req.getParameter("checkId");
            System.out.println(checkId);
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
                    logService.deleteLogs(id);
                }
                writer.print("<script>"+
                        "alert('日志批量删除成功');"+
                        "window.location.href = '/LogServlet.do?action=mylogs&pageIndex=1&pageSize=20'"+
                        "</script>");
            }catch (Exception e){
                writer.print("<script>"+
                        "alert('日志删除失败');"+
                        "window.location.href = '/LogServlet.do?action=mylogs&pageIndex=1&pageSize=20'"+
                        "</script>");
            }
        }
    }
    private void extracted(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //执行excel文件导入操作
        List<LoginLog> logList = new ArrayList<>();
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
                System.out.println(workbook);
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
                    LoginLog log = new LoginLog();
//                        User user = new User();
                    //验证Excel文件字段，这里根据表格内名称
                    ImportExcelUtils.validCellValue(sheet,row,colNum,"用户名称");
                    log.setUsername(ImportExcelUtils.getCellValue(sheet,row,colNum-1));

                    //列号需要自加获取
                    ImportExcelUtils.validCellValue(sheet,row,++colNum,"登陆城市");
                    log.setAddress(ImportExcelUtils.getCellValue(sheet,row,colNum-1));

//                        ImportExcelUtils.validCellValue(sheet,row,++colNum,"性别");
//                        user.setSex("男".equals(ImportExcelUtils.getCellValue(sheet,row,colNum-1))?1:0);

                    ImportExcelUtils.validCellValue(sheet,row,++colNum,"登录Ip");
                    log.setIp(ImportExcelUtils.getCellValue(sheet,row,colNum-1));

                    ImportExcelUtils.validCellValue(sheet,row,++colNum,"登录时间");
                    log.setLogintime(ImportExcelUtils.getCellValue(sheet,row,colNum-1));

//                        ImportExcelUtils.validCellValue(sheet,row,++colNum,"权限");
//                        user.setAddress(ImportExcelUtils.getCellValue(sheet,row,colNum-1));

//                        ImportExcelUtils.validCellValue(sheet,row,++colNum,"类别");
//                        String cellValue = ImportExcelUtils.getCellValue(sheet, row, colNum - 1);
//                        if ("管理员".equals(cellValue)){
//                            user.setType(1);
//                        }else if ("经理".equals(cellValue)){
//                            user.setType(2);
//                        }else {
//                            user.setType(3);
//                        }
                    //存储对象到list集合中
                    logList.add(log);
//                        userList.add(user);
                }
                System.out.println("===================导入的数据是=================");
                for (LoginLog log: logList) {
                    System.out.println(log);
                    //对象添加到数据库
                    logService.addLogs(log);
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
