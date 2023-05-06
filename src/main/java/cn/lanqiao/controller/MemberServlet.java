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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet ("/MemberServlet.do")
public class MemberServlet extends HttpServlet {
    MemberService memberService = new MemberServiceImpl();

    @Override
    //
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
            System.out.println("总条数：" + totalCount);
            //每页的数
            List<User> depatrs = memberService.getDepatrs(name, (Integer.parseInt(pageIndex) - 1) * (Integer.parseInt(pageSize)), Integer.parseInt(pageSize));
            System.out.println("每页查询的数据" + depatrs);
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
            User loginUser = (User) session.getAttribute("loginUser");
            //zheng'ze
            String username2 = "^[\\u4E00-\\u9FA5]{2,4}$";
            String userphone2 = "1[0-9]{10}";
            String oldpasswords = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
            //将值赋值给实体类
            User user = new User(null, username, oldpassword, Integer.parseInt(sex), birthday, userphone, address, Integer.parseInt(type), 0);
            //把值存入数据库
            int i = memberService.addUser(user);
            //后端王前端里面写代码
            PrintWriter writer = resp.getWriter();
            //检查添加用户姓名格式是否不对
            if (username.matches(username2)) {
                //检查添加用户姓名是否重复
                if (!username.equals(loginUser.getUsername())) {
                    //检查添加用户密码格式是否不对
                    if (oldpassword.matches(oldpasswords)) {
                        //检查添加用户电话号码格式是否不对
                        if (userphone.matches(userphone2)) {
                            //检查添加用户电话号码是否重复
                            if (!userphone.equals(loginUser.getPhone())) {
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
                            } else {
                                //电话号码重复
                                writer.println("<script>" +
                                        "alert('用户电话号码已存在');location='/member-add.jsp'"
                                        + "</script>");
                            }
                        } else {
                            //电话号码格式不对
                            writer.println("<script>" +
                                    "alert('用户电话号码必须是11位的阿拉伯数字');location='/member-add.jsp'"
                                    + "</script>");
                        }
                    }  else {
                        //用户密码格式不对
                        writer.println("<script>" +
                                "alert('用户密码必须是6到16个字符');location='/member-add.jsp'"
                                + "</script>");
                    }
                    }else {
                    //用户名重复
                    writer.println("<script>" +
                            "alert('用户名已存在');location='/member-add.jsp'"
                            + "</script>");
                }
                }else {
                //用户名格式不对重复
                writer.println("<script>" +
                        "alert('用户名必须是中文');location='/member-add.jsp'"
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
            User loginUser = (User) session.getAttribute("loginUser");
            //将值赋值给实体类
            User user = new User(Integer.parseInt(id), username, oldpassword, Integer.parseInt(sex), birthday, phone, address, Integer.parseInt(type), 0);
            int i = memberService.updateById(user);
            System.out.println(i);
            //后端王前端里面写代码
            PrintWriter writer = resp.getWriter();
                //判断用户修改的用户名是否重复
                if (!username.equals(loginUser.getUsername())) {
                    if (i > 0) {
                        writer.print("<script>" +
                                "alert('修改成功');" +
                                "window.location='/MemberServlet.do?action=limit&pageIndex=1&pageSize=5'"
                                + "</script>");
                    } else {
                        writer.print("<script>" +
                                "alert('修改失败');" +
                                "window.location.href='/member-edit.jsp'"
                                + "</script>");
                    }
                } else {
                    //用户名重复
                    writer.print("<script>" +
                            "alert('用户名已存在');" +
                            "window.parent.location.href='/member-edit.jsp'"
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
        //---------------修改密码功能-----------------------
        if (value.equals("updatepwd")) {
            User loginUser = (User) session.getAttribute("loginUser");
            String oldpass = req.getParameter("oldpass");   //旧密码
            String newpass = req.getParameter("newpass");   //新密码
            String repass = req.getParameter("repass");     //确认新密码
            System.out.println(oldpass);
            System.out.println(loginUser.getPassword());
            //--------------------判断---------------------
            PrintWriter writer = resp.getWriter();
            //判断旧密码
            if (oldpass.equals(loginUser.getPassword())) {
                //旧密码和新密码不相等
                if (!oldpass.equals(newpass)) {
                    //新密码和确认输入密码相等
                    if (newpass.equals(repass)) {
                        //两次判断皆通过,开始修改密码
                        int i = memberService.updatePwd(loginUser.getId(), newpass);
                        if (i > 0) {
                            //密码修改成功
                            writer.println("<script>" +
                                    "alert('密码修改成功');" +
                                    "window.parent.parent.location.href='/login.jsp'"
                                    + "</script>");
                        } else {
                            //密码修改失败
                            writer.println("<script>" +
                                    "alert('密码修改异常');" +
                                    "window.location.href='/member-password.jsp'"
                                    + "</script>");
                        }
                    } else {
                        writer.println("<script>" +
                                "alert('新密码与确认密码不相同');" +
                                "window.location.href='/member-password.jsp'"
                                + "</script>");
                    }
                } else {
                    writer.println("<script>" +
                            "alert('要修改的密码不能和旧密码相同');" +
                            "window.location.href='/member-password.jsp'"
                            + "</script>");
                }
            } else {
                writer.println("<script>" +
                        "alert('原密码输入错误');" +
                        "window.location.href='/member-password.jsp'"
                        + "</script>");
            }
        }
        //检查用户输入的密码是否正确
        if (value.equals("checkOldPass")) {
            String oldPassword = req.getParameter("oldPassword");
            User loginUser = (User) session.getAttribute("loginUser");
            //判断
            PrintWriter writer = resp.getWriter();
            if (loginUser.getPassword().equals(oldPassword)) {
                //原始正确
                writer.print(1);
            } else {
                //原始密码错误
                writer.println(0);
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
            String username2 = "^[\\u4E00-\\u9FA5]{1,4}$";
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
            int i = memberService.checkPhone(userphone);
            //判断
            PrintWriter writer = resp.getWriter();
            if (userphone.matches(userphone2)) {
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
    }
}
