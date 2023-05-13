package cn.lanqiao.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BehaviorLog {
    private int id;
    private String username;
    private String type;
    private String description;
    private String model;
    private String ip;
    private String operationtime;
    private String result;
}
