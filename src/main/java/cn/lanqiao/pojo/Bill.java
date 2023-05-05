package cn.lanqiao.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class Bill {
    private Integer id;
    private String title;
    private String unit;
    private Integer num;
    private Integer money;
    private Integer providerid;
    private Integer ispay;
    private Integer isdelete;
    private String providerName;//扩展字段，为了数据显示

    public Bill() {
    }

    public Bill(Integer id, String title, String unit, Integer num, Integer money, Integer providerid, Integer ispay, Integer isdelete) {
        this.id = id;
        this.title = title;
        this.unit = unit;
        this.num = num;
        this.money = money;
        this.providerid = providerid;
        this.ispay = ispay;
        this.isdelete = isdelete;
    }


}


