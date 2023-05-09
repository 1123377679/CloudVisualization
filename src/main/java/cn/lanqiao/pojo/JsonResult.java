package cn.lanqiao.pojo;

import lombok.Data;

/**
 * layui后端返回数据到前端的工具类
 */
@Data
public class JsonResult<T> {
    private T data;
    //状态码
    private String code;
    //内容
    private String msg;
    /*
     * * 若没有数据返回，默认状态码为0，提示信息为：操作成功！  写死的
     */
    public JsonResult() {
        this.code = "0";
        this.msg = "操作成功！";
    }

    /**
     * 若没有数据返回，可以人为指定状态码和提示信息
     * @param code
     * @param msg
     */
    public JsonResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    /**
     * 有数据返回时，状态码为0，默认提示信息为：操作成功！
     * @param data
     */
    public JsonResult(T data) {
        this.data = data;
        this.code = "0";
        this.msg = "操作成功！";
    }

    /**
     * 有数据返回，状态码为0，人为指定提示信息
     * @param data
     * @param msg
     */
    public JsonResult(T data, String msg) {
        this.data = data;
        this.code = "0";
        this.msg = msg;
    }


    public JsonResult(T data, String code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }
}
