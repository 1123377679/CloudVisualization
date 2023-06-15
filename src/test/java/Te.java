import cn.lanqiao.pojo.Merchan;
import cn.lanqiao.pojo.User;
import cn.lanqiao.service.MemberService;
import cn.lanqiao.service.OrderService;
import cn.lanqiao.service.impl.MemberServiceImpl;
import cn.lanqiao.service.impl.OrderServicelmpI;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Te {
    public static void main(String[] args) {
//        MemberService memberService = new MemberServiceImpl();
        OrderService orderService = new OrderServicelmpI();
        List<Integer> countByOrder = orderService.getCountByOrder();
        System.out.println(countByOrder);
    }
}
