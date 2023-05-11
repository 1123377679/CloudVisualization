import cn.lanqiao.service.SupplierService;
import cn.lanqiao.service.impl.SupplierServiceImpl;
import org.junit.Test;

import java.util.Map;

public class SupperMarketTest {
    public static void main(String[] args) {
        SupplierService supplierService = new SupplierServiceImpl();
        Map<String, Integer> billCountBySupplier = supplierService.getBillCountBySupplier();
        for (Map.Entry<String, Integer> entry : billCountBySupplier.entrySet()) {
            System.out.println("供应商: " + entry.getKey() + "，订单数量: " + entry.getValue());
        }

    }

}
