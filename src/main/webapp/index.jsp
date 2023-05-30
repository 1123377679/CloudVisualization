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
        #map-container {
            width: 1130px;
            height: 1075px;
            margin: 0;
            overflow: hidden;
        }
        .qidian{
            position: absolute;
            top: 17px;
            left: 2px;
            color: white;
            text-decoration: none;
            height: 30px;
        }
        .zhongdian{
            position: absolute;
            top: 17px;
            left: 282px;
            color: white;
            text-decoration: none;
        }

        /*#addressOne{*/

        /*    position: absolute;*/
        /*    top: 14px;*/
        /*    left: 104px;*/
        /*    color: white;*/
        /*    text-decoration: none;*/
        /*    height: 30px;*/
        /*}*/
        /*#addressTwo{*/
        /*    position: absolute;*/
        /*    top: 13px;*/
        /*    left: 385px;*/
        /*    color: white;*/
        /*    text-decoration: none;*/
        /*    height: 30px;*/
        /*}*/
        .queding{
            position: absolute;
            left: 34%;
            top: 17%;
            width: 60px;
            height: 28px;
        }
    </style>
</head>

<body>
<!--初始化加载层-->
<div class="layuimini-loader">
    <div class="layuimini-loader-inner"></div>
</div>
<header>
    <a href="index2.jsp" class="tiaozhuan"></a>
<%--    <label class="qidian">请输入起点:</label><input id="addressOne" type="text" style="color:black;"><br>--%>
<%--    <label class="zhongdian">请输入终点：</label><input id="addressTwo" type="text" style="color:black;"><br>--%>
<%--    <button class="queding" onclick="functionHandler()">确定</button>--%>
    <h1 style="position: absolute;top: 1px;left: 39%">大数据超市可视化界面</h1>
    <a class="echars-login" href="/login.jsp">超市管理登录</a>
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
                    <li>超市需求人数</li>
                    <li>超市供应人数</li>
                </ul>
            </div>
        </div>
        <div class="map">
            <div id="map-container"></div>
        </div>
    </div>
    <div class="column">
        <div class="panel bar1">
            <h2>柱状图-商品排行</h2>
            <div class="chart"></div>
            <div class="panel-footer"></div>
        </div>
        <div class="panel line1">
            <h2>折线图-访问转发量</h2>
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
<%--<script type="text/javascript" src="https://api.map.baidu.com/api?v=3.0&ak=eZo1Wz4K7qMFxfGOdssD06WGxj4EtCl0"></script>--%>
<%--<script>--%>
<%--    var map = new BMap.Map("map-container");--%>
<%--    map.centerAndZoom(new BMap.Point(105.438979, 28.847374), 11);--%>
<%--    map.addControl(new BMap.NavigationControl());--%>
<%--    map.addControl(new BMap.ScaleControl());--%>
<%--    map.setMapStyleV2({--%>
<%--        styleId: 'f04ee1f54845ee4f96a3d5b8908437c4'--%>
<%--    });--%>

<%--    function geocodeOne(callback) {--%>
<%--        var address = document.getElementById("addressOne").value;--%>
<%--        var geocoder = new BMap.Geocoder();--%>
<%--        geocoder.getPoint(address, function (point) {--%>
<%--            if (point) {--%>
<%--                var start = new BMap.Point(point.lng, point.lat);--%>
<%--                callback(start);--%>
<%--            } else {--%>
<%--                alert("地址解析失败，请输入正确的地址");--%>
<%--            }--%>
<%--        });--%>
<%--    }--%>

<%--    function geocodeTwo(start, callback) {--%>
<%--        var address = document.getElementById("addressTwo").value;--%>
<%--        var geocoder = new BMap.Geocoder();--%>
<%--        geocoder.getPoint(address, function (point) {--%>
<%--            if (point) {--%>
<%--                var end = new BMap.Point(point.lng, point.lat);--%>
<%--                callback(start, end);--%>
<%--            } else {--%>
<%--                alert("地址解析失败，请输入正确的地址");--%>
<%--            }--%>
<%--        });--%>
<%--    }--%>

<%--    function functionHandler() {--%>
<%--        if (map) {--%>
<%--            map.clearOverlays();--%>
<%--        } else {--%>
<%--            map = new BMap.Map("map-container");--%>
<%--            map.centerAndZoom(new BMap.Point(116.404, 39.915), 11);--%>
<%--            map.addControl(new BMap.NavigationControl());--%>
<%--            map.addControl(new BMap.ScaleControl());--%>
<%--            map.setMapStyleV2({--%>
<%--                styleId: 'f04ee1f54845ee4f96a3d5b8908437c4'--%>
<%--            });--%>
<%--        }--%>

<%--        geocodeOne(function (start) {--%>
<%--            geocodeTwo(start, function (start, end) {--%>
<%--                test(start, end);--%>
<%--            });--%>
<%--        });--%>
<%--    }--%>

<%--    function test(start, end) {--%>
<%--        var startMarker = new BMap.Marker(start);--%>
<%--        var endMarker = new BMap.Marker(end);--%>

<%--        map.addOverlay(startMarker);--%>
<%--        map.addOverlay(endMarker);--%>

<%--        var driving = new BMap.DrivingRoute(map, {--%>
<%--            renderOptions: { map: map, autoViewport: true }--%>
<%--        });--%>
<%--        driving.search(start, end);--%>

<%--        driving.setSearchCompleteCallback(function (result) {--%>
<%--            if (driving.getStatus() == BMAP_STATUS_SUCCESS) {--%>
<%--                var plan = result.getPlan(0);--%>
<%--                var route = plan.getRoute(0);--%>
<%--                var polyline = new BMap.Polyline(route.getPath());--%>
<%--                map.addOverlay(polyline);--%>
<%--                map.setViewport(polyline.getPath()); // 将路线显示在地图上--%>
<%--            } else {--%>
<%--                alert("路线规划失败");--%>
<%--            }--%>
<%--        });--%>
<%--    }--%>
<%--</script>--%>
<script>
    var tiaozhuan = document.querySelector('.tiaozhuan');
    var timer;
    var timeout = 5; // 定义自动跳转的时间，单位为秒

    document.addEventListener('mousemove', function() {
        // 如果已经开始倒计时，则退出
        if (timer) {
            return;
        }
        tiaozhuan.innerHTML = '准备跳转';
        timer = setTimeout(function() {
            autoJump();
        }, timeout * 1000);
    });

    document.addEventListener('mouseout', function() {
        if (timer) {
            clearTimeout(timer);
            timer = null;
            tiaozhuan.innerHTML = '取消跳转';
        }
    });

    function autoJump() {
        window.location.href = 'index2.jsp';
    }
</script>
</body>
</html>
