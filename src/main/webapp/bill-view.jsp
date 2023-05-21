
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/css/font.css">
    <link rel="stylesheet" href="/css/xadmin.css">
    <script type="text/javascript" src="/lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="/js/xadmin.js"></script>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
</head>
<body>
<div class="layui-form-item">
    <label class="layui-form-label">账单编码:</label><span style="position: relative;top: 11px">${requestScope.bills.id}</span>
</div>
<div class="layui-form-item">
    <label class="layui-form-label">商品名称:</label><span style="position: relative;top: 11px">${requestScope.bills.title}</span>
</div>
<div class="layui-form-item">
    <label class="layui-form-label">供应商:</label><span style="position: relative;top: 11px">${requestScope.bills.providerid}</span>
</div>
<div class="layui-form-item">
    <label class="layui-form-label">账单金额:</label><span style="position: relative;top: 11px">${requestScope.bills.money}</span>
</div>
<div class="layui-form-item">
    <label class="layui-form-label">商品单位:</label><span style="position: relative;top: 11px">${requestScope.bills.unit}</span>
</div>
<div class="layui-form-item">
    <label class="layui-form-label">商品数量:</label><span style="position: relative;top: 11px">${requestScope.bills.num}</span>
</div>
<div class="layui-form-item">
    <label class="layui-form-label">是否付款:</label><span style="position: relative;top: 11px">${requestScope.bills.ispay==1?"已付款":"未付款"}</span>
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
