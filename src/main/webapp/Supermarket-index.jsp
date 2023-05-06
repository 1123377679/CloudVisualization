<%@ page import="cn.lanqiao.pojo.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html class="x-admin-sm">
<head>
    <meta charset="UTF-8">
    <title>后台登录-X-admin2.2</title>
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="stylesheet" href="/css/font.css">
    <link rel="stylesheet" href="/css/xadmin.css">
    <!-- <link rel="stylesheet" href="./css/theme5.css"> -->
    <script src="/lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="/js/xadmin.js"></script>
    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script>
        // 是否开启刷新记忆tab功能
        // var is_remember = false;
    </script>
</head>
<body class="index">
<!-- 顶部开始 -->
<div class="container">
    <div class="logo">
        <a href="/index.html">X-admin v2.2</a></div>
    <div class="left_open">
        <a><i title="展开左侧栏" class="iconfont">&#xe699;</i></a>
    </div>
    <ul class="layui-nav left fast-add" lay-filter="">
        <li class="layui-nav-item">
            <a href="javascript:;">+新增</a>
            <dl class="layui-nav-child">
                <!-- 二级菜单 -->
                <dd>
                    <a onclick="xadmin.open('最大化','http://www.baidu.com','','',true)">
                        <i class="iconfont">&#xe6a2;</i>弹出最大化</a></dd>
                <dd>
                    <a onclick="xadmin.open('弹出自动宽高','http://www.baidu.com')">
                        <i class="iconfont">&#xe6a8;</i>弹出自动宽高</a></dd>
                <dd>
                    <a onclick="xadmin.open('弹出指定宽高','http://www.baidu.com',500,300)">
                        <i class="iconfont">&#xe6a8;</i>弹出指定宽高</a></dd>
                <dd>
                    <a onclick="xadmin.add_tab('在tab打开','member-list.html')">
                        <i class="iconfont">&#xe6b8;</i>在tab打开</a></dd>
                <dd>
                    <a onclick="xadmin.add_tab('在tab打开刷新','member-del.html',true)">
                        <i class="iconfont">&#xe6b8;</i>在tab打开刷新</a></dd>
            </dl>
        </li>
    </ul>
<%--    用户信息名字的显示：      --%>
<%
    User loginUser = (User) session.getAttribute("loginUser");
    String username = null;
    if (loginUser != null) {
        //拿到name存入username
        username = loginUser.getUsername();
    } else {
        response.sendRedirect("/nologin.jsp");
    }
%>
    <ul class="layui-nav right" lay-filter="">
        <li class="layui-nav-item">
            <a href="javascript:;">
                <%=username%>
            </a>

            <dl class="layui-nav-child">
                <!-- 二级菜单 -->
                <dd>
                    <a onclick="xadmin.open('个人信息','http://www.baidu.com')">个人信息</a></dd>
                <dd>
                    <a onclick="xadmin.open('切换帐号','http://www.baidu.com')">切换帐号</a></dd>
                <dd>
                    <a href="#" onclick="logout();">退出</a></dd>
            </dl>
        </li>
        <li class="layui-nav-item to-index">
            <a href="/">前台首页</a></li>
    </ul>
</div>
<!-- 顶部结束 -->
<!-- 中部开始 -->
<!-- 左侧菜单开始 -->
<div class="left-nav">
    <div id="side-nav">
        <ul id="nav">
            <li>
                <a href="javascript:;">
                    <i class="iconfont left-nav-li" lay-tips="会员管理">&#xe6b8;</i>
                    <cite>会员管理</cite>
                    <i class="iconfont nav_right">&#xe697;</i></a>
                <ul class="sub-menu">
                    <li>
                        <%--前端发送请求,需要后台拿到action=list数据--%>
                        <a onclick="xadmin.add_tab('会员列表','/MemberServlet.do?action=limit&pageIndex=1&pageSize=5')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>会员列表</cite></a>
                    </li>
                </ul>
            </li>
        <%--供应商---------------------------------------------------------------------%>

            <li>
                <a href="javascript:;">
                    <i class="iconfont left-nav-li" lay-tips="供应商管理">&#xe723;</i>
                    <cite>供应商管理</cite>
                    <i class="iconfont nav_right">&#xe697;</i></a>
                <ul class="sub-menu">
                    <li>
                        <a onclick="xadmin.add_tab('供应商列表','/SupplierServlet.do?action=limit&pageIndex=1&pageSize=5')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>订单列表</cite></a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="iconfont left-nav-li" lay-tips="账单管理">&#xe723;</i>
                    <cite>账单管理</cite>
                    <i class="iconfont nav_right">&#xe697;</i></a>
                <ul class="sub-menu">
                    <li>
                        <a onclick="xadmin.add_tab('账单管理','/BillServlet.do?action=limit&pageIndex=1&pageSize=5')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>账单列表</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('统计页面','/xxx')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>账单统计(柱状图)</cite>
                        </a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('统计页面','/xxx')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>账单统计(饼状图)</cite>
                        </a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="iconfont left-nav-li" lay-tips="供应商管理">&#xe6b8;</i>
                    <cite>登录日志</cite>
                    <i class="iconfont nav_right">&#xe697;</i></a>
                <ul class="sub-menu">
                    <li>
                        <%--前端发送请求,需要后台拿到action=list数据--%>
                        <a onclick="xadmin.add_tab('登录日志','/LogServlet.do?action=mylogs&pageIndex=1&pageSize=20')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>用户登录日志</cite></a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="iconfont left-nav-li" lay-tips="供应商管理">&#xe6b8;</i>
                    <cite>权限管理</cite>
                    <i class="iconfont nav_right">&#xe697;</i></a>
                <ul class="sub-menu">
                    <li>
                        <%--前端发送请求,需要后台拿到action=list数据--%>
                        <a onclick="xadmin.add_tab('权限分类','/AuthorityServlet.do?action=limit&pageIndex=1&pageSize=5')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>权限分类</cite>
                        </a>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
</div>
<!-- <div class="x-slide_left"></div> -->
<!-- 左侧菜单结束 -->
<!-- 右侧主体开始 -->
<div class="page-content">
    <div class="layui-tab tab" lay-filter="xbs_tab" lay-allowclose="false">
        <ul class="layui-tab-title">
            <li class="home">
                <i class="layui-icon">&#xe68e;</i>我的桌面</li></ul>
        <div class="layui-unselect layui-form-select layui-form-selected" id="tab_right">
            <dl>
                <dd data-type="this">关闭当前</dd>
                <dd data-type="other">关闭其它</dd>
                <dd data-type="all">关闭全部</dd></dl>
        </div>
        <div class="layui-tab-content">
            <div class="layui-tab-item layui-show">
                <iframe src='/Supermarket-welcome.jsp' frameborder="0" scrolling="yes" class="x-iframe"></iframe>
            </div>
        </div>
        <div id="tab_show"></div>
    </div>
</div>
<div class="page-content-bg"></div>
<style id="theme_style"></style>
<!-- 右侧主体结束 -->
<!-- 中部结束 -->
<script>//百度统计可去掉
var _hmt = _hmt || []; (function() {
    var hm = document.createElement("script");
    hm.src = "https://hm.baidu.com/hm.js?b393d153aeb26b46e9431fabaf0f6190";
    var s = document.getElementsByTagName("script")[0];
    s.parentNode.insertBefore(hm, s);
})();</script>
</body>

</html>
<script>
    function logout() {
        if (confirm("你确定要退出系统吗？")){
            //向后端发出退出请求
            location.href = "/userServlet.do?action=logout";
        }
    }
</script>