<%@ page import="cn.lanqiao.pojo.Supplier" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html class="x-admin-sm">

<head>
    <meta charset="UTF-8">
    <title>超市账单管理系统</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <link rel="stylesheet" href="/css/font.css">
    <link rel="stylesheet" href="/css/xadmin.css">
    <script type="text/javascript" src="/lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="/js/xadmin.js"></script>
    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<%
    Supplier supplier =(Supplier) request.getAttribute("supple");
%>
<div class="layui-fluid">
    <div class="layui-row">
        <form class="layui-form" action="/SupplierServlet.do?action=update&id=${requestScope.supplierById.id}" method="post">
<%--            <c:forEach items="${requestScope.supplierById}" var="s">--%>
                <div class="layui-form-item">
                    <label for="name" class="layui-form-label">
                        <span class="x-red">*</span>供应商名称</label>
                    <div class="layui-input-inline">
                        <input type="text" id="name" name="name" required="" lay-verify="email" autocomplete="off" class="layui-input" value="${supplierById.name}">
                    </div>
                    <div class="layui-form-mid layui-word-aux">
                        <span class="x-red">*</span>请输入供应商名称
                    </div>
                    <div class="layui-form-item">
                        <label for="linkman" class="layui-form-label">
                            <span class="x-red">*</span>联系人
                        </label>
                        <div class="layui-input-inline">
                            <input type="text" id="linkman" name="linkman" required="" lay-verify="pass" autocomplete="off" class="layui-input" value="${supplierById.linkman}">
                        </div>
                        <div class="layui-form-mid layui-word-aux">
                            <span class="x-red">*</span>请输入联系人
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label for="phone" class="layui-form-label">
                            <span class="x-red">*</span>联系电话
                        </label>
                        <div class="layui-input-inline">
                            <input type="text" id="phone" name="phone" required="" lay-verify="repass" autocomplete="off" class="layui-input" value="${supplierById.phone}">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label for="address" class="layui-form-label">
                            <span class="x-red">*</span>联系地址
                        </label>
                        <div class="layui-input-inline">
                            <input type="text" id="address" name="address" required="" lay-verify="repass" autocomplete="off" class="layui-input" value="${supplierById.address}">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label for="fax" class="layui-form-label">
                            <span class="x-red">*</span>传真
                        </label>
                        <div class="layui-input-inline">
                            <input type="text" id="fax" name="fax" required="" lay-verify="repass" autocomplete="off" class="layui-input" value="${supplierById.fax}">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label for="description" class="layui-form-label">
                            <span class="x-red">*</span>描述</label>
                        <div class="layui-input-inline">
                            <input type="text" id="description" name="description" required="" lay-verify="repass" autocomplete="off" class="layui-input" value="${supplierById.description}">
                        </div>
                    </div>
<%--            </c:forEach>--%>
                <div class="layui-form-item">
                    <label class="layui-form-label"></label>
                    <button class="layui-btn" lay-filter="add" type="submit">修改</button>
                </div>
                </div>
        </form>
    </div>
</div>
<script>
    layui.use(['form', 'layer','jquery'],
        function() {
            $ = layui.jquery;
            var form = layui.form,
                layer = layui.layer;

            //自定义验证规则
            form.verify({
                nikename: function(value) {
                    if (value.length < 5) {
                        return '昵称至少得5个字符啊';
                    }
                },
                pass: [/(.+){6,12}$/, '密码必须6到12位'],
                repass: function(value) {
                    if ($('#L_pass').val() != $('#L_repass').val()) {
                        return '两次密码不一致';
                    }
                }
            });

            //监听提交
            form.on('submit(add)',
                function(data) {
                    console.log(data);
                    //发异步，把数据提交给php
                    layer.alert("增加成功", {
                            icon: 6
                        },
                        function() {
                            //关闭当前frame
                            xadmin.close();

                            // 可以对父窗口进行刷新
                            xadmin.father_reload();
                        });
                    return false;
                });

        });</script>
<script>var _hmt = _hmt || []; (function() {
    var hm = document.createElement("script");
    hm.src = "https://hm.baidu.com/hm.js?b393d153aeb26b46e9431fabaf0f6190";
    var s = document.getElementsByTagName("script")[0];
    s.parentNode.insertBefore(hm, s);
})();</script>
</body>

</html>
<script>
    layui.use(['form', 'util', 'laydate'], function(){
        var form = layui.form;
        var layer = layui.layer;
        var util = layui.util;
        var laydate = layui.laydate;

    });
</script>
