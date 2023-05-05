<%@ page import="cn.lanqiao.pojo.User" %>
<%@ page import="cn.lanqiao.pojo.Supplier" %>
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
  <label class="layui-form-label">供应商编码:</label><span style="position: relative;top: 11px">${requestScope.supplier.id}</span>
</div>
<div class="layui-form-item">
  <label class="layui-form-label">供应商名称:</label><span style="position: relative;top: 11px">${requestScope.supplier.name}</span>
</div>
<div class="layui-form-item">
  <label class="layui-form-label">联系人:</label><span style="position: relative;top: 11px">${requestScope.supplier.linkman}</span>
</div>
<div class="layui-form-item">
  <label class="layui-form-label">联系电话:</label><span style="position: relative;top: 11px">${requestScope.supplier.phone}</span>
</div>
<div class="layui-form-item">
  <label class="layui-form-label">地址:</label><span style="position: relative;top: 11px">${requestScope.supplier.address}</span>
</div>
<div class="layui-form-item">
  <label class="layui-form-label">传真:</label><span style="position: relative;top: 11px">${requestScope.supplier.fax}</span>
</div>
<div class="layui-form-item">
  <label class="layui-form-label">简介:</label><span style="position: relative;top: 11px">${requestScope.supplier.description}</span>
</div>
</body>
</html>
