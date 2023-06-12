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
    <style>
        canvas {
            position: absolute;
            top: 0px;
            left: 0px;
            opacity: 0.5;
            width: 100%;
            height: 100%;
            z-index: -100;
        }

        .codrops-demos {
            font-size: 0.8em;
            text-align:center;
            position:absolute;
            z-index:99;
            width:96%;
        }

        .codrops-demos a {
            display: inline-block;
            margin: 0.35em 0.1em;
            padding: 0.5em 1.2em;
            outline: none;
            text-decoration: none;
            text-transform: uppercase;
            letter-spacing: 1px;
            font-weight: 700;
            border-radius: 2px;
            font-size: 110%;
            border: 2px solid transparent;
            color:#fff;
        }

        .codrops-demos a:hover,
        .codrops-demos a.current-demo {
            border-color: #383a3c;
        }
        .layui-input-block {
            margin-left: 39px;
            margin-top: -7px;
            min-height: 37px;
        }
        .layui-form-label {
            float: left;
            display: block;
            /* padding: 9px 14px; */
            margin-left: -66px;
            margin-top: -8px;
            width: 80px;
            font-weight: 400;
            line-height: 20px;
            text-align: right;
        }
    </style>
</head>
<body class="login-bg">
<canvas id="canvas"></canvas>
<div class="login layui-anim layui-anim-up" style="position: fixed;top: -7%;bottom: 7%;left: 35%;;transform: translateX(-40%);max-width: 34%;">
    <div class="message">超市管理系统用户注册</div>
    <div id="darkbannerwrap"></div>

    <form method="post" class="layui-form" action="/userServlet.do?action=register">
        <label class="layui-form-label">用户名:</label>
        <input id="username" name="username" placeholder="用户名"  type="text" lay-verify="required" class="layui-input" style="height: 34px;width: 245px;display: inline-block;margin-top: -6px;"><span id="usernameSpan"></span>
        <hr class="hr15">
        <label class="layui-form-label">密码:</label>
        <input id="password" name="oncePassword" lay-verify="required" placeholder="密码"  type="password" class="layui-input" style="height: 34px;width: 245px;display: inline-block;margin-top: -6px;"><span id="passwordSpan"></span>
        <hr class="hr15">
        <label class="layui-form-label">确认密码:</label>
        <input id="twicePassword" name="twicePassword" lay-verify="required" placeholder="再次输入密码"  type="password" class="layui-input" style="height: 34px;width: 245px;display: inline-block;margin-top: -6px;"><span id="twiceSpan"></span>
        <hr class="hr15">
        <div class="layui-form-item">
            <label class="layui-form-label"style="float: left;display: block;margin-left: -66px;margin-top: -2px;width: 80px;font-weight: 400;line-height: 20px;text-align: right;">生日:</label>
            <div class="layui-input-block" style="margin-left: 0px;min-height: 0px;">
                <input id="birthday" name="birthday" placeholder="生日" type="text" class="layui-input" lay-verify="required" style="height: 34px;width: 245px;margin-top: -6px;">
            </div>
        </div>
        <label class="layui-form-label">联系电话:</label>
        <input id="phone" name="phone" lay-verify="required" placeholder="联系电话"  type="text" class="layui-input" style="height: 34px;width: 245px;display: inline-block;margin-top: -6px;"><span id="phoneSpan"></span>
        <hr class="hr15">
        <label class="layui-form-label">家庭住址:</label>
        <input id="address" name="address" lay-verify="required" placeholder="家庭住址"  type="text" class="layui-input" style="height: 34px;width: 245px;display: inline-block;margin-top: -6px;"><span id="addressSpan"></span>
        <hr class="hr15">
        <label class="layui-form-label">性别:</label>
        <div class="layui-input-block" style="height: 34px;">
            <input type="radio" name="sex" value="男" title="男" checked="">
            <input type="radio" name="sex" value="女" title="女">
        </div>

        <%--  验证码  --%>
        <label class="layui-form-label" style="float: left;display: block;margin-left: -66px;margin-top: 5px;width: 80px;font-weight: 400;line-height: 20px;text-align: right;">验证码:</label>
        <input id="yanzhengm" style="width: 131px;margin-left: 0px" type="text" name="usercode" placeholder="请输入验证码" required/>
        <!--验证码图片-->
        <img src="/CodeServlet" onclick="changeImage(this);" style="position: relative;cursor: pointer; width: 156px"/>
        <input value="注册" lay-submit lay-filter="login" style="width:43%;margin-top: 15px" type="submit">
        <input value="返回登录" style="width:44%;margin-top: 15px;margin-left: 29px;" type="button" onclick="location.href='/login.jsp';">
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
    layui.use('form', function(){
        var form = layui.form;
        var birthdayInput = document.getElementById('birthday');
        // 初始化生日输入框的 laydate 组件
        layui.use('laydate', function() {
            var laydate = layui.laydate;
            laydate.render({
                elem: '#birthday',
                position: 'fixed' // 设置弹层固定位置
            });
            laydate.render({
                elem: birthdayInput
            });
        });
    });
    function changeImage(img) {
        //图片重新加载src地址，因为图片是一个GET请求，浏览器有缓存  time 表示是一个随机参数 ，防止浏览器缓存
        img.src="/CodeServlet?time="+new Date();
    }
    //校验用户名
    var checkUsername = false;
    var RegisterUsername = document.querySelector("#username");
    //焦点事件
    RegisterUsername.onkeyup = function checkRegisterUsername(){
        //用户输入的用户名
        var username = $("#username").val();
        //验证用户输入的密码是否匹配
        $.ajax({
            type: "POST",
            url: "/MemberServlet.do",
            data: "action=checkUserName&username="+username,
            dataType:"text",
            success:function (result) {
                if (result == 1) {
                    $("#usernameSpan").text("用户名已存在!");
                    $("#usernameSpan").css("color","red");
                    checkUsername = false;
                } else if (username == "") {
                    $("#usernameSpan").text("姓名不能为空！");
                    $("#usernameSpan").css("color", "red");
                    checkUsername = false;
                } else if(result == 0){
                    $("#usernameSpan").text("√");
                    $("#usernameSpan").css("color","green");
                    checkUsername = true;
                }else if(result == 2){
                    $("#usernameSpan").text("用户姓名输入错误!");
                    $("#usernameSpan").css("color","red");
                    checkUsername = false;
                }
            }
        });
    }
    //校验密码
    var checkPassword = false;
    var RegisterPassword = document.querySelector("#password");
    RegisterPassword.onkeyup = function checkRegisterPassword(){
        //用户输入的密码
        var password = $("#password").val();
        //验证用户输入的密码是否正确，Jquery版本的Ajax请求
        $.ajax({
            type: "POST",
            url: "/MemberServlet.do",
            data: "action=checkAllWord&newPassword="+password,
            dataType: "text",
            success: function (result) {
                if (password == "") {
                    $("#passwordSpan").text("新的密码不能为空!");
                    $("#passwordSpan").css("color", "red");
                    checkPassword = false;
                } else if (result == 1) {
                    $("#passwordSpan").text("√");
                    $("#passwordSpan").css("color", "green");
                    checkPassword = true;
                } else if (result == 2) {
                    $("#passwordSpan").text("6到11位字符加数字!");
                    $("#passwordSpan").css("color", "red");
                    checkPassword = false;
                }
            }
        });
    }
    //校验确认密码框
    var checktwicePassword = false;
    var RegistertwicePassword = document.querySelector("#twicePassword");
    RegistertwicePassword.onkeyup = function RegistertwicePassword(){
        //用户输入的密码
        let password = $("#password").val();
        let twicePassword = $("#twicePassword").val();
        if (twicePassword == ""){
            $("#twiceSpan").text("不能为空!");
            $("#twiceSpan").css("color","red");
            checktwicePassword = false;
        } else if (password != twicePassword) {
            $("#twiceSpan").text("密码输入不相同!");
            $("#twiceSpan").css("color", "red");
            checktwicePassword = false;
        }  else {
            $("#twiceSpan").text("√");
            $("#twiceSpan").css("color", "green");
            checktwicePassword = true;
        }
    }
    //校验电话
    var checkPhone = false;
    var RegisterPhone = document.querySelector("#phone");
    RegisterPhone.onkeyup = function RegisterPhone(){
        //用户输入的电话
        let number = $("#phone").val();
        $.ajax({
            type: "POST",
            url: "/MemberServlet.do",
            data: "action=checkUserPhone&userphone="+number,
            dataType:"text",
            success:function (result) {
                if (number == "") {
                    $("#phoneSpan").text("不能为空！");
                    $("#phoneSpan").css("color", "red");
                    checkPhone = false;
                } else if(result == 0){
                    $("#phoneSpan").text("√");
                    $("#phoneSpan").css("color","green");
                    checkPhone = true;
                }else if(result == 2){
                    $("#phoneSpan").text("6到11位的数字！");
                    $("#phoneSpan").css("color","red");
                    checkPhone = false;
                }
            }
        });
    }
    //校验用户地址
    var checkAddress = false;
    var RegisterAddress = document.querySelector("#address");
    RegisterAddress.onkeyup = function RegisterAddress(){
        //用户输入的地址
        let address = $("#address").val().trim();
        var addressReg = /^[\u4e00-\u9fa5]{0,8}$/; // 检查是否为纯中文字符的正则表达式
        if (address == ""){
            $("#addressSpan").text("不能为空！");
            $("#addressSpan").css("color", "red");
            checkAddress = false;
        }else if (!addressReg.test(address)){
            $("#addressSpan").text("地址需要中文!");
            $("#addressSpan").css("color", "red");
            checkAddress = false;
        }else {
            $("#addressSpan").text("√");
            $("#addressSpan").css("color","green");
            checkAddress = true;
        }
    }
