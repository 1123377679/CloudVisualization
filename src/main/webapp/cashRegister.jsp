<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>超市收银系统</title>

  <!-- 引入layui文件 -->
  <link rel="stylesheet" href="https://cdn.bootcdn.net/ajax/libs/layui/2.6.9/css/layui.css" />
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" />
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"
    integrity="sha512-vOJS9Q/pr2EtJ/XD7M+0JX5vGjj4M7t0uj51wuHnLx8J1fRO7tbWTgjPWwCXMWk9Hq8Zw5vha68H5HliX0HUCA=="
    crossorigin="anonymous" referrerpolicy="no-referrer" />
  <script src="/js/jquery.min.js"></script>
  <style>
    /* 设置样式 */
    body {
      margin: 0;
      padding: 0;
    }

    .video-container {
      position: fixed;
      top: 0;
      left: 0;
      height: 100%;
      width: 100%;
      z-index: -1;
    }

    .video-container video{
    <%
    this.getClass().getClassLoader().getResourceAsStream("/images/hai.mp4");
    %>
      position: absolute;
      top: 50%;
      left: 50%;
      min-width: 100%;
      min-height: 100%;
      width: auto;
      height: auto;
      z-index: -1;
      transform: translateX(-50%) translateY(-50%);
    }

    .header {
      height: 60px;
      line-height: 60px;
      background-color: #00acac;
      color: #fff;
    }

    .header h2 {
      display: inline-block;
      margin-left: 20px;
    }

    .header .nav {
      float: right;
      margin-right: 20px;
    }

    .table-box {
      margin-top: 20px;
      border: 1px solid #e6e6e6;
      font-family: "Microsoft YaHei", sans-serif;
      color: #333;
      background-color: #f5f5f5;
      height: 245px;
      overflow-y: auto;
      /* 或者是 overflow-y: scroll; */
    }

    table {
      width: 100%;
      border-collapse: collapse;
    }

    th,
    td {
      padding: 10px;
      text-align: center;
      border-bottom: 1px solid #e6e6e6;
    }

    thead th {
      background-color: #f2f2f2;
    }

    .num {
      display: inline-block;
      padding: 0 10px;
      font-size: 16px;
    }

    .subtract-btn,
    .add-btn {
      display: inline-block;
      width: 20px;
      height: 20px;
      line-height: 20px;
      text-align: center;
      font-size: 12px;
      color: #fff;
      background-color: #999;
      cursor: pointer;
    }

    .subtract-btn:hover,
    .add-btn:hover {
      background-color: #555;
    }

    a {
      color: #009688;
      text-decoration: none;
    }

    a:hover {
      color: #006d5b;
      text-decoration: underline;
    }

    .input-box {
      margin-top: 20px;
      padding: 10px;
      background-color: #fff;
    }

    .input-box .title {
      font-size: 16px;
      font-weight: bold;
      margin-bottom: 10px;
    }

    .input-box .form-group {
      margin-bottom: 10px;
    }

    .input-box .label {
      display: inline-block;
      width: 60px;
      text-align: right;
      margin-right: 10px;
    }

    .input-box .input {
      display: inline-block;
      width: 210px;
      height: 36px;
      line-height: 36px;
      border: 1px solid #ddd;
      padding: 0 34px;
      box-sizing: border-box;
    }

    .input-box .clear-btn {
      display: inline-block;
      margin-left: 10px;
      padding: 5px;
      background-color: #1E9FFF;
      color: #fff;
      text-align: center;
      cursor: pointer;
    }

    .calc-box {
      margin-top: 20px;
      padding: 10px;
      background-color: #fff;
    }

    .calc-box .title {
      font-size: 16px;
      font-weight: bold;
      margin-bottom: 10px;
    }

    .calc-box .form-group {
      display: flex;
      align-items: center;
      justify-content: space-between;
      margin-bottom: 10px;
    }

    /* 设置响应式样式 */
    @media screen and (max-width: 768px) {

      .table-box th,
      .table-box td {
        font-size: 12px;
      }

      .input-box .label {
        width: 50px;
      }

      .input-box .input {
        width: 150px;
      }
    }

    @media screen and (max-width: 480px) {

      .table-box th,
      .table-box td {
        font-size: 10px;
      }

      .input-box .label {
        width: 40px;
      }

      .input-box .input {
        width: 100px;
      }
    }

    /* 数字键盘样式 */
    .calc-keyboard {
      width: 100%;
      border: 1px solid #ddd;
      border-radius: 5px;
      padding: 10px;
      box-sizing: border-box;
    }

    .calc-keyboard .row {
      display: flex;
      justify-content: space-between;
      margin-bottom: 5px;
    }

    .calc-keyboard .key {
      flex: 1;
      height: 40px;
      line-height: 40px;
      text-align: center;
      border: 1px solid #ddd;
      border-radius: 5px;
      cursor: pointer;
    }

    .calc-keyboard .key.operation {
      color: #fff;
      background-color: #f60;
    }

    .calc-box .form-group .input {
      display: block;
      width: 100%;
      height: 40px;
      line-height: 40px;
      padding: 0 10px;
      border: 1px solid #ccc;
      border-radius: 5px;
      box-sizing: border-box;
    }

    @media (max-width: 767px) {
      .calc-keyboard .row {
        flex-wrap: wrap;
      }

      .calc-keyboard .key {
        width: calc(33.33% - 5px);
        margin-bottom: 5px;
      }
    }

    /* 重置按钮样式 */
    .reset-btn {
      position: absolute;
      right: 36px;
      top: 401px;
      transform: translateY(-50%);
      background-color: transparent;
      border: none;
      outline: none;
      z-index: 2;
    }


    .reset-btn:hover {
      color: #999;
      cursor: pointer;
    }

    /* 重置按钮图标样式 */
    .reset-btn .fa {
      font-size: 14px;
    }

    .clear-btn {
      margin-left: 10px;
      padding: 5px;
      background-color: #1E9FFF;
      color: #fff;
      text-align: center;
      cursor: pointer;
      border-radius: 3px;
      transition: background-color 0.2s ease-in-out, color 0.2s ease-in-out;
    }

    .alipay-btn {
      display: flex;
      justify-content: center;
      align-items: center;
      font-size: 16px;
      color: #fff;
      background-color: #ffffff;
      border: none;
      border-radius: 4px;
      width: 274px;
      height: 41px;
      cursor: pointer;
      box-shadow: 0 2px 2px rgba(0, 0, 0, .1);
    }

    .alipay-btn i {
      margin-left: 8px;
      font-size: 20px;
    }

    #AddBill {
      background-image: url(/images/条形码.png);
      background-repeat: no-repeat;
      background-position: 5px 5px;
    }

  </style>
