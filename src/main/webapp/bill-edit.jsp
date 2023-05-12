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
    <link rel="stylesheet" href="./css/font.css">
    <link rel="stylesheet" href="./css/xadmin.css">
    <script src="/js/jquery.min.js"></script>
    <script type="text/javascript" src="./lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="./js/xadmin.js"></script>
    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="layui-fluid">
    <div class="layui-row">
        <form class="layui-form" action="/BillServlet.do?action=update&id=${requestScope.bliiById.id}" method="post">

                <div class="layui-form-item">
                    <div class="layui-form-item">
                        <label for="id" class="layui-form-label">
                            <span class="x-red">*</span>账单编号
                        </label>
                        <div class="layui-input-inline">
                            <input type="text" value="${bliiById.id}" id="id" name="id" required="" lay-verify="pass" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label for="title" class="layui-form-label">
                            <span class="x-red">*</span>账单名称
                        </label>
                        <div class="layui-input-inline">
                            <input type="text" value="${bliiById.title}" id="title" name="title" required="" lay-verify="pass" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label for="unit" class="layui-form-label">
                            <span class="x-red">*</span>账单单位
                        </label>
                        <div class="layui-input-inline">
                            <input type="text" value="${bliiById.unit}" id="unit" name="unit" required="" lay-verify="repass" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label for="num" class="layui-form-label">
                            <span class="x-red">*</span>账单数量
                        </label>
                        <div class="layui-input-inline">
                            <input type="text" value="${bliiById.num}" id="num" name="num" required="" lay-verify="repass" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label for="money" class="layui-form-label">
                            <span class="x-red">*</span>总金额
                        </label>
                        <div class="layui-input-inline">
                            <input type="text" value="${bliiById.money}" id="money" name="money" required="" lay-verify="repass" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label for="username" class="layui-form-label">
                            <span class="x-red">*</span>供应商</label>
                        <div class="layui-input-inline" >
                            <select name="providerid" id="username">
                                <option value="-1">----选择供应商----</option>
                                <c:forEach var="a" items="${requestScope.suppliersList}">
                                    <%--做回显--%>
                                    <option value="${a.id}"${requestScope.providerid==a.id?"selected":""}>${a.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">是否支付</label>
                        <div class="layui-input-block">
                            <input type="radio" name="ispay" value="1" title="已支付" ${bliiById.ispay==1?"checked":""}>
                            <input type="radio" name="ispay" value="0" title="未支付" ${bliiById.ispay==0?"checked":""}>
                        </div>
                    </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"></label>
                    <button class="layui-btn" lay-filter="add" type="submit">更新</button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
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
<%--异步获取下拉框--%>
<script type="text/javascript">
    function renderForm(){
        layui.use('form',function () {
            let form = layui.form;
            form.render();
        })
    }
    //页面加载的时候执行
    window.onload = function (){
        //发送AJAX异步请求去Servlet后台获取供应商下拉框的数据
        $.get("/BillsServlet?action=loadSupplier",function (result){
            for (var i = 0;i<result.length;i++){
                var id = result[i].id;
                var name = result[i].name;
                //供应商回显
                if (id==${requestScope.bills.providerid}){
                    //绑定供应商下拉框
                    //.append()：追加
                    //正常来说这样去绑定就可以了
                    $("#username").append("<option selected value='"+id+"'>"+name+"</option>");
                }else {
                    $("#username").append("<option value='"+id+"'>"+name+"</option>");
                }
                renderForm();
            }
        },"json");//返回JSON数据
    }
</script>