</script>
<script>
    "use strict";
    var canvas = document.getElementById('canvas'),
        ctx = canvas.getContext('2d'),
        w = canvas.width = window.innerWidth,
        h = canvas.height = window.innerHeight,

        hue = 217,
        stars = [],
        count = 0,
        maxStars = 1200;

    var canvas2 = document.createElement('canvas'),
        ctx2 = canvas2.getContext('2d');
    canvas2.width = 100;
    canvas2.height = 100;
    var half = canvas2.width / 2,
        gradient2 = ctx2.createRadialGradient(half, half, 0, half, half, half);
    gradient2.addColorStop(0.025, '#fff');
    gradient2.addColorStop(0.1, 'hsl(' + hue + ', 61%, 33%)');
    gradient2.addColorStop(0.25, 'hsl(' + hue + ', 64%, 6%)');
    gradient2.addColorStop(1, 'transparent');

    ctx2.fillStyle = gradient2;
    ctx2.beginPath();
    ctx2.arc(half, half, half, 0, Math.PI * 2);
    ctx2.fill();

    // End cache

    function random(min, max) {
        if (arguments.length < 2) {
            max = min;
            min = 0;
        }

        if (min > max) {
            var hold = max;
            max = min;
            min = hold;
        }

        return Math.floor(Math.random() * (max - min + 1)) + min;
    }

    function maxOrbit(x, y) {
        var max = Math.max(x, y),
            diameter = Math.round(Math.sqrt(max * max + max * max));
        return diameter / 2;
    }

    var Star = function() {

        this.orbitRadius = random(maxOrbit(w, h));
        this.radius = random(60, this.orbitRadius) / 12;
        this.orbitX = w / 2;
        this.orbitY = h / 2;
        this.timePassed = random(0, maxStars);
        this.speed = random(this.orbitRadius) / 900000;
        this.alpha = random(2, 10) / 10;

        count++;
        stars[count] = this;
    }

    Star.prototype.draw = function() {
        var x = Math.sin(this.timePassed) * this.orbitRadius + this.orbitX,
            y = Math.cos(this.timePassed) * this.orbitRadius + this.orbitY,
            twinkle = random(10);

        if (twinkle === 1 && this.alpha > 0) {
            this.alpha -= 0.05;
        } else if (twinkle === 2 && this.alpha < 1) {
            this.alpha += 0.05;
        }

        ctx.globalAlpha = this.alpha;
        ctx.drawImage(canvas2, x - this.radius / 2, y - this.radius / 2, this.radius, this.radius);
        this.timePassed += this.speed;
    }

    for (var i = 0; i < maxStars; i++) {
        new Star();
    }

    function animation() {
        ctx.globalCompositeOperation = 'source-over';
        ctx.globalAlpha = 0.8;
        ctx.fillStyle = 'hsla(' + hue + ', 64%, 6%, 1)';
        ctx.fillRect(0, 0, w, h)

        ctx.globalCompositeOperation = 'lighter';
        for (var i = 1, l = stars.length; i < l; i++) {
            stars[i].draw();
        };

        window.requestAnimationFrame(animation);
    }

    animation();
</script>