import cn.lanqiao.service.BillService;
import cn.lanqiao.service.MemberService;
import cn.lanqiao.service.SupplierService;
import cn.lanqiao.service.impl.BillServiceImpl;
import cn.lanqiao.service.impl.MemberServiceImpl;
import cn.lanqiao.service.impl.SupplierServiceImpl;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class SupperMarketTest {
    MemberService memberService = new MemberServiceImpl();
    SupplierService supplyer = new SupplierServiceImpl();
    BillService billService = new BillServiceImpl();
    public static void main(String[] args) {
        SupplierService supplierService = new SupplierServiceImpl();
        Map<String, Integer> billCountBySupplier = supplierService.getBillCountBySupplier();
        for (Map.Entry<String, Integer> entry : billCountBySupplier.entrySet()) {
//            System.out.println("供应商: " + entry.getKey() + "，订单数量: " + entry.getValue());
        }

    }
    @Test
    public void test(){
        List<Integer> list = memberService.selectAge();
//        System.out.println(list);
    }
    @Test
    public void test2(){
        int i = memberService.checkPassword(3,"12345");
        System.out.println(i);
    }
    @Test
    public void test3(){
        int i = billService.getbillsum("旺旺");
        System.out.println(i);
    }
}