</head>

<body>
<div class="video-container">
  <video autoplay loop muted>
    <source src="/images/hai.mp4" type="video/mp4">
    </video>
</div>

  <div class="header">
    <h2>超市收银系统</h2>
    <div class="nav">
      <span>欢迎您，admin</span>
      <a href="#">个人中心</a>
      <a href="#">登出</a>
    </div>
  </div>
  <div class="layui-container">
    <div class="table-box">
      <table>
        <thead>
          <tr>
            <th>商品名称</th>
            <th>数量</th>
            <th>单价</th>
            <th>合计</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody class="goods-list">
          <!-- 商品条目根据需求自行添加 -->
<%--          <tr>--%>
<%--            <td>可乐</td>--%>
<%--            <td class="count">--%>
<%--              <div class="counter">--%>
<%--                <div class="subtract-btn"><i class="fa fa-minus"></i></div>--%>
<%--                <div class="num">1</div>--%>
<%--                <div class="add-btn"><i class="fa fa-plus"></i></div>--%>
<%--              </div>--%>
<%--            </td>--%>
<%--            <td>¥ 3.00</td>--%>
<%--            <td>¥ 3.00</td>--%>
<%--            <td><a href="#">删除</a></td>--%>
<%--          </tr>--%>
        </tbody>
      </table>
    </div>
    <div class="input-box">
<%--      <div class="title">扫码/输入商品条码</div>--%>
      <div class="form-group">
