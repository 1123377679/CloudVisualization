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
    <![endif]--></head>

<body>
<div class="layui-fluid">
    <div class="layui-row">
        <form class="layui-form" action="/MemberServlet.do?action=updatepwd&id=${requestScope.userById.id}" method="post" onsubmit="return checkPassAll();">
            <div class="layui-form-item">
                <label for="L_repass" class="layui-form-label">
                    <span class="x-red">*</span>旧的密码</label>
                <div class="layui-input-inline">
                    <input type="text" id="oldPassword" name="oldpass" required="" lay-verify="required" autocomplete="off" class="layui-input">
                </div>
                <%--            ----------------------------------------------------------%>
                <div class="layui-form-mid" id="oldPasswordSpan"></div>
            </div>
            <div class="layui-form-item">
                <label for="L_pass" class="layui-form-label">
                    <span class="x-red">*</span>新的密码</label>
                <div class="layui-input-inline">
                    <input type="text" id="newPassword" name="newPassword" required="" lay-verify="required" autocomplete="off" class="layui-input"></div>
                <%--                ------------------------------------------------%>
                <div class="layui-form-mid" id="newPasswordSpan" style="color: #999999">6到16个字符</div>
                <div class="layui-form-item">
                    <label for="L_repass" class="layui-form-label">
                        <span class="x-red">*</span>确认密码</label>
                    <div class="layui-input-inline">
                        <input type="text" id="confirmPassword" name="repass" required="" lay-verify="required" autocomplete="off" class="layui-input"></div>
                    <%--                     ----------------------------------------------------%>
                    <div class="layui-form-mid" id="reconfirmPassword"></div>
                </div>
                <div class="layui-form-item">
                    <label for="L_repass" class="layui-form-label"></label>
                    <button class="layui-btn" lay-filter="save" lay-submit="" type="submit">确认修改</button></div>
        </form>
    </div>
    <div class="translateSelectLanguage" id="translate"></div>
</div>
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
<script>layui.use(['form', 'layer'],
    function() {
        $ = layui.jquery;
        var form = layui.form,
            layer = layui.layer;
    });
</script>
<script>var _hmt = _hmt || []; (function() {
    var hm = document.createElement("script");
    hm.src = "https://hm.baidu.com/hm.js?b393d153aeb26b46e9431fabaf0f6190";
    var s = document.getElementsByTagName("script")[0];
    s.parentNode.insertBefore(hm, s);
})();
</script>

<script>
    //全局变量
    var checkOldAllPassword = false;
    //检查原密码合法性
    var oldPassword = document.querySelector('#oldPassword');
    //文本框焦点事件
    oldPassword.onkeyup = function checkOldPassword(){
        //获取原密码
        var oldPassword = $("#oldPassword").val();
        //验证用户输入的密码是否正确，Jquery版本的Ajax请求
        $.ajax({
            type:"POST",
            url:"/MemberServlet.do",
            data:"action=checkOldPass&oldPassword="+oldPassword+"&id=${requestScope.userById.id}",
            <%--data:"action=checkOldPass&id="+${requestScope.userById.id}+"&oldPassword="+oldPassword,--%>
            dataType:"text",//服务器响应数据的格式
            //result:后端响应的数据
            success:function (result){
                // alert("服务器响应的数据:"+result);
                //根据后端响应的数据进行判断
                if (result == 1){
                    $("#oldPasswordSpan").text("√");
                    $("#oldPasswordSpan").css("color","green");
                    checkOldAllPassword = true;
                } else  if (oldPassword == ""){
                    $("#oldPasswordSpan").text("旧的密码不能为空!");
                    $("#oldPasswordSpan").css("color","red");
                    checkOldAllPassword = false;
                } else {
                    $("#oldPasswordSpan").text("旧的密码填写错误!");
                    $("#oldPasswordSpan").css("color","red");
                    checkOldAllPassword = false;
                }
            }
        });
    }

    //全局变量
    var checkNewAllPassword = false;
    //新密码框
    var newPassword = document.querySelector('#newPassword');
    //鼠标离开焦点事件
    newPassword.onkeyup = function checkNewPassword(){
        //原始密码
        var oldPassword = $("#oldPassword").val();
        //新密码
        var newPassword = $("#newPassword").val();
        //验证用户输入的密码是否正确，Jquery版本的Ajax请求
        $.ajax({
            type: "POST",
            url: "/MemberServlet.do",
            data: "action=checkAllWord&newPassword="+newPassword,
            dataType: "text",
            success: function (result) {
                if (newPassword == "") {
                    $("#newPasswordSpan").text("新的密码不能为空!");
                    $("#newPasswordSpan").css("color", "red");
                    checkNewAllPassword = false;
                } else if (newPassword == oldPassword) {
                    $("#newPasswordSpan").text("新密码不能和旧密码相同!");
                    $("#newPasswordSpan").css("color", "red");
                    checkNewAllPassword = false;
                } else if (result == 1) {
                    $("#newPasswordSpan").text("√");
                    $("#newPasswordSpan").css("color", "green");
                    checkNewAllPassword = true;
                } else if (result == 2) {
                    $("#newPasswordSpan").text("密码为6到11位字符串加数字!");
                    $("#newPasswordSpan").css("color", "red");
                    checkNewAllPassword = false;
                }
            }
        });
    }

    //全局变量
    var checkerNewAllPassword = false;
    //确认新密码框
    var confirmPassword = document.querySelector('#confirmPassword');
    confirmPassword.onkeyup = function checkerNewPassword() {
        //新密码
        var newPassword = $("#newPassword").val();
        //确认新密码
        var confirmPassword = $("#confirmPassword").val();
        if (confirmPassword == ""){
            $("#reconfirmPassword").text("确认密码不能为空!");
            $("#reconfirmPassword").css("color","red");
            checkerNewAllPassword = false;
        } else if (newPassword != confirmPassword) {
            $("#reconfirmPassword").text("两次密码输入不相同!");
            $("#reconfirmPassword").css("color", "red");
            checkerNewAllPassword = false;
        }  else {
            $("#reconfirmPassword").text("√");
            $("#reconfirmPassword").css("color", "green");
            checkerNewAllPassword = true;
        }
    }

    function checkPassAll(){
        console.log(checkOldAllPassword);
        console.log(checkNewAllPassword);
        console.log(checkerNewAllPassword);
        return checkOldAllPassword && checkNewAllPassword && checkerNewAllPassword;
    }
</script>
</body>

</html>
