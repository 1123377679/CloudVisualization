<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Document</title>
    <link rel="stylesheet" href="/css/index.css" />
    <link rel="stylesheet" href="/css/layuimini.css">
    <style>
        .echars-login {
            position: absolute;
            top: 24%;
            left: 71%;
            color: white;
            text-decoration: none;
            font-size: 18px;
        }
    </style>
</head>

<body>
<!--初始化加载层-->
<div class="layuimini-loader">
    <div class="layuimini-loader-inner"></div>
</div>
<header>
    <h1>大数据可视化界面</h1>
    <a class="echars-login" href="/login.jsp">后台管理登录</a>
    <div class="showTime">当前时间：2020年3月17-0时54分14秒</div>
    <script>
        var t = null;
        t = setTimeout(time, 1000); //開始运行
        function time() {
            clearTimeout(t); //清除定时器
            dt = new Date();
            var y = dt.getFullYear();
            var mt = dt.getMonth() + 1;
            var day = dt.getDate();
            var h = dt.getHours(); //获取时
            var m = dt.getMinutes(); //获取分
            var s = dt.getSeconds(); //获取秒
            document.querySelector(".showTime").innerHTML =
                "当前时间：" +
                y +
                "年" +
                mt +
                "月" +
                day +
                "-" +
                h +
                "时" +
                m +
                "分" +
                s +
                "秒";
            t = setTimeout(time, 1000); //设定定时器，循环运行
        }
    </script>
</header>
<section class="mainbox">

    <div class="column">
        <div class="panel bar">
            <h2>
                柱状图-账单信息
            </h2>
            <div class="chart"></div>
            <div class="panel-footer"></div>
        </div>
        <div class="panel line">
            <h2>折线图-会员变化</h2>
            <div class="chart"></div>
            <div class="panel-footer"></div>
        </div>
        <div class="panel pie">
            <h2>饼形图-年龄分布</h2>
            <div class="chart"></div>
            <div class="panel-footer"></div>

        </div>
    </div>
    <div class="column">
        <div class="no">
            <div class="no-hd">
                <ul>
                    <li>125811</li>
                    <li>104563</li>
                </ul>
            </div>
            <div class="no-bd">
                <ul>
                    <li>前端需求人数</li>
                    <li>市场供应人数</li>
                </ul>
            </div>
        </div>
        <div class="map">
            <div class="chart"></div>
            <div class="map1"></div>
            <div class="map2"></div>
            <div class="map3"></div>
        </div>
    </div>
    <div class="column">
        <div class="panel bar1">
            <h2>柱状图-技能掌握</h2>
            <div class="chart"></div>
            <div class="panel-footer"></div>
        </div>
        <div class="panel line1">
            <h2>折线图-播放量</h2>
            <div class="chart"></div>
            <div class="panel-footer"></div>
        </div>
        <div class="panel pie1">
            <h2>饼形图-地区分布</h2>
            <div class="chart"></div>
            <div class="panel-footer"></div>
        </div>
    </div>
</section>
<script src="/js/flexible.js"></script>
<script src="/js/jquery.js"></script>
<script src="/js/echarts.min.js"></script>
<script src="/js/index.js"></script>
<script src="/js/china.js"></script>
<script src="/js/myMap.js"></script>
<script>
    $(window).on('load', function () {
        $('.layuimini-loader').fadeOut(); // 隐藏加载层
    });
</script>
</body>
</html>
