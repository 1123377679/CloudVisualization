package cn.lanqiao.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.lanqiao.pojo.LoginLog;
import cn.lanqiao.pojo.User;
import cn.lanqiao.service.LogService;
import cn.lanqiao.service.MemberService;
import cn.lanqiao.service.UserService;
import cn.lanqiao.service.impl.LogServiceImpl;
import cn.lanqiao.service.impl.MemberServiceImpl;
import cn.lanqiao.service.impl.UserServiceImpl;
import cn.lanqiao.utils.Address;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@WebServlet("/userServlet.do")
public class UserServlet extends HttpServlet {
    UserService userService = new UserServiceImpl();
    MemberService memberService = new MemberServiceImpl();
    //添加用户登录的ip地址
    //引入业务层
    LogService logService = new LogServiceImpl();
    //创建ip工具类
    Address address = new Address();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //处理请求和响应乱码
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        String value = req.getParameter("action");
        //验证码操作
        //获取前端验证码内容
        String usercode = req.getParameter("usercode");
        HttpSession session = req.getSession();
        String syscode = String.valueOf(session.getAttribute("syscode"));
        //登录功能
        if (value.equals("login" )) {
                //equalsIgnoreCase:不区分大小写
                if (usercode.equalsIgnoreCase(syscode)){
                String username = req.getParameter("username");
                String password = req.getParameter("password");
                User user = new User(username, password);
                User login = userService.login(user);
                if (login != null) {
                    //登录成功，记录日志到数据库
                    //获取登录时间
                    DateTime date = DateUtil.date();
                    LoginLog log = new LoginLog(null,login.getUsername(),address.getAddress(),address.getIp(),String.valueOf(date),0);
                    int i = logService.addLogs(log);
                    //登录成功
                    //跳转到Supermarket-index页面
                    resp.sendRedirect("/Supermarket-index.jsp");
                    //个人信息名字：
                    session.setAttribute("loginUser",login);
                    //管理员名字：
                    session.setAttribute("ManageUser",login);
                } else {
//            System.out.println("登录失败.");
                    //存值并且赋值
                    req.setAttribute("message","账号或密码错误");
                    //转页面
                    req.getRequestDispatcher("/login.jsp").forward(req,resp);
                    }
                }else {
                req.setAttribute("message","验证码错误");
                req.getRequestDispatcher("/login.jsp").forward(req,resp);
            }
        }
        //退出功能
        if (value.equals("logout")) {
            //取消session
            session.invalidate();
            //返回到登录页面
            resp.sendRedirect("/login.jsp");
        }
        //注册功能
        if(value.equals("register")){
            //判断验证码是否正确
            PrintWriter writer = resp.getWriter();
            if (usercode.equalsIgnoreCase(syscode)){
                //获取用户名、第一次输入的密码、确认密码、性别、生日、联系电话、家庭住址
                String username = req.getParameter("username");
                String oncePassword = req.getParameter("oncePassword");
                String twicePassword = req.getParameter("twicePassword");
                String sex = req.getParameter("sex");
                int trueSex = -1;
                String birthday = req.getParameter("birthday");
                String phone = req.getParameter("phone");
                String address = req.getParameter("address");
                //判断所有输入框中是否都有信息
                if (username != null && oncePassword !=null && twicePassword !=null && sex != null && birthday != null && phone != null && address != null){
                    //查询所有用户信息再遍历取出所有用户名用以比较，默认输入的用户名不存在(为true)，若存在，则checkName为false
                    boolean checkName = true;
                    List<User> users = userService.selectAllUser();
                    for (User user : users){
                        if (username.equals(user.getUsername())){
                            checkName = false;
                            break;
                        }else {
                            checkName = true;
                        }
                    }
                    //判断用户名在数据库中是否存在，如果不存在就进行下一步
                    if (checkName){
                        //判断两次输入的密码是否一致
                        if (oncePassword.equals(twicePassword)){
                            //判断性别输入是否规范
                            if (sex.equals("男") || Integer.parseInt(sex) == 1){
                                trueSex = 1;
                            }else if (sex.equals("女") || Integer.parseInt(sex) == 0){
                                trueSex = 0;
                            }else {
                                //存值并且赋值
                                req.setAttribute("message","请输入正确的性别（男or女）");
                                req.getRequestDispatcher("/register.jsp").forward(req,resp);
                                return;
                            }
                            User User = new User(null,username,twicePassword,trueSex,birthday,phone,address,3,0);
                            memberService.addUser(User);
                            //获取注册时间
                            DateTime date = DateUtil.date();
                            System.out.println("注册时间："+date);
                            writer.print("<script>"+
                                    "alert('注册成功');"+
                                    "window.location.href = '/login.jsp'"+
                                    "</script>");
                        }else{
                            //存值并且赋值
                            req.setAttribute("message","两次输入的密码不一致");
                            req.getRequestDispatcher("/register.jsp").forward(req,resp);
                        }
                    }else {
                        System.out.println("用户已存在");
                        //存值并且赋值
                        req.setAttribute("message","用户已存在");
                        req.getRequestDispatcher("/register.jsp").forward(req,resp);
                    }
                }else {
                    req.setAttribute("message","所有信息都必须填写");
                    req.getRequestDispatcher("/register.jsp").forward(req,resp);
                }
            }else {
                req.setAttribute("message","验证码错误");
                req.getRequestDispatcher("/register.jsp").forward(req,resp);
            }

        }
    }
}
