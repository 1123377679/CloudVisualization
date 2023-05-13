package cn.lanqiao.Filter;

import cn.hutool.core.date.DateUtil;
import cn.lanqiao.controller.BehaviorLogDAO;
import cn.lanqiao.pojo.Action;
import cn.lanqiao.pojo.BehaviorLog;
import cn.lanqiao.pojo.User;
import cn.lanqiao.utils.Address;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/xxx.do")  // 配置过滤器拦截所有请求
public class BehaviorLogFilter implements Filter {
    private BehaviorLogDAO behaviorLogDAO;

    @Override
    public void init(FilterConfig filterConfig) {
        // 初始化操作，可留空
        behaviorLogDAO = new BehaviorLogDAO();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        //获取请求的URL
        String requestURI = httpRequest.getRequestURI();
        // 获取请求的操作类型，这里假设根据请求路径中的关键词判断
        Action action = getActionFromRequest(requestURI,request);
        HttpSession session = httpRequest.getSession(false);
        if (session!=null){
            //获取Session中的用户信息
            User loginUser = (User) session.getAttribute("loginUser");
            if (loginUser !=null){
                BehaviorLog behaviorLog = new BehaviorLog();
                //创建ip工具类
                Address address = new Address();
                behaviorLog.setUsername(loginUser.getUsername());//用户名
                behaviorLog.setType(request.getParameter("action"));//行为类型
                behaviorLog.setDescription(action.getDescription());//行为描述
                behaviorLog.setModel(action.getType());//行为所属模块
                behaviorLog.setIp(address.getIp());//ip
                behaviorLog.setOperationtime(String.valueOf(DateUtil.date()));//行为记录时间
                behaviorLog.setResult(null);//行为返回结果
                // 将用户操作日志插入到数据库
                behaviorLogDAO.insertBehaviorLog(behaviorLog);
            }
        }else {
            httpResponse.sendRedirect("/nologin.jsp");
        }
        // 继续处理请求
        chain.doFilter(request, response);
    }


    @Override
    public void destroy() {
        // 销毁操作，可留空
    }

    // 根据请求路径获取操作类型
    private Action getActionFromRequest(String requestURI,ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String param = httpRequest.getParameter("action");
        String actionType = "";
        String actionDescription = "";
        if (requestURI.matches(".*/login.jsp")) {
            actionType = "跳转登录页面";
        } else if (requestURI.contains("/userServlet.do")){
            if (param.equals("logout")){
                actionType = "会员登录与退出";
                actionDescription = "退出登录";
            }
        } else if (requestURI.contains("/MemberServlet.do")) {
            actionType = "会员管理";
            switch (param){
                case "limit":
                    actionDescription = "查看会员信息";
                    break;
                case "add":
                    actionDescription = "新增会员信息";
                    break;
                case "goUpdate":
                    actionDescription = "跳转修改会员信息页面";
                    break;
                case "update":
                    actionDescription = "修改会员信息";
                    break;
                case "details":
                    actionDescription = "查看会员详情信息";
                    break;
                case "updatepwd":
                    actionDescription = "修改会员密码";
                    break;
                case "delete":
                    actionDescription = "删除会员";
                    break;
                case "delAll":
                    actionDescription = "批量删除会员";
                    break;
                case "exportException":
                    actionDescription = "导出会员信息为Excel表格";
                    break;
                case "downloadExcelModel":
                    actionDescription = "下载会员数据模板文件";
                    break;
                case "excelImport":
                    actionDescription = "将记录在模板文件上的会员数据上传到数据库";
                    break;
                default:
                    break;
            }
        } else if (requestURI.contains("/SupplierServlet.do")) {
            actionType = "供应商管理";
            switch (param){
                case "limit":
                    actionDescription = "查看供应商信息";
                    break;
                case "add":
                    actionDescription = "新增供应商信息";
                    break;
                case "goUpdate":
                    actionDescription = "跳转修改供应商信息页面";
                    break;
                case "update":
                    actionDescription = "修改会员信息";
                    break;
                case "details":
                    actionDescription = "查看供应商详情信息";
                    break;
                case "delete":
                    actionDescription = "删除供应商";
                    break;
                case "delAll":
                    actionDescription = "批量删除供应商";
                    break;
                case "exportException":
                    actionDescription = "导出供应商信息为Excel表格";
                    break;
                case "downloadExcelModel":
                    actionDescription = "下载供应商数据模板文件";
                    break;
                case "excelImport":
                    actionDescription = "将记录在模板文件上的供应商数据上传到数据库";
                    break;
                default:
                    break;
            }
        } else if (requestURI.contains("/BillServlet.do")){
            actionType = "账单管理";
            switch (param){
                case "limit":
                    actionDescription = "查看账单信息";
                    break;
                case "add":
                    actionDescription = "新增账单信息";
                    break;
                case "goUpdate":
                    actionDescription = "跳转修改账单信息页面";
                    break;
                case "update":
                    actionDescription = "修改账单信息";
                    break;
                case "details":
                    actionDescription = "查看账单详情信息";
                    break;
                case "delete":
                    actionDescription = "删除账单";
                    break;
                case "delAll":
                    actionDescription = "批量删除账单";
                    break;
                case "exportException":
                    actionDescription = "导出账单信息为Excel表格";
                    break;
                case "downloadExcelModel":
                    actionDescription = "下载账单数据模板文件";
                    break;
                case "excelImport":
                    actionDescription = "将记录在模板文件上的账单数据上传到数据库";
                    break;
                default:
                    break;
            }
        }else if (requestURI.contains("/LogServlet.do")){
            actionType = "用户日志";
            switch (param){
                case "mylogs":
                    actionDescription = "查看用户登陆日志";
                    break;
                case "delete":
                    actionDescription = "对登录日志进行删除操作";
                    break;
                case "exportExcel":
                    actionDescription = "将登陆日志的所有数据导出为Excel表格";
                    break;
                case "downloadExcelModel":
                    actionDescription = "下载登录日志模板文件";
                    break;
                case "excelImport":
                    actionDescription = "将记录在模板文件上的日志数据上传到数据库";
                    break;
                case "delAll":
                    actionDescription = "对登录日志进行批量删除操作";
                    break;
                case "Behaviors":
                    actionDescription = "查看用户行为日志";
                    break;
                default:
                    break;
            }
        }else if (requestURI.contains("/AuthorityServlet.do")){
            actionType = "权限分类";
            switch (param){
                case "limit":
                    actionDescription = "查看用户权限";
                    break;
                case "goUpdate":
                    actionDescription = "跳转修改用户权限页面";
                    break;
                case "update":
                    actionDescription = "修改用户权限";
                    break;
                default:
                    break;
            }
        }
        return new Action(actionType,actionDescription);
    }
}
