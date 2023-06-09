<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>大数据超市可视化界面</title>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/echarts.min.js"></script>
    <script type="text/javascript" src="js/js.js"></script>
    <script type="text/javascript" src="js/jquery.limarquee.js"></script>
    <script type="text/javascript" src="js/jquery.cxselect.min.js"></script>
    <link rel="stylesheet" href="css/comon0.css">
    <style>
        .echars-login {
            position: absolute;
            top: 21%;
            left: 67%;
            color: white;
            text-decoration: none;
            font-size: 18px;
        }
    </style>
</head>
<body>
<div style="background:#000d4a url(images/bg.jpg) center top;">
    <div class="loading">
        <div class="loadbox"> <img src="picture/loading.gif"> 页面加载中... </div>
    </div>
    <div class="back"></div>
    <div class="head">
        <a href="index.jsp" class="tiaozhuan"></a>
        <h1>超市商品信息</h1>
        <a class="echars-login" href="/login.jsp">超市管理登录</a>
        <div class="weather"><span id="showTime"></span></div>
    </div>
    <script>
        var t = null;
        t = setTimeout(time,1000);//開始运行
        function time()
        {
            clearTimeout(t);//清除定时器
            dt = new Date();
            var y=dt.getFullYear();
            var mt=dt.getMonth()+1;
            var day=dt.getDate();
            var h=dt.getHours();//获取时
            var m=dt.getMinutes();//获取分
            var s=dt.getSeconds();//获取秒
            document.getElementById("showTime").innerHTML = y+"年"+mt+"月"+day+"日"+h+"时"+m+"分"+s+"秒";
            t = setTimeout(time,1000); //设定定时器，循环运行
        }
    </script>
    <div class="mainbox">
        <ul class="clearfix">
            <li>
                <div class="boxall" style="height:545px;">

                    <div class="navboxall" >
                        <div class="sycm">
                            <ul class="clearfix">
                                <li>
                                    <h2 id="total-amount"></h2>
                                    <span>总金额</span>
                                </li>
                                <li>
                                    <h2 id="total-count"></h2>
                                    <span>数量</span>
                                </li>
                            </ul>
                        </div>
                        <ul class="jindu clearfix">
                            <div>1000</div>
                            <div>2000</div>
                            <li id="zb1"></li>
                            <li id="zb2"></li>
                            <li id="zb3"></li>
                            <li id="zb4"></li>
                        </ul>
                    </div>
                </div>
            </li>
            <li>
                <div class="boxall" style="height:545px">
                    <div class="alltitle">销售量对比</div>
                    <div class="navboxall" id="echart4"></div>
                </div>
            </li>
            <li>
                <div class="boxall" style="height:260px">
                    <div class="alltitle">商品年销售占比</div>
                    <div class="navboxall" id="echart1"> </div>
                </div>
                <div class="boxall" style="height:270px">
                    <div class="alltitle">年龄分布</div>
                    <div class="navboxall"  id="echart2"> </div>
                </div>
            </li>
        </ul>
        <ul class="clearfix">
            <li>
                <div class="boxall" style="height:390px;">
                    <div class="alltitle">订单信息</div>
                    <div class="navboxall" >
                        <div class="wraptit"> <span>订单号</span><span>订单金额</span><span>订单类型</span><span>当前状态</span> </div>
                        <div class="wrap">
                            <ul id="wrap_ul"></ul>
                        </div>
                    </div>
                </div>
            </li>
            <li style="width:38%">
                <div class="boxall" style="height:390px">
                    <div class="alltitle">商品结算率</div>
                    <div class="navboxall" id="echart3"></div>
                </div>
            </li>
            <li style="width:38%">
                <div class="boxall" style="height:390px">
                    <div class="alltitle">销售额对比</div>
                    <div class="navboxall" id="echart5"></div>
                </div>
            </li>
        </ul>
    </div>
</div>
</body>
</html>
<script>
    var tiaozhuan = document.querySelector('.tiaozhuan');
    let mouseTimer = null;
    let jumpTimer = null;

    window.addEventListener('mousemove', function() {
        clearTimeout(jumpTimer);

        if (!mouseTimer) {
            console.log('鼠标开始移动');
        }

        clearTimeout(mouseTimer);

        mouseTimer = setTimeout(function() {
            console.log('鼠标已停止移动');
            autoJump();
            mouseTimer = null;
        }, 5000);
    });

    function autoJump() {
        window.location.href = 'index.jsp';
    }

    jumpTimer = setTimeout(function() {
        console.log('5秒时间到，自动跳转到新页面');
        autoJump();
    }, 10000);
    $(function(){
        $('.wrap').liMarquee({
            direction: 'up',//身上滚动
            //runshort: false,//内容不足时不滚动
            scrollamount: 20//速度
        });
    });
    //发送ajax请求:获取订单信息
    // 获取ul元素
    var wrapUl = $('#wrap_ul');
    $(function (){
        $.ajax({
            type: "POST",
            url: "/Merchandise.do",
            data: "action=orderLimit",
            dataType:"json",
            success:function (result) {
                // console.log(result);
                // 循环追加数据
                $.each(result,function (index,item){
                    var liElem = $('<li></li>');
                    var pElem = $('<p></p>');
                    // 构建span元素
                    var spanOrderNo = $('<span></span>').text(item.orderNo);
                    var spanTotalAmount = $('<span></span>').text(item.totalAmount.toFixed(2));
                    var spanOrderType = $('<span></span>').text(item.orderType);
                    var spanPaymentStatus = $('<span></span>').text(item.paymentStatus==1 ? '已支付' : '未支付');

                    // 将span元素添加到p元素中
                    pElem.append(spanOrderNo).append(spanTotalAmount).append(spanOrderType).append(spanPaymentStatus);

                    // 将p元素添加到li元素中
                    liElem.append(pElem);

                    // 将li元素添加到ul元素中
                    wrapUl.append(liElem);
                })
            }
        });
    })
    //发送ajax请求:总数
    // 获取ul元素
    var totalCountElem = $('#total-count');
    $(function() {
        $.ajax({
            type: 'POST',
            url: '/SupplierServlet.do',
            data: 'action=orderLimitt',
            dataType: 'JSON',
            success: function(response) {
                var orderCount = parseInt(response);
                // console.log(orderCount)
                totalCountElem.text(orderCount);
            },
            error: function(xhr, textStatus, errorThrown) {
                console.log('请求失败：' + textStatus);
            }
        });
    });
    //金额
    var totalCountEle = $('#total-amount');
    $(function() {
        $.ajax({
            type: 'POST',
            url: '/SupplierServlet.do',
            data: 'action=orderLimito',
            dataType: 'JSON',
            success: function(response) {
                var orderCountm = parseInt(response);
                // console.log(orderCountm)
                totalCountEle.text(orderCountm);
            },
            error: function(xhr, textStatus, errorThrown) {
                console.log('请求失败：' + textStatus);
            }
        });
    });

</script>