<%--        <label class="label">条码：</label>--%>
        <input type="text" class="input" id="AddBill" placeholder="请扫描或手动输入条码" />
        <div class="clear-btn" onclick="addButton();">添加</div>
        <div class="clear-btn">清空</div>
      </div>
    </div>
    <div class="calc-box">
      <div class="title">结算金额：</div>
      <div class="form-group">
        <label class="label" style="width: 75px;">应付金额：</label>
        <div class="input resettable">¥ 0.00</div>
      </div>
      <div class="form-group">
        <label class="label" style="width: 77px;">实付金额：</label>
        <div class="input out resettable" contenteditable="true">¥ 0.00</div>
        <div class="clear-btn" onclick="updateRealPayable();">
          <i class="fas fa-times"></i>
        </div>
      </div>
      <div class="form-group">
        <label class="label" style="width: 77px;">找零金额：</label>
        <div class="input resettable">¥ 0.00</div>
        <div class="clear-btn" onclick="$('input[name=name]').val('')">
          <i class="fas fa-times"></i>
        </div>
      </div>
      <div class="form-group">
        <div class="calc-keyboard">
          <div class="row">
            <div class="key">1</div>
            <div class="key">2</div>
            <div class="key">3</div>
            <div class="key operation">删除</div>
          </div>
          <div class="row">
            <div class="key">4</div>
            <div class="key">5</div>
            <div class="key">6</div>
            <div class="key operation">清空</div>
          </div>
          <div class="row">
            <div class="key">7</div>
            <div class="key">8</div>
            <div class="key">9</div>
            <div class="key operation" onclick="showPaymentWindow()">结算</div>
          </div>
          <div class="row">
            <div class="key">00</div>
            <div class="key">0</div>
            <div class="key">.</div>
            <button class="alipay-btn"></button>
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- 引入layui文件 -->
  <script src="https://cdn.bootcdn.net/ajax/libs/layui/2.6.9/layui.js"></script>


</body>

</html>
<script>
  layui.use(['layer'], function () {
    var layer = layui.layer;


  });
  function showPaymentWindow() {
    layui.use('layer', function () {
      var layer = layui.layer;
      var index = layer.open({
        type: 2,
        title: '支付结算',
        shadeClose: true,
        shade: [0.8, '#393D49'],
        area: ['70%', '80%'],
        content: 'payment.jsp'
      });
      // layer.full(index);
    });
  }
</script>

