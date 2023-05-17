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
    <script src="/js/jquery.min.js"></script>
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
        <form class="layui-form" action="/BillServlet.do?action=add" method="post">
            <div class="layui-form-item">
                <div class="layui-form-item">
                    <label for="linkman" class="layui-form-label">
                        <span class="x-red">*</span>商品名称
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" id="linkman" name="title" required="" lay-verify="pass" autocomplete="off" class="layui-input" onblur="checkName()">
                    </div>
                    <div class="layui-form-mid layui-word-aux">
                        <span class="x-red">*</span>请输入商品名称（长度不超过8个字符）
                    </div>
                </div>
                <div class="layui-form-item">
                    <label for="phone" class="layui-form-label">
                        <span class="x-red">*</span>商品单位
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" id="phone" name="unit" required="" lay-verify="repass" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label for="address" class="layui-form-label">
                        <span class="x-red">*</span>商品数量
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" id="address" name="num" required="" lay-verify="repass" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label for="fax" class="layui-form-label">
                        <span class="x-red">*</span>总金额
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" id="fax" name="money" required="" lay-verify="repass" autocomplete="off" class="layui-input" onblur="checkMoney()">
                    </div>
                    <div class="layui-form-mid layui-word-aux">
                        <span class="x-red">*</span>请输入数字
                    </div>
                </div>
                <div class="layui-form-item">
                    <label for="username" class="layui-form-label">
                        <span class="x-red">*</span>供应商</label>
                    <div class="layui-input-inline" >
                        <select name="providerid" id="username">
                            <option value="-1">----选择供应商----</option>
                        </select>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">单选框</label>
                    <div class="layui-input-block">
                        <input type="radio" name="ispay" value="0" title="未支付" checked>
                        <input type="radio" name="ispay" value="1" title="已支付">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"></label>
                    <button class="layui-btn" lay-filter="add" type="submit" >添加</button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>

</html>

<script type="text/javascript">
    function renderForm(){
        layui.use('form',function () {
            let form = layui.form;
            form.render();
        });
    }

    // 页面加载的时候执行
    window.onload = function (){
        //发送AJAX异步请求去Servlet后台获取供应商下拉框  的数据
        $.get("/BillServlet.do?action=loadSupplier",function (result){
            for (var i = 0;i<result.length;i++){
                var id = result[i].id;
                var name = result[i].name;
                //绑定供应商下拉框
                //.append()：追加
                //正常来说这样去绑定就可以了
                $("#username").append("<option value='"+id+"'>"+name+"</option>");
                //在这个后台管理系统中会自动生成一个列表，所以需要获取这个列表的class再往里面填冲数据
                // $(".layui-anim").append("<dd lay-value='"+name+"' class=''>"+name+"</dd>");
                renderForm();
            }
        },"json");//返回JSON数据

        // 商品名称输入框外判断输入内容必须为中文，长度不能大于八个字符，超出给提示
        $("#linkman").blur(function() {
            var name = $(this).val();
            if (!/^[\u4e00-\u9fa5]{1,8}$/.test(name)) {
                layer.msg('请输入中文字符不能超过8个');
                $(this).val("");
            }
        });
        // 总金额输入框外判断必须输入数字，字母和汉字不行
        $("#fax").blur(function() {
            var money = $(this).val();
            if (!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(money)) {
                layer.msg('请输入数字');
                $(this).val("");
            }
        });



    }
</script>