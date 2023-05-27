package cn.lanqiao.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Commodity {
    private Integer id;
    private String name;
    private String barcode;
    private BigDecimal price;
    private Boolean status;

    public Commodity(Integer id, String name, String barcode, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.barcode = barcode;
        this.price = price;
    }
}
