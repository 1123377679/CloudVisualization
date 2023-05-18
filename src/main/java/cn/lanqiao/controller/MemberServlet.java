package cn.lanqiao.controller;
import cn.lanqiao.pojo.JsonResult;
import cn.lanqiao.pojo.User;
import cn.lanqiao.service.MemberService;
import cn.lanqiao.service.UserService;
import cn.lanqiao.service.impl.MemberServiceImpl;
import cn.lanqiao.service.impl.UserServiceImpl;
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
import javax.servlet.http.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@WebServlet ("/MemberServlet.do")
@MultipartConfig //标注当前servlet支持文件上传
public class MemberServlet extends HttpServlet {
    MemberService memberService = new MemberServiceImpl();
    UserService userService = new UserServiceImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //处理请求和响应乱码
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        //用来获取前端的请求
        String value = req.getParameter("action");
        HttpSession session = req.getSession();
        //-------------做分页页面查询功能和模糊查询功能--
        if (value.equals("limit")) {
            //前端发送当前页面的请求
            String pageIndex = req.getParameter("pageIndex");
            //前端发送的每页显示条数
            String pageSize = req.getParameter("pageSize");
            //前端发送过来的输入框发送的信息
            String name = req.getParameter("username");
            //总条数
            int totalCount = memberService.getTotalCount();
//            System.out.println("总条数：" + totalCount);
            //每页的数
            List<User> depatrs = memberService.getDepatrs(name, (Integer.parseInt(pageIndex) - 1) * (Integer.parseInt(pageSize)), Integer.parseInt(pageSize));
//            System.out.println("每页查询的数据" + depatrs);
            //存到工具类
            PageUtils pageUtils = new PageUtils<>(Integer.parseInt(pageIndex), Integer.parseInt(pageSize), totalCount, depatrs);
            //将pageUtils存储
            req.setAttribute("pageUtils", pageUtils);
            //将depatrs存储到页面上
            req.setAttribute("depatrs", depatrs);
            //将pageUtils传递到页面
            req.getRequestDispatcher("/member-list.jsp").forward(req, resp);
        }
        //----------------添加用户信息功能----------------------
        if (value.equals("add")) {
            String username = req.getParameter("username");         //用户名
            String oldpassword = req.getParameter("oldpassword");   //用户密码
            String newpassword = req.getParameter("newpassword");   //确认用户密码
            String sex = req.getParameter("sex");               //用户性别
            String birthday = req.getParameter("birthday");     //用户生日
            String userphone = req.getParameter("userphone");           //用户手机号码
            String address = req.getParameter("userAddress");       //用户地址
            String type = req.getParameter("userlei");             //用户类别
            //将值赋值给实体类
            User user = new User(null, username, oldpassword, Integer.parseInt(sex), birthday, userphone, address, Integer.parseInt(type), 0);
            //把值存入数据库
            int i = memberService.addUser(user);
            //后端王前端里面写代码
            PrintWriter writer = resp.getWriter();
            if (i > 0) {
                //添加成功
                writer.println("<script>" +
                        "alert('添加成功');" +
                        "window.parent.location='/MemberServlet.do?action=limit&pageIndex=1&pageSize=5'"
                        + "</script>");
            } else {
                //添加失败
                writer.println("<script>" +
                        "alert('添加失败');location='/member-add.jsp'"
                        + "</script>");
            }
        }
        //---------------跳转修改页面并且回显-------------------
        if (value.equals("goUpdate")) {
            String id = req.getParameter("id");
            //根据id找到实体类对象
            User userById = memberService.getUserById(id);
            //存值，好让前端获取内容
            req.setAttribute("userById", userById);
            //将查询的id信息发送到页面
            req.getRequestDispatcher("/member-edit.jsp").forward(req, resp);
        }
        //---------------修改功能--------------------------
        if (value.equals("update")) {
            String id = req.getParameter("id");
            String username = req.getParameter("userName");         //用户名
            String oldpassword = req.getParameter("oldpassword");   //用户密码
            String newpassword = req.getParameter("newpassword");   //确认用户密码
            String sex = req.getParameter("sex");               //用户性别
            String birthday = req.getParameter("birthday");     //用户生日
            String phone = req.getParameter("userphone");           //用户手机号码
            String address = req.getParameter("userAddress");       //用户地址
            String type = req.getParameter("userlei");             //用户类别
            //将值赋值给实体类
            User user = new User(Integer.parseInt(id), username, oldpassword, Integer.parseInt(sex), birthday, phone, address, Integer.parseInt(type), 0);
            int i = memberService.updateById(user);
            System.out.println(i);
            //后端王前端里面写代码
            PrintWriter writer = resp.getWriter();
            if (i > 0) {
                writer.print("<script>" +
                        "alert('修改成功');" +
                        "window.location='/MemberServlet.do?action=limit&pageIndex=1&pageSize=5'"
                        + "</script>");
            } else {
                writer.print("<script>" +
                        "alert('修改失败');" +
                        "location.href='/member-edit.jsp'"
                        + "</script>");
            }
        }
        //---------------查看详情功能-------------------------..
        if (value.equals("details")) {
            String id = req.getParameter("id");
            User userById = memberService.getUserById(id);
            req.setAttribute("users", userById);
            req.getRequestDispatcher("/member-view.jsp").forward(req, resp);
        }
        //-------------------修改密码跳转页面-------------------
        if (value.equals("goupdatepwd")) {
            String id = req.getParameter("id");
            //根据id找到实体类对象
            User userById = memberService.getUserById(id);
            //存值，好让前端获取内容
            req.setAttribute("userById", userById);
            //将查询的id信息发送到页面
            req.getRequestDispatcher("/member-password.jsp").forward(req, resp);
        }
        //---------------修改密码功能-----------------------
        if (value.equals("updatepwd")) {
            User loginUser = (User) session.getAttribute("loginUser");
            String id = req.getParameter("id");
            String oldpass = req.getParameter("oldpass");   //旧密码
            String newpass = req.getParameter("newpass");   //新密码
            String repass = req.getParameter("repass");     //确认新密码
            //System.out.println(oldpass);
            //System.out.println(loginUser.getPassword());
            //--------------------判断---------------------
            PrintWriter writer = resp.getWriter();
            if (Integer.parseInt(id) == (loginUser.getId())) {
            if (memberService.updatePwd(loginUser.getId(), newpass) > 0) {
                //密码修改成功
                writer.println("<script>" +
                        "alert('密码修改成功');" +
                        "window.parent.parent.location.href='/login.jsp'"
                        + "</script>");
                }
            } else if (memberService.updatePwd(Integer.parseInt(id), newpass) > 0)  {
                    //密码修改成功
                    writer.print("<script>" +
                            "alert('密码修改成功');" +
                            "window.parent.location='/MemberServlet.do?action=limit&pageIndex=1&pageSize=5'"
                            + "</script>");
                } else {
                    //密码修改失败
                    writer.println("<script>" +
                            "alert('密码修改异常');" +
                            "window.location.href='/member-password.jsp'"
                            + "</script>");
                }
        }
        //检查用户输入的密码是否正确
        if (value.equals("checkOldPass")) {
            //用户输入的用户名
            String id = req.getParameter("id");
            System.out.println("id"+id);
            String oldPassword = req.getParameter("oldPassword");
            System.out.println("旧密码:"+oldPassword);
            int i = memberService.checkPassword(Integer.parseInt(id),oldPassword);
            System.out.println(i);
            System.out.println(id);
            System.out.println(oldPassword);
            //判断
            PrintWriter writer = resp.getWriter();
                if (i > 0) {
                    //存在
                    writer.print(1);
                } else {
                    //不存在
                    writer.print(0);
                }
            }
        //----------------删除功能-----------------------------
        if (value.equals("delete")) {
            String id = req.getParameter("id");
            int i = memberService.deleteById(id);
            PrintWriter writer = resp.getWriter();
            if (i > 0) {
                //用户删除成功
                writer.println("<script>" +
                        "alert('用户删除成功');" +
                        "window.location.href='/MemberServlet.do?action=limit&pageIndex=1&pageSize=5'"
                        + "</script>");
            } else {
                //用户删除失败
                writer.println("<script>" +
                        "alert('用户删除失败');" +
                        "window.location.href='/MemberServlet.do?action=limit&pageIndex=1&pageSize=5'"
                        + "</script>");
            }
        }
        //检查用户添加输入的新用户名是否正确
        if (value.equals("checkUserName")) {
            //用户输入的用户名
            String username = req.getParameter("username");
            String username2 = "[\\u4e00-\\u9fa5_a-z]{1,6}";
            int i = memberService.checkName(username);
            //判断
            PrintWriter writer = resp.getWriter();
            if (username.matches(username2)) {
                if (i > 0) {
                    //存在
                    writer.print(1);
                } else {
                    //不存在
                    writer.print(0);
                }
            } else {
                writer.print(2);
            }
        }
        //检查用户添加输入的密码格式是否正确
        if (value.equals("checkUserWord")) {
            //用户输入的密码
            String oldpassword = req.getParameter("oldpassword");
            String oldpasswords = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
            PrintWriter writer = resp.getWriter();
            if (oldpassword.matches(oldpasswords)) {
                writer.print(0);
            } else {
                writer.print(2);
            }
        }
        //检查用户添加输入的电话号码是否正确
        if (value.equals("checkUserPhone")) {
            //用户输入的电话号码
            String userphone = req.getParameter("userphone");
            String  userphone2 = "1[0-9]{10}";
            //判断
            PrintWriter writer = resp.getWriter();
            if (userphone.matches(userphone2)) {
                //格式正确
                writer.print(0);
            } else {
                //格式不正确
                writer.print(2);
            }
        }
        //批量删除
        if (value.equals("delAll")){
            String checkId = req.getParameter("id");
//            System.out.println(checkId);
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
                    memberService.deleteById(id);
                }
                writer.print("<script>"+
                        "alert('会员信息删除成功');"+
                        "window.location.href = '/MemberServlet.do?action=limit&pageIndex=1&pageSize=5'"+
                        "</script>");
            }catch (Exception e){
                writer.print("<script>"+
                        "alert('会员信息删除失败');"+
                        "window.location.href = '/MemberServlet.do?action=limit&pageIndex=1&pageSize=5'"+
                        "</script>");
            }
        }
        //导出数据
        if (value.equals("exportException")){
            String name = req.getParameter("name");
            //System.out.println("要进行Excel导出操作...查询"+name);
            //获取数据库中的数据
            List<User> users = userService.selectAllUser();
            //1.创建工作簿
            HSSFWorkbook workbook = new HSSFWorkbook();
            //2.创建工作表
            HSSFSheet sheet = workbook.createSheet("会员信息表");
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
            cell_row01.setCellValue("会员列表数据");
            //设置标题样式
            HSSFCellStyle titleStyle = ExprotCellStyle.createTitleCellStyle(workbook);
            cell_row01.setCellStyle(titleStyle);

            //第二行
            rownum++;
            HSSFRow row02 = sheet.createRow(rownum);
            HSSFCell cell_row02 = row02.createCell(0);
            cell_row02.setCellValue("总数:" + users.size() + "，导出时间:" + DateUtils.nowTime());
            //设置小标题样式
            HSSFCellStyle secondTitleStyle = ExprotCellStyle.createSecondTitleStyle(workbook);
            cell_row02.setCellStyle(secondTitleStyle);

            //第三行
            rownum++;
            HSSFRow row03 = sheet.createRow(rownum);
            String[] titles = {"会员ID", "会员姓名", "会员密码", "会员性别", "会员生日", "会员电话", "会员地址"};
            //得到表头的样式
            HSSFCellStyle tableTitleStyle = ExprotCellStyle.createTableTitleStyle(workbook);
            for (int i = 0; i < titles.length; i++) {
                HSSFCell cell = row03.createCell(i);
                cell.setCellValue(titles[i]);
                cell.setCellStyle(tableTitleStyle);
            }

            //表格主题样式
            HSSFCellStyle tableBodyStyle = ExprotCellStyle.setRowCellCenter(workbook);
            for (int i = 0; i < users.size(); i++) {
                rownum++;

                HSSFRow row = sheet.createRow(rownum);
                User p = users.get(i);

                //创建id
                HSSFCell idCell = row.createCell(0);
                idCell.setCellValue(p.getId());
                idCell.setCellStyle(tableBodyStyle);

                //创建姓名
                HSSFCell nameCell = row.createCell(1);
                nameCell.setCellValue(p.getUsername());
                nameCell.setCellStyle(tableBodyStyle);

                //创建密码
                HSSFCell linkmaneCell = row.createCell(2);
                linkmaneCell.setCellValue(p.getPassword());
                linkmaneCell.setCellStyle(tableBodyStyle);

                //创建性别
                HSSFCell phoneCell = row.createCell(3);
                if (p.getSex() == 1) {
                    phoneCell.setCellValue("男");
                    phoneCell.setCellStyle(tableBodyStyle);
                } else if (p.getSex() == 0) {
                    phoneCell.setCellValue("女");
                    phoneCell.setCellStyle(tableBodyStyle);
                }


                //创建生日
                HSSFCell addressCell = row.createCell(4);
                addressCell.setCellValue(p.getBirthday());
                addressCell.setCellStyle(tableBodyStyle);

                //创建电话
                HSSFCell faxCell = row.createCell(5);
                faxCell.setCellValue(p.getPhone());
                faxCell.setCellStyle(tableBodyStyle);

                //创建地址
                HSSFCell descriptionCell = row.createCell(6);
                descriptionCell.setCellValue(p.getAddress());
                descriptionCell.setCellStyle(tableBodyStyle);
            }

            //3.设置content-disposition响应头控制浏览器以下载的形式打开文件
            //下载中文文件时，需要注意的地方就是中文文件名要使用
            String fileName = "会员管理数据信息表.xls";
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
        //模板下载
        if (value.equals("downloadExcelModel")) {
            //1.获取要下载的文件的绝对路径
            //在resources目录放入QQ.png，注意项目导出后resource中的文件被打包到/WEB-INF/classes下,服务器的真实路径
            String realPath = this.getServletContext().getRealPath("/WEB-INF/classes/会员列表数据模板.xls");
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
        //折线图请求
        if (value.equals("peopleNum")){
            //System.out.println("折线图请求发送过来");
            User user = new User();
            ArrayList<Integer> totalCount = memberService.getTotalCount(user);
            //System.out.println(totalCount);
            String jsonString = JSONObject.toJSONString(totalCount);
            PrintWriter writer = resp.getWriter();
            writer.print(jsonString);
        }
        //折线图第二请求
        if (value.equals("goOldUser")){
            //System.out.println("折线图请求发送过来");
            User user = new User();
            ArrayList<Integer> totalCount = memberService.getCount(user);
            //System.out.println(totalCount);
            String jsonString = JSONObject.toJSONString(totalCount);
            PrintWriter writer = resp.getWriter();
            writer.print(jsonString);
        }
        //上传文件
        //实现文件上传到数据库
        if (value.equals("excelImport")){
            extracted(req, resp);
        }
    }
    private void extracted(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
        MemberService memberService = new MemberServiceImpl();
        //执行excel文件导入操作
        List<User> userList = new ArrayList<>();
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
                    User user = new User();
                    //验证Excel文件字段，这里根据表格内名称
                    ImportExcelUtils.validCellValue(sheet,row,colNum,"会员姓名");
                    user.setUsername(ImportExcelUtils.getCellValue(sheet,row,colNum-1));

                    //列号需要自加获取
                    ImportExcelUtils.validCellValue(sheet,row,++colNum,"会员密码");
                    user.setPassword(ImportExcelUtils.getCellValue(sheet,row,colNum-1));

                    ImportExcelUtils.validCellValue(sheet,row,++colNum,"性别");
                    user.setSex("男".equals(ImportExcelUtils.getCellValue(sheet,row,colNum-1))?1:0);

                    ImportExcelUtils.validCellValue(sheet,row,++colNum,"会员生日");
                    user.setBirthday(ImportExcelUtils.getCellValue(sheet,row,colNum-1));

                    ImportExcelUtils.validCellValue(sheet,row,++colNum,"会员电话");
                    user.setPhone(ImportExcelUtils.getCellValue(sheet,row,colNum-1));

                    ImportExcelUtils.validCellValue(sheet,row,++colNum,"会员地址");
                    user.setAddress(ImportExcelUtils.getCellValue(sheet,row,colNum-1));

                    ImportExcelUtils.validCellValue(sheet,row,++colNum,"权限");
                    String cellValue = ImportExcelUtils.getCellValue(sheet, row, colNum - 1);
                    if ("1".equals(cellValue)){
                        user.setType(1);
                    }else if ("2".equals(cellValue)){
                        user.setType(2);
                    }else {
                        user.setType(3);
                    }
                    //存储对象到list集合中
                    userList.add(user);
                }
                System.out.println("===================导入的数据是=================");
                for (User user: userList) {
                    System.out.println(user);
                    //对象添加到数据库
                    memberService.addUser(user);
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
