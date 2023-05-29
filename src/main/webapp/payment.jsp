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
          <input type="radio" name="payment_method" value="cash" title="" checked>
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
          <label class="layui-form-label">商品名称</label>
          <div class="layui-input-block">
            <input type="text" name="product_name" required lay-verify="required" placeholder="请输入商品名称"
              autocomplete="off" class="layui-input">
          </div>
        </div>
        <div class="layui-form-item">
          <label class="layui-form-label">价格</label>
          <div class="layui-input-block">
            <input type="text" name="price" required lay-verify="required|number" placeholder="请输入商品价格"
              autocomplete="off" class="layui-input">
          </div>
        </div>
        <div class="layui-form-item">
          <label class="layui-form-label">数量</label>
          <div class="layui-input-block">
            <input type="text" name="quantity" required lay-verify="required|number" placeholder="请输入商品数量"
              autocomplete="off" class="layui-input">
          </div>
        </div>
        <div class="layui-form-item">
          <label class="layui-form-label">实付金额</label>
          <div class="layui-input-block">
            <input type="text" name="pay_amount" required lay-verify="required|number" placeholder="请输入实付金额"
              autocomplete="off" class="layui-input">
          </div>
        </div>
        <div class="layui-form-item">
          <label class="layui-form-label">备注信息</label>
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
      <button class="layui-btn pay-button">确认收款</button>
    </div>
  </div>

  <!-- 引入layui核心库 -->
  <script src="https://cdn.staticfile.org/layui/2.5.6/layui.min.js"></script>

  <!-- 自定义JavaScript代码 -->
  <script>
    layui.use(['form', 'layer', 'jquery'], function () {
      var form = layui.form;
      var layer = layui.layer;
      var $ = layui.jquery;

      // 监听添加会员按钮
      $('.member-add').on('click', function () {
        var phone = $('input[name="member_phone"]').val();
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

      // 弹出支付结算页面
      $('.pay-button').on('click', function () {
        layer.open({
          type: 2,
          title: '支付结算',
          shadeClose: true,
          shade: [0.8, '#393D49'],
          area: ['60%', '80%'],
          content: 'payment-checkout.html'
        });
      });
    });
  </script>

</body>

</html>