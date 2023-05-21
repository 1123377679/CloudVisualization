<%@ page import="cn.lanqiao.pojo.User" %>
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
    <script src="/lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="/js/xadmin.js"></script>
    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <%--    欢迎管理员名字的显示：    --%>
        <%
            User manageUser = (User) session.getAttribute("ManageUser");
            String manname = null;
            if (manageUser != null) {
                //拿到name存入username
                manname = manageUser.getUsername();
            } else {
                response.sendRedirect("/nologin.jsp");
            }
        %>
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-body ">
                    <blockquote class="layui-elem-quote">欢迎管理员：
                        <span class="x-red">
                            <%=manname%>
                        </span><div class="showTime">当前时间：2020年3月17-0时54分14秒</div>
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
                    </blockquote>
                </div>
            </div>
        </div>
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">数据统计</div>
                <div class="layui-card-body ">
                    <ul class="layui-row layui-col-space10 layui-this x-admin-carousel x-admin-backlog">
                        <li class="layui-col-md2 layui-col-xs6">
                            <a href="javascript:;" class="x-admin-backlog-body">
                                <h3>会员数</h3>
                                <p>
                                    <cite class="iconfont_a">67</cite></p>
                            </a>
                        </li>
                        <li class="layui-col-md2 layui-col-xs6">
                            <a href="javascript:;" class="x-admin-backlog-body">
                                <h3>供应数</h3>
                                <p>
                                    <cite class="iconfont_b">11</cite></p>
                            </a>
                        </li>
                        <li class="layui-col-md2 layui-col-xs6">
                            <a href="javascript:;" class="x-admin-backlog-body">
                                <h3>帐单数</h3>
                                <p>
                                    <cite>20</cite></p>
                            </a>
                        </li>
                        <li class="layui-col-md2 layui-col-xs6">
                            <a href="javascript:;" class="x-admin-backlog-body">
                                <h3>登录数</h3>
                                <p>
                                    <cite>936</cite></p>
                            </a>
                        </li>
                        <li class="layui-col-md2 layui-col-xs6">
                            <a href="javascript:;" class="x-admin-backlog-body">
                                <h3>查阅数</h3>
                                <p>
                                    <cite>3567</cite></p>
                            </a>
                        </li>
                        <li class="layui-col-md2 layui-col-xs6 ">
                            <a href="javascript:;" class="x-admin-backlog-body">
                                <h3>总计数</h3>
                                <p>
                                    <cite>4601</cite></p>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="layui-col-sm6 layui-col-md3">
            <div class="layui-card">
                <div class="layui-card-header">会员列表下载
                    <span class="layui-badge layui-bg-cyan layuiadmin-badge">月</span></div>
                <div class="layui-card-body  ">
                    <p class="layuiadmin-big-font">33,555</p>
                    <p>新下载
                        <span class="layuiadmin-span-color">11%
                                    <i class="layui-inline layui-icon layui-icon-face-smile-b"></i></span>
                    </p>
                </div>
            </div>
        </div>
        <div class="layui-col-sm6 layui-col-md3">
            <div class="layui-card">
                <div class="layui-card-header">供应商列表下载
                    <span class="layui-badge layui-bg-cyan layuiadmin-badge">月</span></div>
                <div class="layui-card-body ">
                    <p class="layuiadmin-big-font">12,426</p>
                    <p>新下载
                        <span class="layuiadmin-span-color">6%
                                    <i class="layui-inline layui-icon layui-icon-face-smile-b"></i></span>
                    </p>
                </div>
            </div>
        </div>
        <div class="layui-col-sm6 layui-col-md3">
            <div class="layui-card">
                <div class="layui-card-header">账单列表下载
                    <span class="layui-badge layui-bg-cyan layuiadmin-badge">月</span></div>
                <div class="layui-card-body ">
                    <p class="layuiadmin-big-font">42,678</p>
                    <p>新下载
                        <span class="layuiadmin-span-color">15%
                                    <i class="layui-inline layui-icon layui-icon-face-smile-b"></i></span>
                    </p>
                </div>
            </div>
        </div>
        <div class="layui-col-sm6 layui-col-md3">
            <div class="layui-card">
                <div class="layui-card-header">登录日志下载
                    <span class="layui-badge layui-bg-cyan layuiadmin-badge">月</span></div>
                <div class="layui-card-body ">
                    <p class="layuiadmin-big-font">19,758</p>
                    <p>新下载
                        <span class="layuiadmin-span-color">9%
                                    <i class="layui-inline layui-icon layui-icon-face-smile-b"></i></span>
                    </p>
                </div>
            </div>
        </div>
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">系统信息</div>
                <div class="layui-card-body ">
                    <table class="layui-table">
                        <tbody>
                        <tr>
                            <th>2.0版本</th>
                            <td>1.0.180420</td></tr>
                        <tr>
                            <th>服务器地址</th>
                            <td>47.120.39.138</td></tr>
                        <tr>
                            <th>操作系统</th>
                            <td>Windows</td></tr>
                        <tr>
                            <th>运行环境</th>
                            <td>Apache/2.4.23 (Win32) OpenSSL/1.0.2j mod_fcgid/2.3.9</td></tr>
                        <tr>
                            <th>PHP版本</th>
                            <td>5.6.27</td></tr>
                        <tr>
                            <th>PHP运行方式</th>
                            <td>cgi-fcgi</td></tr>
                        <tr>
                            <th>MYSQL版本</th>
                            <td>5.5.53</td></tr>
                        <tr>
                            <th>ThinkPHP</th>
                            <td>5.0.18</td></tr>
                        <tr>
                            <th>上传附件限制</th>
                            <td>100M</td></tr>
                        <tr>
                            <th>执行时间限制</th>
                            <td>30s</td></tr>
                        <tr>
                            <th>剩余空间</th>
                            <td>86015.2M</td></tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">开发团队</div>
                <div class="layui-card-body ">
                    <table class="layui-table">
                        <tbody>
                        <tr>
                            <th>版权所有</th>
                            <td>xuebingsi(xuebingsi)
                                <a href="http://x.xuebingsi.com/" target="_blank">访问官网</a></td>
                        </tr>
                        <tr>
                            <th>开发团队</th>
                            <td>光宗耀组(583211729@qq.com)</td></tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <style id="welcome_style"></style>
        <div class="layui-col-md12">
            <blockquote class="layui-elem-quote layui-quote-nm">感谢layui,百度Echarts,jquery,本系统由x-admin提供技术支持。</blockquote></div>
    </div>
</div>
</div>
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
