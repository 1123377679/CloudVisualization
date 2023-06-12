package cn.lanqiao.controller;

import java.io.FileWriter;
import java.io.IOException;

/**
 * 支付配置
 */
public class AlipayConfig {


    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "9021000122676036";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCslqIF4tibvotK3+gVUQoQDUK5l91M+hnk3E3XgUTM3u6mEHWQU0W3mOijpY1ftXuyJd3zJK54mwZ9RiHVuqLTZWjjYmCM65PrTN72RHKoW9Mf/rWc+TbBpSdNvTU6XVakSNvEbbTS9h2k85IE/+xzgftipEGn0XvngcngcDQ7IXXD00KQ46cRlqErzdljuGBZI392udIK5l0jSbon9rzxJflvwUJtVzUrrol67ZZk+TryruV3Swy+Nvx9NMkQRGqGKtcgtQUfgt1r/SJNZUDdhd3IEr+D1VGHApqpswLwuKG4etXCJPOPS+OGzB2/PBo88V+kM2EpHKP1mn1pmw/9AgMBAAECggEAVZJCN0ToPSBWlAM8YQVTRe4tPTQM55PBrvWEKuvPHv0RuojCoZKIKKXKAwmcdO3YrNWVnG59ixLiPs69C+mDveiz/rnSQUy0hKASlGwPpWYR4fU6OuBdw9mNCUBc2dlZkg8F1/jFBSw+OoEBzzTNGeBCsf/bBcgZ9Qkm/P7lBsOZf1NemDT17pwDSb6/3oUodcDzI8cOz6i61yTw6eVI0ol9p9aVWAhDNWaUEnIlLMsqXOPnAiH1SVOHeHplv0gwBO4ZhBc3Lh8n2M6lcI8pWnuICwd5VtKNbCOXloiQhtoG5OAX0ONQiGJHBEiFfQ9W6zMV8B/orO12gQVPsurLCQKBgQDfFnY/Gk8cFdgUjTQ37khoMlPGcx6kBDHIhuLY1rnspC7fJeGXkoYeCWvtx+BDAlZpCLGUbwwYFEa1ESFZqq6n1a9crOTkRk6yeOmMdQLiv7MpJ+SKqoEo3iaqJAVX+4q516SkyTrVuQvM9abloR8cESOcuNOu6pCMab8FwEw1ewKBgQDGDOvVWLV9OyprvLH6WS1rvGkjomKr4rBmqk0R86whJmPmzP/hcnJ2FjEzfe8voDXyi7sDTcnjOgsfSicVALn9pHF4IniK0V66HZ2o6syVqW/L2MRNaVCYivnxSxPruPvxb32V6wonk7QD7Bzes4s2Jo6FRPvS2q4JWH3h5EAK5wKBgQDcupDM5u8XTtW5n+zm2jFyRzl0xTqv9iZ7zlptSigz6E8oI4cOPb/NwGy1nGpcYte0nRF3WFiTCpzbUtO4sJSXI2qbO8ZNxvLey5vpiW0NhbdzJTGPshyRnxyKg5F1+EDOmHsjQKeiB7GRXg2AbuclEiA2VPm9vADBrW9/9UByOQKBgQCwL8KFc3tRQxdD7QPwEPFlTKHD/6wD+ZCwZIRXmgzhGMP7CRgBucy13Jw8kKX+GmqjsUbXc5ZMvNA0L8WA27qaOHZxR0kxRX0d2UbuhKMn6Sn5kIdhznTSn6Am1oGMTG5lpKd2fTf1aWLVsnEkdVk8/SQAzxOvyrbUvZKPKH0ItQKBgGPbLCk4xXlegxK5ZwG/IWzP/xxNLcmwO+4DULyrwmkWgsfGe3JNVo3HvjyP0p+DaKV3aoDTcpuWu/wQtjlZKf2Ccj+OnHxfRl001fcTFVsuZY6PHfjHUiyItKZSNmQc4Ax6+9mWb/GzvUHdGVk878LkGkZih6rmrEdwIe0ZdKEn";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgqRZI7yKT59Le0AAGEDOcnFfuouGGnvhskyH2mBHjn5SwwEWgQTLR6GAelECpEDNp0NhroO/Ac6AWAg8RIdwE5HMZXQDQyXSj8JqIyNoQ08nDTxsnpTW7vdavN98vYmA1qPZeIMm/FqfxsdeuUf1+VkSQWaZHTraO73jr20Oi0xuBFc4dfHY9LPqcMufqBSbAMKDWtb4MghV8oGN8Lg8JXHzYE3gCaw09sMxwHnjbb65wcbz29wW+iT0fCDU59e2auNHclbzvxArXfL1Q3pS4TTpHIu7v8Y5gWnZKLevDF/rpik3VpmbD8tU5z2ah0BTAkbabwbEfCfU7MJRmowbZwIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://localhost:8080/cashRegister.jsp";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
        public static String return_url = "http://localhost:8080/cashRegister.jsp?iframe=true&target=_top";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "http://localhost:8080/cashRegister.jsp?iframe=true&target=_top";



    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

