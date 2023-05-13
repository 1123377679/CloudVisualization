import cn.lanqiao.pojo.User;
import cn.lanqiao.service.MemberService;
import cn.lanqiao.service.impl.MemberServiceImpl;

import java.util.ArrayList;

public class Te {
    public static void main(String[] args) {
        MemberService memberService = new MemberServiceImpl();
        User user = new User();
        ArrayList<Integer> totalCount = memberService.getTotalCount(user);
        System.out.println(totalCount);
    }
}
