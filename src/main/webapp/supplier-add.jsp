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
<div class="layui-fluid">
    <div class="layui-row">
        <form class="layui-form" action="/SupplierServlet.do?action=add" method="post" onsubmit="return checkSupplierAll()">
            <div class="layui-form-item">
                <label for="name" class="layui-form-label">
                    <span class="x-red">*</span>供应商名称</label>
                <div class="layui-input-inline">
                    <input type="text" id="name" name="name" required="" lay-verify="email" autocomplete="off" class="layui-input">
                </div>
                <div class="layui-form-mid" id="checkSuppliername" style="color:#999999">将会成为你唯一的登录名</div>
                <div class="layui-form-item">
                    <label for="linkman" class="layui-form-label">
                        <span class="x-red">*</span>联系人
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" id="linkman" name="linkman" required="" lay-verify="pass" autocomplete="off" class="layui-input">
                    </div>
                    <div class="layui-form-mid" id="checkSupplierlinkman" style="color:#999999">请输入联系人</div>
                </div>
                <div class="layui-form-item">
                    <label for="phone" class="layui-form-label">
                        <span class="x-red">*</span>联系电话
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" id="phone" name="phone" required="" lay-verify="repass" autocomplete="off" class="layui-input">
                    </div>
                    <div class="layui-form-mid" id="checkSupplierphone" style="color:#999999">11位的阿拉伯数字</div>
                </div>
                <div class="layui-form-item">
                    <label for="address" class="layui-form-label">
                        <span class="x-red">*</span>联系地址
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" id="address" name="address" required="" lay-verify="repass" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label for="fax" class="layui-form-label">
                        <span class="x-red">*</span>传真
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" id="fax" name="fax" required="" lay-verify="repass" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label for="description" class="layui-form-label">
                        <span class="x-red">*</span>描述</label>
                    <div class="layui-input-inline">
                        <input type="text" id="description" name="description" required="" lay-verify="repass" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"></label>
                    <button class="layui-btn" lay-filter="add" type="submit">添加</button>
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
<div class="translateSelectLanguage" id="translate"></div>
</body>
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
    var checkSupplierName=true;
    //获取输入框
    var Name=document.querySelector('#name');
    //用户绑定焦点事件
    Name.onkeyup = function checkSuppliername(){
        //获取输入框中的内容
        var suppliername =$("#name").val();
        $.ajax({
            type:"POST",
            url:"/SupplierServlet.do",
            //?????????????data打成date了
            data:"action=checkSupplierName&suppliername="+suppliername,
            dataType:"text",//服务器返回的数据类型
            success:function(result){
                if (result==0){
                    $("#checkSuppliername").text("√");
                    $("#checkSuppliername").css("color","green");
                    checkSupplierPhone =false;

                }else if(suppliername=="") {
                    $("#checkSuppliername").text("不能为空");
                    $("#checkSuppliername").css("color","red");
                    checkSupplierPhone =false;

                }else if (result==1){
                    $("#checkSuppliername").text("已经存在该供应商名称" );
                    $("#checkSuppliername").css("color","red");
                    checkSupplierPhone =true;

                }else if (result==2){
                    $("#checkSuppliername").text("供应商名称必须为中文");
                    $("#checkSuppliername").css("color","red");
                    checkSupplierPhone =false;

                }

            }
        })
    }
    var checkSupplierLinkman=true;
    //获取输入框
    var Linkman=document.querySelector('#linkman');
    //用户绑定焦点事件
    Linkman.onkeyup = function checkSupplierlinkman(){
        //获取输入框中的内容
        var supplierlinkman =$("#linkman").val();
        $.ajax({
            type:"POST",
            url:"/SupplierServlet.do",
            //?????????????data打成date了
            data:"action=checkSupplierLinkman&supplierlinkman="+supplierlinkman,
            dataType:"text",//服务器返回的数据类型
            success:function(result){
                if (  supplierlinkman==""){
                    $("#checkSupplierlinkman").text("不能为空");
                    $("#checkSupplierlinkman").css("color","red");
                    checkSupplierPhone =false;
                }else if(result==0) {
                    $("#checkSupplierlinkman").text("√");
                    $("#checkSupplierlinkman").css("color","green");
                    checkSupplierPhone =true;
                } else if (result==2){
                    $("#checkSupplierlinkman").text("联系人姓名必须为中文");
                    $("#checkSupplierlinkman").css("color","red");
                    checkSupplierPhone =false;

                }

            }
        })
    }
    //电话号码
    var checkSupplierPhone =true;
    var Phone=document.querySelector('#phone');
    //用户绑定焦点事件
    Phone.onkeyup = function checkSupplierphone(){
        //获取输入框中的内容
        var supplierphone =$("#phone").val();
        $.ajax({
            type:"POST",
            url:"/SupplierServlet.do",
            //?????????????data打成date了
            data:"action=checkSupplierPhone&supplierphone="+supplierphone,
            dataType:"text",//服务器返回的数据类型
            success:function(result){
                if (result==1){
                    $("#checkSupplierphone").text("√" );
                    $("#checkSupplierphone").css("color","green");
                    checkSupplierPhone =true;

                }else if (supplierphone==""){
                    $("#checkSupplierphone").text("不能为空");
                    $("#checkSupplierphone").css("color","red");
                    checkSupplierPhone =false;
                }
                else if(result==0) {
                    $("#checkSupplierphone").text("电话为短号/座机号/11位号码的数字");
                    $("#checkSupplierphone").css("color","red");
                    checkSupplierPhone =false;

                }
            }
        })
    }
    function  checkSupplierAll (){
        console.log(checkSupplierName);
        console.log(checkSupplierLinkman);
        console.log(checkSupplierPhone);
        return checkSupplierName&&checkSupplierLinkman&&checkSupplierPhone;
    }
</script>
