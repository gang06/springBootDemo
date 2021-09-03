package com.example.demo;

import com.example.demo.utils.EncryptionUtil;

/**
 * @Description:
 * @author gang
 * @date 2021/8/12 23:13
 * @version 1.0
 */
public class SignTests {
    public static void main(String[] args) throws Exception {
        /*EncryptionUtil dig = new EncryptionUtil();
        String privatekeyStr = dig.getFileByFilePath("privatekey");
        String publickeyStr = dig.getFileByFilePath("publickey");
        String string = "哈哈哈";
        String sign = EncryptionUtil.sign(string, privatekeyStr);
        System.out.println("签名：");
        System.out.println(sign);
        System.out.println("----");
        boolean flag = EncryptionUtil.verify(sign, string, publickeyStr);
        System.out.println("验证签名结果:");
        System.out.println(flag);*/

        EncryptionUtil.generatePubPrivateKey();
    }
}
