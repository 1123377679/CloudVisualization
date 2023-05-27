<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html  class="x-admin-sm">
<head>
    <meta charset="UTF-8">
    <title>后台登录-X-admin2.2</title>
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="stylesheet" href="/css/font.css">
    <link rel="stylesheet" href="/css/login.css">
    <link rel="stylesheet" href="/css/xadmin.css">
    <script type="text/javascript" src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <script src="/lib/layui/layui.js" charset="utf-8"></script>
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body class="login-bg">

<div class="login layui-anim layui-anim-up" style="position: fixed;top: -7%;bottom: 7%;left: 40%;transform: translateX(-40%);">
    <div class="message">超市管理系统用户注册</div>
    <div id="darkbannerwrap"></div>

    <form method="post" class="layui-form" action="/userServlet.do?action=register">
        <input name="username" placeholder="用户名"  type="text" lay-verify="required" class="layui-input" >
        <hr class="hr15">
        <input name="oncePassword" lay-verify="required" placeholder="密码"  type="password" class="layui-input">
        <hr class="hr15">
        <input name="twicePassword" lay-verify="required" placeholder="再次输入密码"  type="password" class="layui-input">
        <hr class="hr15">
        <input name="sex" lay-verify="required" placeholder="性别"  type="text" class="layui-input">
        <hr class="hr15">
        <input name="birthday" lay-verify="required" placeholder="生日"  type="text" class="layui-input">
        <hr class="hr15">
        <input name="phone" lay-verify="required" placeholder="联系电话"  type="text" class="layui-input">
        <hr class="hr15">
        <input name="address" lay-verify="required" placeholder="家庭住址"  type="text" class="layui-input">
        <hr class="hr15">
        <%--  验证码  --%>
        <input id="yanzhengm" style="width: 131px;" type="text" name="usercode" placeholder="请输入验证码" required/>
        <!--验证码图片-->
        <img src="/CodeServlet" onclick="changeImage(this);" style="position: relative;cursor: pointer;"/>
        <input value="注册" lay-submit lay-filter="login" style="width:100%;margin-top: 15px" type="submit">
        <input value="返回登录" style="width:100%;margin-top: 15px;" type="button" onclick="location.href='/login.jsp';">
<%--        <input value="忘记密码？" lay-submit lay-filter="login" style="width:32%;margin-top: 15px;margin-left: 68%;" type="submit">--%>
<%--        <hr class="hr20" >--%>
    </form>
    <div style="color: red">
            <%=
         //获取值并且进行判断
         request.getAttribute("message")==null?"":request.getAttribute("message")
         %>
        <div>

        </div>

        <script>
            //百度统计可去掉
            var _hmt = _hmt || [];
            (function() {
                var hm = document.createElement("script");
                hm.src = "https://hm.baidu.com/hm.js?b393d153aeb26b46e9431fabaf0f6190";
                var s = document.getElementsByTagName("script")[0];
                s.parentNode.insertBefore(hm, s);
            })();
        </script>
</body>
</html>
<script>
    function changeImage(img) {
        //图片重新加载src地址，因为图片是一个GET请求，浏览器有缓存  time 表示是一个随机参数 ，防止浏览器缓存
        img.src="/CodeServlet?time="+new Date();
    }
</script>