<script>
  // 绑定加减号按钮点击事件
  function bindClickEvent() {
    // 绑定减号按钮点击事件
    $('.subtract-btn').click(function () {
      var num = parseInt($(this).siblings('.num').text());
      if (num > 1) {
        $(this).siblings('.num').text(num - 1);
        updateTotal($(this));
        updatePayable();
        e.stopPropagation();
      }
    });

    // 绑定加号按钮点击事件
    $('.add-btn').click(function () {
      var num = parseInt($(this).siblings('.num').text());
      $(this).siblings('.num').text(num + 1);
      updateTotal($(this));
      updatePayable();
      e.stopPropagation();
    });

    // 更新商品加减数量和合计金额
    function updateTotal($elem) {
      // 计算单价和数量
      var price = parseFloat($elem.closest('tr').find('td:eq(2)').text().substring(2));
      var num = parseInt($elem.siblings('.num').text());
      // 更新合计金额
      var total = (price * num).toFixed(2);
      $elem.closest('tr').find('td:nth-last-child(2)').text('¥ ' + total);
    }


    // 清空输入框
    $('.clear-btn').click(function () {
      $('.input-box .input').val('');
    });

    // 数字键盘绑定事件
    $('.calc-keyboard .key').click(function () {
      var text = $(this).text();
      var $amountInput = $('.calc-box .form-group:eq(1) .input');
      if (text === '删除') {
        var amount = $amountInput.text();
        $amountInput.text(amount.substring(0, amount.length - 1));
      } else if (text === '清空') {
        $amountInput.text('¥ 0.00');
      } else if (text === '结算') {
        var shouldPay = parseFloat($('.calc-box .form-group:eq(0) .input').text().substring(2));
        // 处理实付金额，去掉非数字字符并转化为数字类型
        var actualPay = parseFloat($amountInput.text().replace(/[^0-9\.]+/g, ""));
        var change = actualPay - shouldPay;
        $('.calc-box .form-group:eq(2) .input').text('¥ ' + change.toFixed(2));
        // layer.open({
        //   title: '结算成功',
        //   content: '找零金额为：¥ ' + change.toFixed(2),
        //   yes: function (index, layero) {
        //     layer.close(index);
        //   }
        // });
      } else {
        var amount = $amountInput.text();
        if (amount === '¥ 0.00') {
          $amountInput.text(text);
        } else if (amount === '¥ ') {
          $amountInput.text('¥ ' + text);
        } else {
          $amountInput.text(amount + text);
        }
      }
    });

    // 重置按钮绑定事件
    $('.reset-btn').click(function (e) {
      var $input = $(e.target).siblings('.input');
      $input.text('¥ 0.00');
    });

    // 阻止重置按钮事件冒泡
    $('.reset-btn').click(function (e) {
      e.stopPropagation();
    });
    // 删除商品
    $('tbody').on('click', 'a', function () {
      var $tr = $(this).closest('tr'),
              subtotal = parseFloat($tr.find('td:nth-last-child(2)').text());
      $tr.remove();

      // 计算小计和总计
      var total = parseFloat($('tfoot td:last-child').text());
      $('tfoot td:last-child').text((total - subtotal).toFixed(2));
      if ($('tbody tr').length == 0) {
        $('tfoot td:last-child').prev().prev().text('');
      }
      // 更新应付金额
      updatePayable();
    })
    $('form').submit(function (e) {
      e.preventDefault();
      // 清空表单
      $('input[name=name]').val('');
      $('input[name=price]').val('');
      $('.counter .num').text('1');
      $('.subtract-btn').addClass('disabled');
    });
    // 获取应付金额输入框元素
    var $payableInput = $('.calc-box .form-group:eq(1) .input');
    // 点击清空按钮时将应付金额设置为0
    $('.clear-btn').click(function () {
      $payableInput.text('¥ 0.00');
    });
    // 获取应付金额输入框元素
    var $payableInput = $('.calc-box .form-group:eq(2) .input');
    // 点击清空按钮时将应付金额设置为0
    $('.clear-btn').click(function () {
      $payableInput.text('¥ 0.00');
    });
  }
  // 更新应付金额
  function updatePayable() {
    var total = 0;
    $('.goods-list tr').each(function () {
      total += parseFloat($(this).find('td:nth-last-child(2)').text().substring(2));
    });
    $('.calc-box .form-group:eq(0) .input').text('¥ ' + total.toFixed(2));
    // 将 total 存储在 LocalStorage 中
    localStorage.setItem('total', total);
    // sessionStorage.setItem('total', total);
    <%--console.log(`Total is ${total}`);--%>
  }
  // 获取输入框和添加按钮元素
  // const inputBox = $('#AddBill');
  // 添加事件监听器
  function addButton(){
    // 获取输入框中的值
    let val = $('#AddBill').val();
    $.ajax({
      type:"POST",
      url:"/CommodityServlet.do",
      data:"action=addBill&val="+val,
      dataType:"json",//服务器返回的数据类型
      success:function(result) {
        if (result == 0){
         alert("您输入的条码不存在,请重新输入");
         $('#AddBill').val('');
        }
        let rowHtml = "<tr>"+
                "<td>"+result.name+"</td>"+
                "<td class='count'>" +
                "<div class='counter'>" +
                "<div class='subtract-btn'><i class='fa fa-minus'></i></div>" +
                "<div class='num'>1</div>" +
                "<div class='add-btn'><i class='fa fa-plus'></i></div>" +
                "</div>" +
                "</td>" +
                "<td>¥ " + result.price.toFixed(2)  + "</td>" +
                "<td>¥ " + result.price.toFixed(2)  + "</td>" +
                "<td><a href='#'>删除</a></td>" +
                "</tr>";
        // 将 HTML 字符串插入到表格中
        $('.goods-list').append(rowHtml);
        // 绑定新生成的加减号按钮点击事件
        bindClickEvent();
        // 更新应付金额
        updatePayable();
        //更新实付金额
        updateRealPayable();
      }
    });
  }
  //回显实付金额
  function updateRealPayable(){ 
    // 存储输入框的值到 localStorage 中
    $('.out').on('input', function() {
      var value = $(this).text();
      if (value === ''){
        value = '¥ 0.00';
      }
      localStorage.setItem('inputValue', value);
    });
  }
</script>