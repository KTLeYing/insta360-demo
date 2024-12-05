package com.mzl.insta360demo.util;

import cn.hutool.crypto.SecureUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Description: TODO
 * @Author: mzl
 */
public class Md5Util {

    public static String generateMD5(String input) {
        try {
            // 获取MessageDigest实例，并指定使用MD5算法
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 将输入的字符串转换为字节数组
            byte[] messageDigest = md.digest(input.getBytes());

            // 创建一个StringBuilder来存储生成的哈希值
            StringBuilder sb = new StringBuilder();

            // 将每个字节转换为两位的16进制数，并追加到StringBuilder
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }

            // 返回生成的MD5哈希值
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String input = "HelloWorld";
        String md5Hash = generateMD5(input);
        System.out.println("MD5 Hash of '" + input + "' is: " + md5Hash);
    }

    public static void main1(String[] args) {
        String input = "HelloWorld";
        String md5Hash = SecureUtil.md5(input);
        System.out.println("MD5 哈希值：" + md5Hash);
    }


}