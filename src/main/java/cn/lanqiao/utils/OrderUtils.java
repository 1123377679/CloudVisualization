package cn.lanqiao.utils;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;

public class OrderUtils {

    // 随机订单号前缀
    private static final String ORDER_PREFIX = "2";

    // 订单号位数
    private static final int ORDER_LENGTH = 10;

    // 随机数生成器
    private static final Random RANDOM = new Random();

    // 已使用的订单号集合
    private static final Set<String> USED_ORDERS = new HashSet<String>();

    /**
     * 生成一个随机订单号，以2开头，共11位
     * @return 订单号字符串
     */
    public static synchronized String generateOrderId() {
        String orderId = "";
        while (orderId.length() < ORDER_LENGTH) {
            orderId += RANDOM.nextInt(10);
        }
        orderId = ORDER_PREFIX + orderId;
        if (USED_ORDERS.contains(orderId)) {
            return generateOrderId();   // 如果订单号已被使用，则重新生成一个新的订单号
        } else {
            USED_ORDERS.add(orderId);   // 将订单号添加到已使用集合中
            return orderId;
        }
    }
}