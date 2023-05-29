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
        <form class="layui-form" method="post" action="/CommodityServlet.do?action=add" onsubmit="return checkAddAll();">
            <div class="layui-form-item">
                <label for="L_email" class="layui-form-label">
                    <span class="x-red">*</span>商品名称</label>
                <div class="layui-input-inline">
                    <input type="text" id="shangpingname" name="name" required="" lay-verify="email" autocomplete="off" class="layui-input"></div>
                <%------------  --   --- ---------------------- ----------------------     ------        --%>
                <div class="layui-form-mid" id="nameWord" style="color: #999999">需标注商品规格</div>
            </div>
            <div class="layui-form-item">
                <label for="L_pass" class="layui-form-label">
                    <span class="x-red">*</span>商品条码</label>
                <div class="layui-input-inline">
                    <input type="text" id="barcode" name="barcode" required="" lay-verify="pass" autocomplete="off" class="layui-input"></div>
                <%------------  --   --- ---------------------- ----------------------     ------        --%>
                <div class="layui-form-mid" id="codeWord" style="color: #999999">13位 EAN-13 商品条码</div>
                <div class="layui-form-item">
                    <label for="L_repass" class="layui-form-label">
                        <span class="x-red">*</span>商品价格</label>
                    <div class="layui-input-inline">
                        <input type="text" id="shangpingprice" name="price" required="" lay-verify="repass" autocomplete="off" class="layui-input"></div>
                    <%------------  --   --- ---------------------- ----------------------     ------        --%>
                    <div class="layui-form-mid" id="priceWord" style="color: #999999">（元/件）</div>
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
<div class="translateSelectLanguage" id="translate"></div>
</body>
</html>
<script>
    //全局变量
    var checkAllCommodityName = false;
    // 获取商品名称文本框元素
    var shangpingname = $("#shangpingname");

    // 商品名称文本框焦点事件处理函数
    shangpingname.keyup(function checkshangpingname() {
        // 获取商品名称
        var name = shangpingname.val();
        $.ajax({
            type: "POST",
            url: "/CommodityServlet.do",
            data: "action=checkName&name=" + name,
            dataType: "text",
            success: function (result) {
                // 验证是否输入了商品名称
                if (name == ""){
                    $("#nameWord").text("商品名称不能为空！");
                    $("#nameWord").css("color", "red");
                    checkAllCommodityName = false;
                } else if (result == 0){
                    $("#nameWord").text("商品名称已存在！");
                    $("#nameWord").css("color", "red");
                    checkAllCommodityName = false;
                } else if (result == 1){
                    $("#nameWord").text("√");
                    $("#nameWord").css("color", "green");
                    checkAllCommodityName = true;
                }
            }
        });
    });


    //全局变量
    var checkAllBarCode = false;
    //商品条码
    var barcode = document.querySelector("#barcode");
    barcode.onkeyup = function checkBarcode(){
        //获取条码
        var barcode = $("#barcode").val();
        $.ajax({
            type: "POST",
            url: "/CommodityServlet.do",
            data: "action=checkBarcode&barcode=" + barcode,
            dataType: "text",
            success: function (result) {
                if (barcode == "") {
                    $("#codeWord").text("商品条码不能为空！");
                    $("#codeWord").css("color", "red");
                    checkAllBarCode = false;
                }else if (result == 0){
                    $("#codeWord").text("商品条码不合法！");
                    $("#codeWord").css("color", "red");
                    checkAllBarCode = false;
                }else if (result == 1){
                    $("#codeWord").text("√");
                    $("#codeWord").css("color", "green");
                    checkAllBarCode = true;
                }else if (result == 2){
                    $("#codeWord").text("商品条码已存在！");
                    $("#codeWord").css("color", "red");
                    checkAllBarCode = false;
                }
            }
        });
    }


    //全局变量
    var checkAllPrice = false;
    // 获取商品价格文本框元素
    var shangpingprice = $("#shangpingprice");

    // 商品价格文本框焦点事件处理函数
    shangpingprice.keyup(function checkshangpingprice() {
        // 获取商品价格
        var price = shangpingprice.val();
        // 验证是否输入了商品价格
        if (price == ""){
            $("#priceWord").text("商品价格不能为空！");
            $("#priceWord").css("color", "red");
            checkAllPrice = false;
        } else if (!price.match(/^[1-9]\d*(\.\d{1,2})?$|^0(\.\d{1,2})?$/)){
            $("#priceWord").text("商品价格格式不正确！");
            $("#priceWord").css("color", "red");
            checkAllPrice = false;
        } else {
            $("#priceWord").text("√");
            $("#priceWord").css("color", "green");
            checkAllPrice = true;
        }
    });




    function checkAddAll(){
        console.log(checkAllCommodityName);
        console.log(checkAllBarCode);
        console.log(checkAllPrice);
        return checkAllCommodityName&&checkAllBarCode&&checkAllPrice;
    }

</script>
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

