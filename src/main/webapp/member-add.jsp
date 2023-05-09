<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <form class="layui-form" method="post" action="/MemberServlet.do?action=add" onsubmit="return checkAddAll();">
            <div class="layui-form-item">
                <label for="L_email" class="layui-form-label">
                    <span class="x-red">*</span>用户姓名</label>
                <div class="layui-input-inline">
                    <input type="text" id="username" name="username" required="" lay-verify="email" autocomplete="off" class="layui-input"></div>
                <%------------  --   --- ---------------------- ----------------------     ------        --%>
                <div class="layui-form-mid" id="userNameSpan" style="color: #999999">将会成为您唯一的登录名</div>
            </div>
            <div class="layui-form-item">
                <label for="L_pass" class="layui-form-label">
                    <span class="x-red">*</span>用户密码</label>
                <div class="layui-input-inline">
                    <input type="password" id="oldpassword" name="oldpassword" required="" lay-verify="pass" autocomplete="off" class="layui-input"></div>
                <%------------  --   --- ---------------------- ----------------------     ------        --%>
                <div class="layui-form-mid" id="userNameWord" style="color: #999999">6到16个字符</div>
            <div class="layui-form-item">
                <label for="L_repass" class="layui-form-label">
                    <span class="x-red">*</span>确认密码</label>
                <div class="layui-input-inline">
                    <input type="password" id="newpassword" name="newpassword" required="" lay-verify="repass" autocomplete="off" class="layui-input"></div>
                <%------------  --   --- ---------------------- ----------------------     ------        --%>
                <div class="layui-form-mid" id="userNameWordSpan" style="color: #999999">6到16个字符</div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">用户性别</label>
                <div class="layui-input-block">
                    <input type="radio" name="sex" value="1" title="男" checked="">
                    <input type="radio" name="sex" value="0" title="女">
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">生日日期</label>
                    <div class="layui-input-inline">
                        <input type="date" name="birthday" id="date" lay-verify="date" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <label for="L_repass" class="layui-form-label">
                    <span class="x-red">*</span>用户电话</label>
                <div class="layui-input-inline">
                    <input type="text" id="userphone" name="userphone" required="" lay-verify="repass" autocomplete="off" class="layui-input"></div>
                <%------------  --   --- ---------------------- ----------------------     ------        --%>
                <div class="layui-form-mid" id="userNamePhone" style="color: #999999">11位数字</div>
            </div>
            <div class="layui-form-item">
                <label for="L_repass" class="layui-form-label">
                    <span class="x-red">*</span>用户地址</label>
                <div class="layui-input-inline">
                    <input type="text" id="userAddress" name="userAddress" required="" lay-verify="repass" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">用户类别</label>
                <div class="layui-input-block">
                    <input type="radio" name="userlei" value="1" title="管理员" checked="">
                    <input type="radio" name="userlei" value="2" title="经理">
                    <input type="radio" name="userlei" value="3" title="普通用户">
                </div>
            </div>
            <div class="layui-form-item">
                <label for="L_repass" class="layui-form-label"></label>
                <button class="layui-btn" lay-filter="add" type="submit">点击添加</button>
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
</body>
</html>
<script>
    //全局变量
    var checkAllUserName = false;
    //用户姓名
    var username = document.querySelector("#username");
    //文本框焦点事件
    username.onblur = function checkUserName(){
        //获取原用户
        var username = $("#username").val();
        //验证用户输入的密码是否正确，Jquery版本的Ajax请求
        $.ajax({
            type: "POST",
            url: "/MemberServlet.do",
            data: "action=checkUserName&username="+username,
            dataType:"text",
            success:function (result) {
                if (result == 1) {
                    $("#userNameSpan").text("用户姓名已存在!");
                    $("#userNameSpan").css("color","red");
                    checkAllUserName = false;
                } else if (username == "") {
                    $("#userNameSpan").text("用户姓名不能为空！");
                    $("#userNameSpan").css("color", "red");
                    checkAllUserName = false;
                } else if(result == 0){
                    $("#userNameSpan").text("√");
                    $("#userNameSpan").css("color","green");
                    checkAllUserName = true;
                }else if(result == 2){
                    $("#userNameSpan").text("用户姓名必须是中文!");
                    $("#userNameSpan").css("color","red");
                    checkAllUserName = false;
                }
            }
        });
    }

    //全局变量
    var checkAllUserWord = false;
    //用户密码
    var oldpassword = document.querySelector("#oldpassword");
    //文本框焦点事件
    oldpassword.onblur = function checkUserWord(){
        //获取原用户
        var oldpassword = $("#oldpassword").val();
        //验证用户输入的密码是否正确，Jquery版本的Ajax请求
        $.ajax({
            type: "POST",
            url: "/MemberServlet.do",
            data: "action=checkUserWord&oldpassword="+oldpassword,
            dataType:"text",
            success:function (result) {
                if (oldpassword == "") {
                    $("#userNameWord").text("用户密码不能为空！");
                    $("#userNameWord").css("color", "red");
                    checkAllUserWord = false;
                } else if(result == 0){
                    $("#userNameWord").text("√");
                    $("#userNameWord").css("color","green");
                    checkAllUserWord = true;
                }else if(result == 2){
                    $("#userNameWord").text("用户密码必须是6到16个字符!");
                    $("#userNameWord").css("color","red");
                    checkAllUserWord = false;
                }
            }
        });
    }

    //全局变量
    var checkerAllNewPassword = false;
    //确认密码
    var newpassword = document.querySelector('#newpassword');
    newpassword.onblur = function checkerNewPassword() {
        //用户密码
        var oldpassword = $("#oldpassword").val();
        //确认密码
        var newpassword = $("#newpassword").val();
        if (newpassword == ""){
            $("#userNameWordSpan").text("确认密码不能为空!");
            $("#userNameWordSpan").css("color","red");
            checkerAllNewPassword = false;
        } else if (oldpassword != newpassword) {
            $("#userNameWordSpan").text("两次密码输入不相同!");
            $("#userNameWordSpan").css("color", "red");
            checkerAllNewPassword = false;
        }  else {
            $("#userNameWordSpan").text("√");
            $("#userNameWordSpan").css("color", "green");
            checkerAllNewPassword = true;
        }
    }

    //全局变量
    var checkAllUserPhone = false;
    //电话号码
    var userphone = document.querySelector("#userphone");
    //文本框焦点事件
    userphone.onblur = function checkUserPhone(){
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
                    $("#userNamePhone").text("用户电话不能为空！");
                    $("#userNamePhone").css("color", "red");
                     checkAllUserPhone = false;
                } else if(result == 0){
                    $("#userNamePhone").text("√");
                    $("#userNamePhone").css("color","green");
                     checkAllUserPhone = true;
                }else if(result == 2){
                    $("#userNamePhone").text("用户电话必须是11位的阿拉伯数字！");
                    $("#userNamePhone").css("color","red");
                     checkAllUserPhone = false;
                }
            }
        });
    }

    function checkAddAll() {
        console.log(checkAllUserName);
        console.log(checkAllUserWord);
        console.log(checkerAllNewPassword);
        console.log(checkAllUserPhone);
        return checkAllUserName&&checkAllUserWord&&checkerAllNewPassword&&checkAllUserPhone;
    }
</script>

