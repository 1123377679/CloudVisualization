<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <script src="/js/jquery.min.js"></script>
    <script src="/lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="/js/xadmin.js"></script>
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="x-nav">
          <span class="layui-breadcrumb">
            <a href="">首页</a>
            <a href="">演示</a>
            <a>
              <cite>导航元素</cite></a>
          </span>
    <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right" onclick="location.reload()" title="刷新">
        <i class="layui-icon layui-icon-refresh" style="line-height:30px"></i></a>
</div>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-body ">
                    <form class="layui-form layui-col-space5" action="/Merchandise.do?action=limit&pageIndex=${requestScope.pageUtils.pageIndex}&pageSize=${requestScope.pageUtils.pageSize}" method="post">
                        <div class="layui-inline layui-show-xs-block">
                            <input type="text" name="username"  placeholder="请输入订单号" autocomplete="off" class="layui-input">
                        </div>
                        <div class="layui-inline layui-show-xs-block">
                            <button class="layui-btn"  lay-submit="" lay-filter="sreach"><i class="layui-icon">&#xe615;</i></button>
                        </div>
                    </form>
                </div>
                <div class="layui-card-header">
                    <button class="layui-btn layui-btn-danger" onclick="delAll()"><i class="layui-icon"></i>批量删除</button>
                </div>
                <div class="layui-card-body layui-table-body layui-table-main">
                    <table class="layui-table layui-form">
                        <thead>
                        <tr>
                            <th>
                                <input type="checkbox" lay-filter="checkall" name="" lay-skin="primary">
                            </th>
                            <th>ID</th>
                            <th>订单编号</th>
                            <th>会员名称</th>
                            <th>订单类型</th>
                            <th>总金额</th>
                            <th>支付状态</th>
                            <th>生成时间</th>
                            <th>支付金额</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <c:forEach items="${requestScope.pageUtils.records}" var="p">
                            <tbody>
                            <tr>
                                <td>
                                    <input type="checkbox" name="id" value="${p.id}"   lay-skin="primary">
                                </td>
                                <td>${p.id}</td>
                                <td>${p.orderNo}</td>
                                <td>${p.memberName}</td>
                                <td>${p.orderType}</td>
                                <td>${p.totalAmount}</td>
                                <td class="td-status">
                                    <c:if test="${p.paymentStatus == 0}"><span class="layui-btn layui-btn-normal layui-btn-mini" style="background-color: #d94054">未支付</span>
                                    </c:if>
                                    <c:if test="${p.paymentStatus == 1}"><span class="layui-btn layui-btn-normal layui-btn-mini">已支付</span>
                                    </c:if>
                                </td>
                                <td>${p.createdAt}</td>
                                <td>${p.paymentAmount}</td>
                                <td class="td-manage">
                                    <a title="查看详情"  onclick="xadmin.open('查看详情','/Merchandise.do?action=details&id=${p.id}',600,400)" href="javascript:;">
                                        <i class="layui-icon">&#xe66e;</i>
                                    </a>
                                    <a title="编辑"  onclick="xadmin.open('编辑','/Merchandise.do?action=goUpdate&id=${p.id}',600,400)" href="javascript:;">
                                        <i class="layui-icon">&#xe642;</i>
                                    </a>
                                    <a title="删除" href="javascript:deleteMember(${p.id});">
                                        <i class="layui-icon">&#xe640;</i>
                                    </a>
                                </td>
                            </tr>
                            </tbody>
                        </c:forEach>
                    </table>
                </div>
                <%--  左箭头      --%>
                <div class="layui-box layui-laypage layui-laypage-default" id="layui-laypage-1" style="margin-left: 15px;">
                    <a href="/Merchandise.do?action=limit&pageIndex=${requestScope.pageUtils.pageIndex-1}&pageSize=${requestScope.pageUtils.pageSize}"
                       class="layui-laypage-prev ${requestScope.pageUtils.pageIndex == 1 ? 'layui-disabled':''}"
                       data-page="0"
                    ${requestScope.pageUtils.pageIndex == 1? 'onclick="return false;"':''}>
                        <i class="layui-icon" >&lt;</i>
                    </a>
                    <c:forEach begin="${requestScope.pageUtils.numberStart}" end="${requestScope.pageUtils.numberEnd}" var="num" step="1">
                        <c:if test="${requestScope.pageUtils.pageIndex == num}">
                            <span style="color:#fd0303;font-weight: bold;">${num}</span>
                        </c:if>
                        <c:if test="${requestScope.pageUtils.pageIndex!=num}">
                            <a href="/Merchandise.do?action=limit&pageIndex=${num}&pageSize=${requestScope.pageUtils.pageSize}">${num}</a>
                        </c:if>
                    </c:forEach>
                    <%--  右箭头      --%>
                    <a href="/Merchandise.do?action=limit&pageIndex=${requestScope.pageUtils.pageIndex+1}&pageSize=${requestScope.pageUtils.pageSize}"
                       class="layui-laypage-next ${requestScope.pageUtils.pageIndex == requestScope.pageUtils.numberEnd ? 'layui-disabled':''}"
                       data-page="2"
                    ${requestScope.pageUtils.pageIndex == requestScope.pageUtils.numberEnd ? 'onclick="return false;"':''}>
                        <i class="layui-icon">&gt;</i>
                    </a>
                    <%--     确定和输入跳转页面     --%>
                    <span class="layui-laypage-skip">到第
							   <input type="text" min="1" id="number" value="${requestScope.pageUtils.pageIndex}" class="layui-input">页
								<button type="button" class="layui-laypage-btn" onclick="jumpPage();">确定</button>
							</span>
                    <span class="layui-laypage-count">[当前${requestScope.pageUtils.pageIndex}/${requestScope.pageUtils.pageCount}]</span>
                    <span class="layui-laypage-limits">
							    <select lay-ignore="" onchange="goPage(this);">
									<option value="5"  ${requestScope.pageUtils.pageSize==5?"selected":""}>5 条/页</option>
									<option value="10" ${requestScope.pageUtils.pageSize==10?"selected":""}>10 条/页</option>
									<option value="20" ${requestScope.pageUtils.pageSize==20?"selected":""}>20 条/页</option>
									<option value="30" ${requestScope.pageUtils.pageSize==30?"selected":""}>30 条/页</option>
									<option value="40" ${requestScope.pageUtils.pageSize==40?"selected":""}>40 条/页</option>
							</select>
          </span>
                </div>
            </div>
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
    translate.ignore.class.push('left_b');
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
<%--<style>--%>
<%--    display: inline-block;--%>
<%--    height: 38px;--%>
<%--    line-height: 38px;--%>
<%--    padding: 0 18px;--%>
<%--    background-color: #009688;--%>
<%--    color: #fff;--%>
<%--    white-space: nowrap;--%>
<%--    text-align: center;--%>
<%--    font-size: 14px;--%>
<%--    border: none;--%>
<%--    border-radius: 2px;--%>
<%--    cursor: pointer;--%>
<%--</style>--%>
<script>
    /**
     * 动态修改页面大小
     */
    function goPage(select) {
        var pageSize = $(select).val();
        location.href = "/Merchandise.do?action=limit&pageIndex=1&pageSize=" + pageSize;
    }

    /**
     * 跳转页码
     */
    function jumpPage() {
        var number = $("#number").val();
        // alert(number);
        if (number <= 0) {
            number = 1;
        } else if (!(/(^[1-9]\d*$)/.test(number))) {
            alert("输入的页码非法！");
            $("#number").val("");
            $("#number").focus();
            return;//终止
        } else if (number >${requestScope.pageUtils.pageCount}) {
            number =${requestScope.pageUtils.pageCount};
        }
        location.href = "/Merchandise.do?action=limit&pageIndex=" + number + "&pageSize=${requestScope.pageUtils.pageSize}";
    }
    //删除功能
    function deleteMember(id) {
        layer.msg('确定要删除吗?',{
            time:20000,//20s后自动关闭
            btn:['确定','取消'], //按钮
            yes:function(index, layero){
                self.location = '/Merchandise.do?action=delete&id=' + id;
            }
        })
    }

    //批量删除
    function delAll() {
        layer.msg(' 确定要删除吗?', {
            time: 20000, //20s后自动关闭
            btn: ['确定', '取消'],
            yes: function (index, layero) {
                var id = [];//先定义一个空的数组
                if ($("input[type='checkbox']:checked").length > 0) {
                    $("input[type='checkbox']:checked").each(function (i) {
                        id[i] = $(this).val();
                    })
                    // self.location = '/LogServlet.do?action=delAll&checkId='+checkId;//确定按钮跳转地址
                    window.location.href = '/Merchandise.do?action=delAll&id=' + id;//确定按钮跳转地址
                    //$("input[type='checkbox]:checked")拿到已经勾选的东西，然后再each循环
                    //或者$("input[name=check]")
                } else {
                    alert("请选择你要删除的信息");
                    window.location.href = '/Merchandise.do?action=limit&pageIndex=1&pageSize=5';
                }
            }
        });
    }

    //下载模板
    function downloadExcelModel(){
        location.href = "/Merchandise.do?action=downloadExcelModel";
    }

    //上传文件
    layui.use(['laydate','form','upload'], function(){
        var laydate = layui.laydate;
        var form = layui.form;
        var upload = layui.upload;
        // 指定允许上传的文件类型
        upload.render({
            elem: '#test3'//id
            ,url: '/Merchandise.do?action=excelImport' //此处配置你自己的上传接口即可
            ,accept: 'file' //普通文件
            ,multiple:true
            //这里需要返回JSON数据，不然会报接口格式返回有误
            ,done: function(res){
                console.log(res);
                //如果上传成功
                if(res.code == 200){
                    layer.msg('上传成功',{

                    },function (){
                        window.location.reload();
                    });
                }else if (res.code == 500){
                    //上传失败
                    layer.msg('上传失败',{

                    },function (){
                        window.location.reload();
                    });
                }
            }
        });
    });

    //动态效果
    $('#LayerDemo . layui-btn').on('click', function() {
        var othis = $(this), method = othis.data('method');
        active[method] ? active[method].call(this, othis):'';
    });

    <%--导出数据--%>
    function exportExcel(){
        // 搜索的关键字
        var name = $('#name').val();
        //发送请求到后台导出excel数据
        location.href = "/Merchandise.do?action=exportException&name="+name;
    }
</script>
</html>
<script>
    layui.use(['laydate','form','upload','laypage','layer'], function(){
        var laydate = layui.laydate;
        var form = layui.form;
        var upload = layui.upload;
        var laypage = layui.laypage;
        var layer = layui.layer;

        // 监听全选
        form.on('checkbox(checkall)', function(data){

            if(data.elem.checked){
                $('tbody input').prop('checked',true);
            }else{
                $('tbody input').prop('checked',false);
            }
            form.render('checkbox');
        });

        //执行一个laydate实例
        laydate.render({
            elem: '#start' //指定元素
        });

        //执行一个laydate实例
        laydate.render({
            elem: '#end' //指定元素
        });
    });
</script>

