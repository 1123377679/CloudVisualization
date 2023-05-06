<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="cn.lanqiao.pojo.User" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/css/font.css">
    <link rel="stylesheet" href="/css/xadmin.css">
    <script type="text/javascript" src="/lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="/js/xadmin.js"></script>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
</head>
<body>
<%
    User users = (User) request.getAttribute("users");
%>
<div class="layui-form-item">
    <label class="layui-form-label">用户编号:</label><span style="position: relative;top: 11px">${requestScope.users.id}</span>
</div>
<div class="layui-form-item">
    <label class="layui-form-label">用户姓名:</label><span style="position: relative;top: 11px">${requestScope.users.username}</span>
</div>
<div class="layui-form-item">
    <label class="layui-form-label">用户性别:</label><span style="position: relative;top: 11px">${requestScope.users.sex == 1?"男":"女"}</span>
</div>
<div class="layui-form-item">
    <label class="layui-form-label">出生日期:</label><span style="position: relative;top: 11px">${requestScope.users.birthday}</span>
</div>
<div class="layui-form-item">
    <label class="layui-form-label">用户电话:</label><span style="position: relative;top: 11px">${requestScope.users.phone}</span>
</div>
<div class="layui-form-item">
    <label class="layui-form-label">用户地址:</label><span style="position: relative;top: 11px">${requestScope.users.address}</span>
</div>
<div class="layui-form-item">
    <label class="layui-form-label">用户类别:</label>
    <span style="position: relative;top: 11px">
        <c:if test="${requestScope.users.type == 1}">
           超级管理员
        </c:if>
        <c:if test="${requestScope.users.type == 2}">
            经理
        </c:if>
        <c:if test="${requestScope.users.type == 3}">
            普通员工
        </c:if>
    </span>
</div>
</body>
</html>
