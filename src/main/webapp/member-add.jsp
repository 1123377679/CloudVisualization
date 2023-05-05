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
        <%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <!DOCTYPE html>
        <html>
        <head lang="en">
            <meta charset="UTF-8">
            <title>超市账单管理系统</title>
            <link rel="stylesheet" href="/css/public.css"/>
            <link rel="stylesheet" href="/css/style.css"/>
        </head>
        <body>
        <!--头部-->
        <header class="publicHeader">
            <h1>超市账单管理系统</h1>

            <div class="publicHeaderR">
                <p><span>下午好！</span><span style="color: #fff21b"> Admin</span> , 欢迎你！</p>
                <a href="login.html">退出</a>
            </div>
        </header>
        <!--时间-->
        <section class="publicTime">
            <span id="time">2015年1月1日 11:11  星期一</span>
            <a href="#">温馨提示：为了能正常浏览，请使用高版本浏览器！（IE10+）</a>
        </section>
        <!--主体内容-->
        <section class="publicMian ">
            <div class="left">
                <h2 class="leftH2"><span class="span1"></span>功能列表 <span></span></h2>
                <nav>
                    <ul class="list">
                        <li><a href="billList.html">账单管理</a></li>
                        <li ><a href="providerList.html">供应商管理</a></li>
                        <li id="active"><a href="userList.html">用户管理</a></li>
                        <li><a href="password.html">密码修改</a></li>
                        <li><a href="login.html">退出系统</a></li>
                    </ul>
                </nav>
            </div>
            <div class="right">
                <div class="location">
                    <strong>你现在所在的位置是:</strong>
                    <span>用户管理页面 >> 用户添加页面</span>
                </div>
                <div class="providerAdd">
                    <form action="#">


                        <div>
                            <label for="userName">用户名称：</label>
                            <input type="text" name="userName" id="userName"/>
                            <span >*请输入用户名称</span>
                        </div>
                        <div>
                            <label for="userpassword">用户密码：</label>
                            <input type="text" name="userpassword" id="userpassword"/>
                            <span>*密码长度必须大于6位小于20位</span>

                        </div>
                        <div>
                            <label for="userRemi">确认密码：</label>
                            <input type="text" name="userRemi" id="userRemi"/>
                            <span>*请输入确认密码</span>
                        </div>
                        <div>
                            <label >用户性别：</label>

                            <select name="sex">
                                <option value="man">男</option>
                                <option value="woman">女</option>
                            </select>
                            <span></span>
                        </div>
                        <div>
                            <label for="data">出生日期：</label>
                            <input type="text" name="birthday" id="data"/>
                            <span >*</span>
                        </div>
                        <div>
                            <label for="userphone">用户电话：</label>
                            <input type="text" name="userphone" id="userphone"/>
                            <span >*</span>
                        </div>
                        <div>
                            <label for="userAddress">用户地址：</label>
                            <input type="text" name="userAddress" id="userAddress"/>
                        </div>
                        <div>
                            <label >用户类别：</label>
                            <input type="radio" name="userlei" value="管理员"/>管理员
                            <input type="radio" name="userlei" value="经理"/>经理
                            <input type="radio" name="userlei" value="普通用户"/>普通用户
                        </div>
                        <div class="providerAddBtn">
                            <!--<a href="#">保存</a>-->
                            <!--<a href="userList.html">返回</a>-->
                            <input type="button" value="保存" onclick="history.back(-1)"/>
                            <input type="button" value="返回" onclick="history.back(-1)"/>
                        </div>
                    </form>
                </div>

            </div>
        </section>
        <footer class="footer">
        </footer>
        <script src="/js/time.js"></script>

        </body>
        </html>
    </div>
</div>
<script>layui.use(['form', 'layer','jquery'],
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

    });
</script>
</body>

</html>
