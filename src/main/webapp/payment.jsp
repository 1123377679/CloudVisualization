<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>支付结算页面</title>
  <!-- 引入layui核心库 -->
  <link rel="stylesheet" href="https://cdn.staticfile.org/layui/2.5.6/css/layui.min.css">
  <!-- 自定义样式文件 -->
  <link rel="stylesheet" href="css/payment.css">
  <script src="/js/jquery.min.js"></script>
  <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
  <style>
    .price {
      height: 36px;
      line-height: 36px;
    }
    .output{
      height: 36px;
      line-height: 36px;
    }

  </style>
</head>

<body>

  <div class="payment-container">
    <!-- 左侧：支付方式选择 -->
    <div class="payment-methods">
      <div class="methods-header">选择支付方式</div>
      <div class="methods-content">
        <div class="method-item method-cash">
          <div class="method-icon"><i class="layui-icon layui-icon-rmb"></i></div>
          <div class="method-name">现金支付</div>
          <input type="radio"  name="payment_method" value="cash" title="" checked>
        </div>
        <div class="method-item method-alipay">
          <div class="method-icon"><i class="layui-icon layui-icon-flag"></i></div>
          <div class="method-name">支付宝支付</div>
          <input type="radio" name="payment_method" value="alipay" title="">
        </div>
      </div>
    </div>
      <!-- 中间：结算信息 -->
      <div class="payment-info">
        <div class="info-header">结算信息</div>
        <div class="info-content">
          <div class="layui-form-item">
            <label class="layui-form-label">应付金额:</label>
            <div class="layui-input-block price">
            </div>
            <%--            <input type="text" name="price" required lay-verify="required|number" placeholder="请输入商品价格"--%>
            <%--              autocomplete="off" class="layui-input" id="price">--%>
          </div>
          <div class="layui-form-item">
            <label class="layui-form-label">实付金额:</label>
            <div class="layui-input-block output">
            </div>
            <%--            <input type="text" name="pay_amount" required lay-verify="required|number" placeholder="请输入实付金额"--%>
            <%--              autocomplete="off" class="layui-input">--%>
          </div>
          <div class="layui-form-item">
            <label class="layui-form-label">备注信息:</label>
            <div class="layui-input-block">
              <textarea name="remarks" placeholder="请输入备注信息" class="layui-textarea"></textarea>
            </div>
          </div>
        </div>
      </div>


    <!-- 右侧：会员信息 -->
    <div class="payment-member">
      <div class="member-header">会员信息</div>
      <div class="member-content">
        <div class="member-section">
          <div class="member-label">会员手机号</div>
          <div class="member-input">
            <input type="text" name="member_phone" placeholder="请输入会员手机号" autocomplete="off" class="layui-input">
          </div>
          <div class="member-add"><i class="layui-icon layui-icon-add-circle"></i></div>
        </div>
      </div>
    </div>

    <!-- 底部：确认收款/取消 -->
    <div class="payment-actions">
      <button class="layui-btn layui-btn-danger cancel-button">取消</button>
      <button class="layui-btn pay-button" onclick="confirmPayment()">确认收款</button>
    </div>
  </div>
  <!-- 引入layui核心库 -->
  <script src="https://cdn.staticfile.org/layui/2.5.6/layui.min.js"></script>

  <!-- 自定义JavaScript代码 -->
  <script>
    var phone;
    layui.use(['form', 'layer', 'jquery'], function () {
      var form = layui.form;
      var layer = layui.layer;
      var $ = layui.jquery;

      // 监听添加会员按钮
      $('.member-add').on('click', function () {
        phone = $('input[name="member_phone"]').val();
        if (phone == '') {
          layer.msg('会员手机号不能为空！');
        } else {
          // TODO: 根据手机号查询会员信息并添加到页面上
          var html = '<div class="member-section">\
                      <div class="member-label">会员手机号</div>\
                      <div class="member-input">' + phone + '</div>\
                      <div class="member-remove"><i class="layui-icon layui-icon-close"></i></div>\
                      </div>';
          $('.member-content').append(html);
          $('input[name="member_phone"]').val('');
        }
      });
      // 监听移除会员按钮
      $('.payment-member').on('click', '.member-remove', function () {
        $(this).parent().remove();
      });

      // 计算总价
      form.on('submit(payment-submit)', function (data) {
        var price = parseFloat(data.field.price);
        var quantity = parseInt(data.field.quantity);
        var total_price = price * quantity;
        $('input[name="total_price"]').val(total_price.toFixed(2));
        return false;
      });
    });


    //应付金额
    var total;
    //回显应付金额
    $(document).ready(function () {
      total = localStorage.getItem('total');
      if (total !== null) {
        // 使用 parseFloat() 方法将字符串转换为数字类型
        total = parseFloat(total);

        // 检查 total 的值是否是数字类型
        if (typeof total === 'number' && !isNaN(total)) {
          $('.price').text('¥ ' + total.toFixed(2));
        } else {
          // 如果不是数字类型，则给 total 赋一个默认值
          total = 0;
          $('.price').text('¥ ' + total.toFixed(2));
        }
      }
    });
    // 实付金额
    var value;
    //回显实付金额
    $(document).ready(function() {
      value = localStorage.getItem('inputValue');
      if (value !== null) {
        // 将字符串转换为浮点数
        value = parseFloat(value);

        // 检查输入的值是否为数字类型且非NaN
        if (typeof value === 'number' && !isNaN(value)) {
          // 格式化输出
          var formattedValue = '¥ ' + value.toFixed(2);
          $('.output').text(formattedValue);
        } else {
          // 如果输入值不是数字类型或者是NaN，则给默认值 0.00
          value = 0;
          $('.output').text('¥ 0.00');
        }
      } else {
        // 如果 localStorage 中没有存储输入值，则给默认值 0.00
        value = 0;
        $('.output').text('¥ 0.00');
      }
    });


      function confirmPayment() {
        // 获取选中的支付方式
        var payment_method = $('input[name="payment_method"]:checked').val();
        if (payment_method === 'alipay'){
          // 获取备注信息
          var remarks = $('textarea[name="remarks"]').val();
          $.ajax({
            type: "POST",
            url: "/AlipayTest.do",
            data: "value="+value+"&total="+total+"&remarks="+remarks+"&phone="+phone,
            success:function (response){
              // console.log(response)
              // 插入页面中
              $('body').append(response);
              // 触发表单提交事件
              $('#alipaysubmit').trigger('submit');

            },
            error: function () {
              console.log('请求失败');
            },
          })
      }else if (payment_method === 'cash'){
          // 获取备注信息
          var remarks = $('textarea[name="remarks"]').val();
          // 组装需要携带的数据
          var requestData = {
            action: 'caSay',
            payment_method: payment_method,
            value: value,
            total: total,
            remarks: remarks,
            phone: phone,
          };
          console.log(requestData)
        // 发送 AJAX 请求
        $.ajax({
          type: "POST",
          url: "/Merchandise.do",
          data: requestData,
          dataType: "json", // 返回的响应类型为 JSON 格式
          success: function (response) {
            if (response.success) { // 如果现金支付成功，则提示用户
              alert(response.message);
              if (response.redirect) { // 如果需要重定向，跳转到指定页面
                window.top.location.href = response.redirect;
              }
            } else { // 否则提示支付失败
              alert("现金支付失败，请重试！");
            }
          },
          error: function (jqXHR, textStatus, errorThrown) {
            // 请求失败后的处理逻辑
            alert("请求现金支付接口失败，请稍后重试！");
          }
        });
      }
    }

  </script>
</body>

</html>