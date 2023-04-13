package cn.lanqiao.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//getset方法
@Data
//参数的构造函数
@NoArgsConstructor
//有参的构造函数
@AllArgsConstructor


public class User {
    private Integer id;
    private String username;
    private String password;
    private Integer sex;
    private String birthday;
    private String phone;
    private String address;
    private Integer type;
    private Integer isdelete;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
