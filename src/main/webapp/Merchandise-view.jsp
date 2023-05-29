<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="cn.lanqiao.pojo.User" %>
<%@ page import="cn.lanqiao.pojo.Merchan" %>
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
<div class="layui-form-item">
    <label class="layui-form-label">订单ID:</label><span style="position: relative;top: 11px">${requestScope.merchanById.id}</span>
</div>
<div class="layui-form-item">
    <label class="layui-form-label">订单编号:</label><span style="position: relative;top: 11px">${requestScope.merchanById.orderNo}</span>
</div>
<div class="layui-form-item">
    <label class="layui-form-label">会员名称:</label><span style="position: relative;top: 11px">${requestScope.merchanById.memberName}</span>
</div>
<div class="layui-form-item">
    <label class="layui-form-label">订单类型:</label><span style="position: relative;top: 11px">${requestScope.merchanById.orderType}</span>
</div>
<div class="layui-form-item">
    <label class="layui-form-label">总金额:</label><span style="position: relative;top: 11px">${requestScope.merchanById.totalAmount}</span>
</div>
<div class="layui-form-item">
    <label class="layui-form-label">支付状态:</label>
    <span style="position: relative;top: 11px">
    <c:if test="${requestScope.merchanById.paymentStatus == 0}">
        未支付
    </c:if>
    <c:if test="${requestScope.merchanById.paymentStatus == 1}">
        已支付
    </c:if>
    </span>
</div>
<div class="layui-form-item">
    <label class="layui-form-label">生成时间:</label><span style="position: relative;top: 11px">${requestScope.merchanById.createdAt}</span>
</div>
<div class="layui-form-item">
    <label class="layui-form-label">支付金额:</label><span style="position: relative;top: 11px">${requestScope.merchanById.paymentAmount}</span>
</div>
</body>
</html>
