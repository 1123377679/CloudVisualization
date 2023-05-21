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
        <form class="layui-form" target="_parent" action="/MemberServlet.do?action=update&id=${requestScope.userById.id}" method="post" onsubmit="return checkEditAll();">
            <div class="layui-form-item">
                <label for="L_email" class="layui-form-label">
                    <span class="x-red">*</span>用户姓名</label>
                <div class="layui-input-inline">
                    <input type="text" id="userName" name="userName" required="" lay-verify="email" autocomplete="off" class="layui-input" value="${requestScope.userById.username}">
                </div>
                <div class="layui-form-mid" id="userNameSpanDiv" style="color: #999999">将会成为您唯一的登录名</div>
                <div class="layui-form-item" style="display: none">
                    <label for="L_pass" class="layui-form-label">
                        <span class="x-red">*</span>密码</label>
                    <div class="layui-input-inline">
                        <input type="text" id="L_pass" name="oldpassword" required="" lay-verify="pass" autocomplete="off" class="layui-input" value="${requestScope.userById.password}"></div>
                    <div class="layui-form-mid layui-word-aux">6到16个字符</div></div>
                <%--            <div class="layui-form-item">--%>
                <%--                <label for="L_repass" class="layui-form-label">--%>
                <%--                    <span class="x-red">*</span>确认密码</label>--%>
                <%--                <div class="layui-input-inline">--%>
                <%--                    <input type="password" id="L_repass" name="newpassword" required="" lay-verify="repass" autocomplete="off" class="layui-input"></div>--%>
                <%--            </div>--%>
                <div class="layui-form-item">
                    <label class="layui-form-label">用户性别</label>
                    <div class="layui-input-block">
                        <input type="radio" name="sex" value="1" title="男" ${requestScope.userById.sex == 1?"checked":""}>
                        <input type="radio" name="sex" value="0" title="女" ${requestScope.userById.sex == 0?"checked":""}>
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">生日日期</label>
                        <div class="layui-input-inline">
                            <input type="text" name="birthday" id="date" lay-verify="date" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input" value="${requestScope.userById.birthday}"></div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label for="L_repass" class="layui-form-label">
                        <span class="x-red">*</span>用户电话</label>
                    <div class="layui-input-inline">
                        <input type="text" id="userphone" name="userphone" required="" lay-verify="repass" autocomplete="off" class="layui-input" value="${requestScope.userById.phone}"></div>
                    <div class="layui-form-mid" id="userNamePhoneSpan" style="color: #999999">国内手机号码/座机号码/短号号码</div>
                </div>
                <div class="layui-form-item">
                    <label for="L_repass" class="layui-form-label">
                        <span class="x-red">*</span>用户地址</label>
                    <div class="layui-input-inline">
                        <input type="text" id="userAddress" name="userAddress" required="" lay-verify="repass" autocomplete="off" class="layui-input" value="${requestScope.userById.address}"></div>
                    <div class="layui-form-mid" id="userAddressSPAN" style="color: #999999"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">用户类别</label>
                    <div class="layui-input-block">
                        <input type="radio" name="userlei" value="1" title="管理员" ${requestScope.userById.type == 1?"checked":""}>
                        <input type="radio" name="userlei" value="2" title="经理" ${requestScope.userById.type == 2?"checked":""}>
                        <input type="radio" name="userlei" value="3" title="普通用户" ${requestScope.userById.type == 3?"checked":""}>
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
<div class="translateSelectLanguage" id="translate"></div>
</body>
</html>
<!-- 引入多语言切换的js -->
<script src="https://res.zvo.cn/translate/translate.js"></script>
<script>
    /*
  * 是否显示 select选择语言的选择框，true显示； false不显示。默认为true
  * 注意,这行要放到 translate.execute(); 上面
  */

    translate.selectLanguageTag.show = true;
    translate.setUseVersion2(); // 这里使用自己的版本
    // translate.setAutoDiscriminateLocalLanguage('chinese_simplified'); // 自动切换国际化语言
    // translate.ignore.class.push('text-one');
    // translate.ignore.class.push('echars-login');
    // translate.ignore.class.push('showTime');
    translate.execute();
</script>
<style>
    .translateSelectLanguage{
        position: absolute;
        top: 2%;
        left: 30%;
        text-decoration: none;
        font-size: 14px;
        border: none;
        display: none;
    }
</style>
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
    var userAllAddressSPAN = true;
    //用户地址
    var userAddress = document.querySelector('#userAddress');
    userAddress.onkeyup = function checkerNewPassword() {
        var userAddress = $("#userAddress").val();
        if (userAddress == "") {
            $("#userAddressSPAN").text("用户地址不能为空!");
            $("#userAddressSPAN").css("color", "red");
            userAllAddressSPAN = false;
        } else {
            $("#userAddressSPAN").text("√");
            $("#userAddressSPAN").css("color", "green");
            userAllAddressSPAN = true;
        }
    }

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
            url: "/MemberServlet.do",
            data: "action=checkUserName&username="+userName,
            dataType:"text",
            success:function (result) {
                if (result == 1) {
                    $("#userNameSpanDiv").text("用户名已存在!");
                    $("#userNameSpanDiv").css("color","red");
                    checkUserAllNameVal = false;
                } else if (userName == "") {
                    $("#userNameSpanDiv").text("用户姓名不能为空！");
                    $("#userNameSpanDiv").css("color", "red");
                    checkUserAllNameVal = false;
                } else if(result == 0){
                    $("#userNameSpanDiv").text("√");
                    $("#userNameSpanDiv").css("color","green");
                    checkUserAllNameVal = true;
                }else if(result == 2){
                    $("#userNameSpanDiv").text("用户姓名输入错误!");
                    $("#userNameSpanDiv").css("color","red");
                    checkUserAllNameVal = false;
                }
            }
        });
    }

    //全局变量
    var checkUserAllPhone = true;
    //电话号码
    var userphone = document.querySelector("#userphone");
    //文本框焦点事件
    userphone.onkeyup = function checkUserPhone(){
        //获取原用户
        var userphone = $("#userphone").val();
        //验证用户输入的密码是否正确，Jquery版本的Ajax请求
        $.ajax({
            type: "POST",
            url: "/MemberServlet.do",
            data: "action=checkUserPhone&userphone="+userphone,
            dataType:"text",
            success:function (result) {
                if (userphone == "") {
                    $("#userNamePhoneSpan").text("用户电话号码不能为空！");
                    $("#userNamePhoneSpan").css("color", "red");
                    checkUserAllPhone = false;
                } else if(result == 0){
                    $("#userNamePhoneSpan").text("√");
                    $("#userNamePhoneSpan").css("color","green");
                    checkUserAllPhone = true;
                }else if(result == 2){
                    $("#userNamePhoneSpan").text("电话号码位6到11位的数字！");
                    $("#userNamePhoneSpan").css("color","red");
                    checkUserAllPhone = false;
                }
            }
        });
    }
    function checkEditAll(){
        console.log(checkUserAllNameVal);
        console.log(checkUserAllPhone);
        console.log(userAllAddressSPAN);
        return userAllAddressSPAN&&checkUserAllNameVal&&checkUserAllPhone;
    }
</script>

