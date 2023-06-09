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
        <form class="layui-form" action="/BillServlet.do?action=update&id=${requestScope.bliiById.id}" method="post" onsubmit="return checkAddAll();">
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
                        <div class="layui-form-mid" id="checkBillName" style="color:#999999"></div>
                    </div>
                    <div class="layui-form-item">
                        <label for="unit" class="layui-form-label">
                            <span class="x-red">*</span>账单单位
                        </label>
                        <div class="layui-input-inline">
                            <input type="text" value="${bliiById.unit}" id="unit" name="unit" required="" lay-verify="repass" autocomplete="off" class="layui-input">
                        </div>
                        <div class="layui-form-mid" id="checkBillPhone" style="color:#999999"></div>
                    </div>
                    <div class="layui-form-item">
                        <label for="num" class="layui-form-label">
                            <span class="x-red">*</span>账单数量
                        </label>
                        <div class="layui-input-inline">
                            <input type="text" value="${bliiById.num}" id="num" name="num" required="" lay-verify="repass" autocomplete="off" class="layui-input">
                        </div>
                        <div class="layui-form-mid" id="checkBillNum" style="color:#999999"></div>
                    </div>
                    <div class="layui-form-item">
                        <label for="money" class="layui-form-label">
                            <span class="x-red">*</span>总金额
                        </label>
                        <div class="layui-input-inline">
                            <input type="text" value="${bliiById.money}" id="money" name="money" required="" lay-verify="repass" autocomplete="off" class="layui-input">
                        </div>
                        <div class="layui-form-mid" id="checkBillFax" style="color:#999999"></div>
                    </div>
                    <div class="layui-form-item">
                        <label for="username" class="layui-form-label">
                            <span class="x-red">*</span>供应商</label>
                        <div class="layui-input-inline" >
                            <select name="providerid" id="username">
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

<script>
    var checkBillName = true;
    //获取输入框
    var Name = document.querySelector('#title');
    //用户绑定焦点事件
    Name.onkeyup = function checkName(){
        //获取输入框中的内容
        var billName =$("#title").val();
        $.ajax({
            type:"POST",
            url:"/BillServlet.do",
            data:"action=checkBillName&billName="+billName,
            dataType:"text",//服务器返回的数据类型
            success:function(result){
                if (result==1){
                    $("#checkBillName").text("不能英文且不能超过8个字符！");
                    $("#checkBillName").css("color","red");
                    checkBillName =false;
                }else if(billName=="") {
                    $("#checkBillName").text("不能为空");
                    $("#checkBillName").css("color","red");
                    checkBillName =false;

                }else if (result==0){
                    $("#checkBillName").text("√" );
                    $("#checkBillName").css("color","green");
                    checkBillName =true;
                }
            }
        })
    }

    var checkBillPhone = true;
    //获取输入框
    var Phone = document.querySelector('#unit');
    //用户绑定焦点事件
    Phone.onkeyup = function checkPhone(){
        //获取输入框中的内容
        var billPhone =$("#unit").val();
        console.log(billPhone)
        $.ajax({
            type:"POST",
            url:"/BillServlet.do",
            data:"action=checkBillPhone&billPhone="+billPhone,
            dataType:"text",//服务器返回的数据类型
            success:function(result){
                if(billPhone =="") {
                    $("#checkBillPhone").text("不能为空");
                    $("#checkBillPhone").css("color", "red");
                    checkBillPhone = false;
                }else if (result==1){
                    $("#checkBillPhone").text("不能英文且不能超过1个字符！");
                    $("#checkBillPhone").css("color","red");
                    checkBillPhone =false;
                }else if (result==0){
                    $("#checkBillPhone").text("√" );
                    $("#checkBillPhone").css("color","green");
                    checkBillPhone =true;
                }
            }
        })
    }


    var checkBillNum = true;
    //获取输入框
    var address = document.querySelector('#num');
    //用户绑定焦点事件
    address.onkeyup = function checkAddress(){
        //获取输入框中的内容
        var billAddress =$("#num").val();
        console.log(billAddress)
        $.ajax({
            type:"POST",
            url:"/BillServlet.do",
            data:"action=checkBillNum&billAddress="+billAddress,
            dataType:"text",//服务器返回的数据类型
            success:function(result){
                if(billAddress =="") {
                    $("#checkBillNum").text("不能为空");
                    $("#checkBillNum").css("color", "red");
                    checkBillNum = false;
                }else if (result==1){
                    $("#checkBillNum").text("商品必须为数字并且为整数！");
                    $("#checkBillNum").css("color","red");
                    checkBillNum =false;
                }else if (result==0){
                    $("#checkBillNum").text("√" );
                    $("#checkBillNum").css("color","green");
                    checkBillNum =true;
                }
            }
        })
    }



    var checkBillFax = true;
    //获取输入框
    var fax = document.querySelector('#money');
    //用户绑定焦点事件
    fax.onkeyup = function checkFax(){
        //获取输入框中的内容
        var billFax =$("#money").val();
        console.log(billFax)
        $.ajax({
            type:"POST",
            url:"/BillServlet.do",
            data:"action=checkBillFax&billFax="+billFax,
            dataType:"text",//服务器返回的数据类型
            success:function(result){
                if(billFax =="") {
                    $("#checkBillFax").text("不能为空");
                    $("#checkBillFax").css("color", "red");
                    checkBillFax = false;
                }else if (result==1){
                    $("#checkBillFax").text("商品必须为数字并且为正数！");
                    $("#checkBillFax").css("color","red");
                    checkBillFax =false;
                }else if (result==0){
                    $("#checkBillFax").text("√" );
                    $("#checkBillFax").css("color","green");
                    checkBillFax =true;
                }
            }
        })
    }
    function checkAddAll() {
        return checkBillName&&checkBillPhone&&checkBillNum&&checkBillFax;
    }
</script>
