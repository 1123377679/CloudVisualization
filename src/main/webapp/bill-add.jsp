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
        <form class="layui-form" action="/BillServlet.do?action=add" method="post" onsubmit="return checkAddAll();">
            <div class="layui-form-item">
                <div class="layui-form-item">
                    <label for="linkman" class="layui-form-label">
                        <span class="x-red">*</span>商品名称
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" id="linkman" name="title" required="" lay-verify="pass" autocomplete="off" class="layui-input" onblur="checkName()">
                    </div>
                    <div class="layui-form-mid" id="checkBillName" style="color:#999999">输入商品名称</div>
                </div>
                <div class="layui-form-item">
                    <label for="phone" class="layui-form-label">
                        <span class="x-red">*</span>商品单位
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" id="phone" name="unit" required="" lay-verify="repass" autocomplete="off" class="layui-input">
                    </div>
                    <div class="layui-form-mid" id="checkBillPhone" style="color:#999999">输入商品单位</div>
                </div>
                <div class="layui-form-item">
                    <label for="address" class="layui-form-label">
                        <span class="x-red">*</span>商品数量
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" id="address" name="num" required="" lay-verify="repass" autocomplete="off" class="layui-input">
                    </div>
                    <div class="layui-form-mid" id="checkBillNum" style="color:#999999">输入商品数量</div>
                </div>
                <div class="layui-form-item">
                    <label for="fax" class="layui-form-label">
                        <span class="x-red">*</span>总金额
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" id="fax" name="money" required="" lay-verify="repass" autocomplete="off" class="layui-input" onblur="checkMoney()">
                    </div>
                    <div class="layui-form-mid" id="checkBillFax" style="color:#999999">输入商品总金额</div>
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

    }
</script>
<script>
    var checkBillName = false;
    //获取输入框
    var Name = document.querySelector('#linkman');
    //用户绑定焦点事件
    Name.onkeyup = function checkName(){
        //获取输入框中的内容
        var billName =$("#linkman").val();
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

    var checkBillPhone = false;
    //获取输入框
    var Phone = document.querySelector('#phone');
    //用户绑定焦点事件
    Phone.onkeyup = function checkPhone(){
        //获取输入框中的内容
        var billPhone =$("#phone").val();
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


    var checkBillNum = false;
    //获取输入框
    var address = document.querySelector('#address');
    //用户绑定焦点事件
    address.onkeyup = function checkAddress(){
        //获取输入框中的内容
        var billAddress =$("#address").val();
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
                    $("#checkBillNum").text("商品必须为数字并且为正整数！");
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



    var checkBillFax = false;
    //获取输入框
    var fax = document.querySelector('#fax');
    //用户绑定焦点事件
    fax.onkeyup = function checkFax(){
        //获取输入框中的内容
        var billFax =$("#fax").val();
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