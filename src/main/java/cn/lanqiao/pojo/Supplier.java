package cn.lanqiao.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Supplier {
    private Integer id;
    private String name;
    private String linkman;
    private String phone;
    private String address;
    private String fax;
    private String description;
    private Integer isdelete;

    public Supplier(Integer id, String name, String linkman, String phone, String address, String fax, String description, Integer isdelete) {
        this.id = id;
        this.name = name;
        this.linkman = linkman;
        this.phone = phone;
        this.address = address;
        this.fax = fax;
        this.description = description;
        this.isdelete = isdelete;
    }
}
