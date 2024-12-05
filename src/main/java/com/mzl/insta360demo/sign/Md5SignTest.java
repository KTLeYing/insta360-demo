package com.mzl.insta360demo.sign;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * @Description: MD5签名算法测试
 * @Author: mzl
 */
public class Md5SignTest {

    public static String fileMD5Sign(String filename) {
        String md5value;
        Path path = Paths.get(filename);
        try {
            byte[] content = Files.readAllBytes(path);
            // 计算 MD5 值
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(content);
            // 将 MD5 值用 Base64 编码输出
            md5value = Base64.getEncoder().encodeToString(digest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return md5value;
    }

    public static String fileHmacMD5Sign(String filename) {
        Path path = Paths.get(filename);
        String md5value;
        try {
            byte[] content = Files.readAllBytes(path);
            String contentStr = new String(content, StandardCharsets.UTF_8);
            String sign = signHex("WJRIodQycPxOFuSTtJCaNObhLxgMDxvW", contentStr);
            md5value = new String(Base64.getEncoder().encode(sign.getBytes("UTF-8")), "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return md5value;
    }

    public static String signHex(String secret, String message) {
        try {
            Mac md5_HMAC = Mac.getInstance("HmacMD5");
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacMD5");
            md5_HMAC.init(secretKey);
            byte[] hash = md5_HMAC.doFinal(message.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String fileName = "/Users/mazhenle/Desktop/Work/Insta360/TestFile/m3u8/master.m3u8";

        String md5Hash = fileMD5Sign(fileName);
        System.out.println("【1】FileMD5Sign of '" + fileName + "' is: " + md5Hash);

        String hmacMd5Hash = fileHmacMD5Sign(fileName);
        System.out.println("【1】HmacMD5Sign of '" + fileName + "' is: " + hmacMd5Hash);

    }

}