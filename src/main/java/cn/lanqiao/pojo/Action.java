package cn.lanqiao.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Action {
    private String type;
    private String description;
    private String result;

    public Action(String type, String description) {
        this.type = type;
        this.description = description;
    }
}
