package cn.lanqiao.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginLog {
    private Integer id;//id
    private String username;//用户名
    private String address;//地址
    private String ip;//ip地址
    private String logintime;//登录时间
    private Integer isdelete;//状态

}
