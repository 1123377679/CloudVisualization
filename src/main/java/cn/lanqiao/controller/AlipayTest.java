package cn.lanqiao.controller;

import cn.lanqiao.dao.AlipayDao;
import cn.lanqiao.pojo.Merchan;
import cn.lanqiao.service.AlipayService;
import cn.lanqiao.service.MemberService;
import cn.lanqiao.service.impl.AlipayServiceImpl;
import cn.lanqiao.service.impl.MemberServiceImpl;
import cn.lanqiao.utils.OrderUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.sun.scenario.effect.impl.sw.java.JSWBlend_SRC_OUTPeer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Servlet implementation class AlipayTest
 */
@WebServlet(value = "/AlipayTest.do")
public class AlipayTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MemberService memberService = new MemberServiceImpl();
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AlipayTest() {
		super();
		// TODO Auto-generated constructor stub
	}
	AlipayService alipayService = new AlipayServiceImpl();
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
		//设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(AlipayConfig.return_url);
		alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
		//前端传过来的金额
		//随机生成一个11位的订单号，而且不能重复
		//取前端输入的备注信息

		//商户订单号，商户网站订单系统中唯一订单号，必填
		String s = OrderUtils.generateOrderId();
		String out_trade_no = s;
		//付款金额，必填
		String total = req.getParameter("total");
		String total_amount = total;
		//会员手机号
		String phone = req.getParameter("phone");
		//校验手机号是否存在
		int i = memberService.checkUserPhone(phone);
		String userNameByPhone = "";
		if (i>=1){
			//手机号存在,拿会员名字
			userNameByPhone = memberService.getUserNameByPhone(phone);
		}else {
			userNameByPhone = "游客";
		}
		//订单名称，必填
		String subject = userNameByPhone;
		//商品描述，可空
		String remarks = req.getParameter("remarks");
		String body = remarks;

		alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
				+ "\"total_amount\":\"" + total_amount + "\","
				+ "\"subject\":\"" + subject + "\","
				+ "\"body\":\"" + body + "\","
				+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

		//请求
		String result;
		try {
			result = alipayClient.pageExecute(alipayRequest).getBody();
			resp.setContentType("text/html;charset=" + AlipayConfig.charset);
			resp.getWriter().write(result);//直接将完整的表单html输出到页面
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (AlipayApiException e) {
			e.printStackTrace();
			resp.getWriter().write("捕获异常出错");//直接将完整的表单html输出到页面
			resp.getWriter().flush();
			resp.getWriter().close();
		}



			//支付金额
			String value1 = req.getParameter("value");
			//生成时间
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			//写一个sql
			//添加：将订单编号和会员名称，订单类型(固定),总金额就是付款金额，已支付，生成时间，获取当前时间New Date
			//转换数据类型
			BigDecimal totalAmount = new BigDecimal(total);
			BigDecimal Amount = new BigDecimal(value1);
			Merchan merchan = new Merchan(null, s, subject, remarks, totalAmount, 1, timestamp, Amount, 0);
			System.out.println(merchan);
			int add = alipayService.add(merchan);
			System.out.println(add);


	}
}
