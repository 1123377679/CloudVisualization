
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <label class="layui-form-label">账单编码:</label><span style="position: relative;top: 11px">${requestScope.bills.id}</span>
</div>
<div class="layui-form-item">
    <label class="layui-form-label">商品名称:</label><span style="position: relative;top: 11px">${requestScope.bills.title}</span>
</div>
<div class="layui-form-item">
    <label class="layui-form-label">供应商:</label><span style="position: relative;top: 11px">${requestScope.bills.providerid}</span>
</div>
<div class="layui-form-item">
    <label class="layui-form-label">账单金额:</label><span style="position: relative;top: 11px">${requestScope.bills.money}</span>
</div>
<div class="layui-form-item">
    <label class="layui-form-label">商品单位:</label><span style="position: relative;top: 11px">${requestScope.bills.unit}</span>
</div>
<div class="layui-form-item">
    <label class="layui-form-label">商品数量:</label><span style="position: relative;top: 11px">${requestScope.bills.num}</span>
</div>
<div class="layui-form-item">
    <label class="layui-form-label">是否付款:</label><span style="position: relative;top: 11px">${requestScope.bills.ispay==1?"已付款":"未付款"}</span>
</div>
</body>
</html>
