package cn.lanqiao.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.sql.Timestamp;


//getset方法
@Data
//参数的构造函数
@NoArgsConstructor
//有参的构造函数
@AllArgsConstructor
public class Merchan {
    private int id;
    private String orderNo;
    private String memberName;
    private String orderType;
    private BigDecimal totalAmount;
    private Integer paymentStatus;
    private Timestamp createdAt;
    private BigDecimal paymentAmount;
    private Integer isdelete;
}
