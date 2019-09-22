package com.yxm.springbootapi.encryption;

import com.alibaba.fastjson.JSONObject;
import com.yxm.springbootapi.oauth2.utils.HttpClientUtils;

import java.net.URLEncoder;

/**
 * @author CAESAR
 * @Classname Test001
 * @Description TODO
 * @Date 2019-09-22 20:44
 */
public class Test001 {


    public static void main(String[] args) throws Exception {
        // java 提供http协议 特殊字符转换类
        String encode = URLEncoder.encode("1+1");
        System.out.println(encode);

        testDES();




    }

    /**
     * 测试案例2   对称加密之DES
     */
    private static void testDES() throws Exception {
        //  待加密字符串
        String str = "caesar";
        // 密钥   长度要是8的倍数
        String key = "85968956";

        // 加密
        byte[] encrypt = DesUtils.encrypt(str.getBytes(), key);
        System.out.println("加密后：" + new String(encrypt));

        // 解密
        byte[] decrypt = DesUtils.decrypt(encrypt, key);
        System.out.println("解密后：" + new String(decrypt));


    }


    /**
     * 测试案例1 URL特殊字符转码
     */
    private static void testUrl(){
        String userName = "1+1";
        //  这种传递参数会导致后台服务器拿到参数错误  且错误结果为1 1
        JSONObject jsonObject = HttpClientUtils.httpGet("http://localhost:8080/testUrl?userName="+userName);
        System.out.println(jsonObject);

        // 所以要对传递的参数进行处理  就是使用java提供的特殊字符转换类实现   这样后台就能拿到正确参数
        JSONObject jsonObject1 = HttpClientUtils.httpGet("http://localhost:8080/testUrl?userName="+URLEncoder.encode(userName));
        System.out.println(jsonObject1);
    }


}
