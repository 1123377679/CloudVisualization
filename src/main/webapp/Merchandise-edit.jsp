<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html class="x-admin-sm">

<head>
    <meta charset="UTF-8">
    <title>欢迎页面-X-admin2.2</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <link rel="stylesheet" href="/css/font.css">
    <link rel="stylesheet" href="/css/xadmin.css">
    <script type="text/javascript" src="/js/jquery.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="/lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="/js/xadmin.js"></script>
    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="layui-fluid">
    <div class="layui-row">
        <form class="layui-form" target="_parent" action="/Merchandise.do?action=update&id=${requestScope.merchanById.id}" method="post" onsubmit="return checkEditAll();">
                <div class="layui-form-item" style="display: none">
                <label for="L_email" class="layui-form-label">
                    <span class="x-red">*</span>订单编号</label>
                <div class="layui-input-inline">
                    <input type="text" id="orderNo" name="orderNo" required="" lay-verify="email" autocomplete="off" class="layui-input" value="${requestScope.merchanById.orderNo}">
                </div>
                </div>

            <div class="layui-form-item">
                <label for="L_email" class="layui-form-label">
                    <span class="x-red">*</span>会员姓名</label>
                <div class="layui-input-inline">
                    <input type="text" id="userName" name="userName" required="" lay-verify="email" autocomplete="off" class="layui-input" value="${requestScope.merchanById.memberName}">
                </div>
                <div class="layui-form-mid" id="userNameSpanDiv" style="color: #999999"></div>
            <div class="layui-form-item">
                <label for="L_email" class="layui-form-label">
                    <span class="x-red">*</span>订单类型</label>
                <div class="layui-input-inline">
                    <input type="text" id="orderType" name="orderType" required="" lay-verify="email" autocomplete="off" class="layui-input" value="${requestScope.merchanById.orderType}">
                </div>
                <div class="layui-form-mid" id="orderTypeSpan" style="color: #999999"></div>
            </div>
            <div class="layui-form-item">
                <label for="L_email" class="layui-form-label">
                    <span class="x-red">*</span>总金额</label>
                <div class="layui-input-inline">
                    <input type="text" id="totalAmount" name="totalAmount" required="" lay-verify="email" autocomplete="off" class="layui-input" value="${requestScope.merchanById.totalAmount}">
                </div>
            </div>
            <div class="layui-form-item">
                <label for="L_email" class="layui-form-label">
                    <span class="x-red">*</span>支付状态</label>
                <div class="layui-input-inline">
                    <input type="radio" name="userlei" value="0" title="未支付" ${requestScope.merchanById.paymentStatus == 0?"checked":""}>
                    <input type="radio" name="userlei" value="1" title="已支付" ${requestScope.merchanById.paymentStatus == 1?"checked":""}>
                </div>
            </div>
            <div class="layui-form-item" >
                <label for="L_email" class="layui-form-label">
                    <span class="x-red">*</span>生成时间</label>
                <div class="layui-input-inline">
                    <input type="text" id="createdAt" name="createdAt" required="" lay-verify="email" autocomplete="off" class="layui-input" value="${requestScope.merchanById.createdAt}">
                </div>
            </div>
            <div class="layui-form-item">
                <label for="L_email" class="layui-form-label">
                    <span class="x-red">*</span>支付金额</label>
                <div class="layui-input-inline">
                    <input type="text" id="paymentAmount" name="paymentAmount" required="" lay-verify="email" autocomplete="off" class="layui-input" value="${requestScope.merchanById.paymentAmount}">
                </div>
            </div>
                <div class="layui-form-item">
                    <label for="L_repass" class="layui-form-label"></label>
                    <button class="layui-btn" lay-filter="add" type="submit">修改</button>
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

        });
</script>
<script>var _hmt = _hmt || []; (function() {
    var hm = document.createElement("script");
    hm.src = "https://hm.baidu.com/hm.js?b393d153aeb26b46e9431fabaf0f6190";
    var s = document.getElementsByTagName("script")[0];
    s.parentNode.insertBefore(hm, s);
})();
</script>
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
<script>
    //全局变量
    var checkUserAllNameVal = true;
    //用户姓名
    var userName = document.querySelector("#userName");
    //文本框焦点事件
    userName.onkeyup = function checkUserNameVal(){
        //获取原用户
        var userName = $("#userName").val();
        //验证用户输入的密码是否正确，Jquery版本的Ajax请求
        $.ajax({
            type: "POST",
            url: "/Merchandise.do",
            data: "action=checkUserName&username="+userName,
            dataType:"text",
            success:function (result) {
                if (userName == "") {
                    $("#userNameSpanDiv").text("会员姓名不能为空！");
                    $("#userNameSpanDiv").css("color", "red");
                    checkUserAllNameVal = false;
                }else if(result == 2){
                    $("#userNameSpanDiv").text("会员姓名输入错误!");
                    $("#userNameSpanDiv").css("color","red");
                    checkUserAllNameVal = false;
                } else if(result == 0){
                    $("#userNameSpanDiv").text("√");
                    $("#userNameSpanDiv").css("color","green");
                    checkUserAllNameVal = true;
                }
            }
        });
    }

    //全局变量
    var orderTypeSpanAll = true;
    //订单类型
    var orderType = document.querySelector("#orderType");
    //文本框焦点事件
    orderType.onkeyup = function checkUserNameVal(){
        //获取原用户
        var orderType = $("#orderType").val();
        //验证用户输入的密码是否正确，Jquery版本的Ajax请求
        $.ajax({
            type: "POST",
            url: "/Merchandise.do",
            data: "action=checkOrderType&orderType="+orderType,
            dataType:"text",
            success:function (result) {
                if (orderType == "") {
                    $("#orderTypeSpan").text("会员姓名不能为空！");
                    $("#orderTypeSpan").css("color", "red");
                    orderTypeSpanAll = false;
                }else if(result == 2){
                    $("#orderTypeSpan").text("会员姓名输入错误!");
                    $("#orderTypeSpan").css("color","red");
                    orderTypeSpanAll = false;
                } else if(result == 0){
                    $("#orderTypeSpan").text("√");
                    $("#orderTypeSpan").css("color","green");
                    orderTypeSpanAll = true;
                }
            }
        });
    }
    function checkEditAll(){
        console.log(checkUserAllNameVal);
        console.log(orderTypeSpanAll);
        return checkUserAllNameVal && orderTypeSpanAll;
    }
</script>

