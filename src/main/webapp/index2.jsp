
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="refresh" content="100;url='https://gitee.com/iGaoWei/big-data-view'">
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/echarts.min.js"></script>
    <script type="text/javascript" src="js/js.js"></script>
    <script type="text/javascript" src="js/jquery.limarquee.js"></script>
    <script type="text/javascript" src="js/jquery.cxselect.min.js"></script>
    <link rel="stylesheet" href="css/comon0.css">
</head>
<body>
<div style="background:#000d4a url(images/bg.jpg) center top;">
    <div class="loading">
        <div class="loadbox"> <img src="picture/loading.gif"> 页面加载中... </div>
    </div>
    <div class="back"></div>
    <div class="head">
        <h1>大数据可视化展示平台通用模板</h1>
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
                                    <h2>22864</h2>
                                    <span>总金额</span></li>
                                <li>
                                    <h2>1572</h2>
                                    <span>数量</span></li>

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
                    <div class="alltitle">标题样式</div>
                    <div class="navboxall" id="echart4"></div>
                </div>
            </li>
            <li>
                <div class="boxall" style="height:260px">
                    <div class="alltitle">标题样式</div>
                    <div class="navboxall" id="echart1"> </div>
                </div>
                <div class="boxall" style="height:270px">
                    <div class="alltitle">标题样式</div>
                    <div class="navboxall"  id="echart2"> </div>
                </div>
            </li>
        </ul>
        <ul class="clearfix">
            <li>
                <div class="boxall" style="height:390px;">
                    <div class="alltitle">标题样式</div>
                    <div class="navboxall" >
                        <div class="wraptit"> <span>订单号</span><span>订单金额</span><span>计划时间</span><span>当前状态</span> </div>
                        <div class="wrap">
                            <ul>
                                <li>
                                    <p><span>100021415</span><span>199</span><span>手机</span><span>18小时</span></p>
                                </li>
                                <li>
                                    <p><span>100021415</span><span>199</span><span>手机</span><span>18小时</span></p>
                                </li>
                                <li>
                                    <p><span>100021415</span><span>199</span><span>手机</span><span>18小时</span></p>
                                </li>
                                <li>
                                    <p><span>100021415</span><span>199</span><span>手机</span><span>18小时</span></p>
                                </li>
                                <li>
                                    <p><span>100021415</span><span>199</span><span>手机</span><span>18小时</span></p>
                                </li>
                                <li>
                                    <p><span>100021415</span><span>199</span><span>手机</span><span>18小时</span></p>
                                </li>
                                <li>
                                    <p><span>100021415</span><span>199</span><span>手机</span><span>18小时</span></p>
                                </li>
                                <li>
                                    <p><span>100021415</span><span>199</span><span>手机</span><span>18小时</span></p>
                                </li>

                            </ul>
                        </div>
                    </div>
                </div>
            </li>
            <li style="width:38%">
                <div class="boxall" style="height:390px">
                    <div class="alltitle">标题样式</div>
                    <div class="navboxall" id="echart3"></div>
                </div>
            </li>
            <li style="width:38%">
                <div class="boxall" style="height:390px">
                    <div class="alltitle">标题样式</div>
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
    var timer;
    var timeout = 5; // 定义自动跳转的时间，单位为秒

    document.addEventListener('mousemove', function() {
        // 如果已经开始倒计时，则退出
        if (timer) {
            return;
        }
        tiaozhuan.innerHTML = '';
        timer = setTimeout(function() {
            autoJump();
        }, timeout * 1000);
    });

    document.addEventListener('mouseout', function() {
        if (timer) {
            clearTimeout(timer);
            timer = null;
            tiaozhuan.innerHTML = '';
        }
    });

    function autoJump() {
        window.location.href = 'index.jsp';
    }
    $(function(){
        $('.wrap').liMarquee({
            direction: 'up',//身上滚动
            //runshort: false,//内容不足时不滚动
            scrollamount: 20//速度
        });
    });
</script>
