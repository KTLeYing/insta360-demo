package com.mzl.insta360demo.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @Description: Md5加密工具类
 * @Author: mzl
 */
public class Md5Util1 {

    /**
     * 生成随机盐值
     *
     * @return 盐
     * @throws NoSuchAlgorithmException
     */
    public static byte[] generateSalt() throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * 字符串进行加盐MD5加密
     *
     * @param input 输入字符串
     * @param salt 盐
     * @return 加密后的盐
     * @throws NoSuchAlgorithmException
     */
    public static String getSaltedMD5(String input, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(salt);
        byte[] hashedBytes = md.digest(input.getBytes());

        // 将盐和加密后的数据拼接起来，以便验证时使用相同的盐值
        byte[] combined = new byte[hashedBytes.length + salt.length];
        System.arraycopy(hashedBytes, 0, combined, 0, hashedBytes.length);
        System.arraycopy(salt, 0, combined, hashedBytes.length, salt.length);

        return Base64.getEncoder().encodeToString(combined);
    }

    /**
     * 字符串进行加盐MD5加密校验
     *
     * @param input 输入
     * @param hashedString 哈希字符串
     * @return 加盐MD5加密
     * @throws NoSuchAlgorithmException
     */
    public static boolean verifySaltedMD5(String input, String hashedString) throws NoSuchAlgorithmException {
        byte[] decodedHash = Base64.getDecoder().decode(hashedString);

        // 分离出加密数据和盐
        byte[] storedSalt = new byte[16];
        System.arraycopy(decodedHash, decodedHash.length - 16, storedSalt, 0, 16);

        // 使用存储的盐和输入的字符串进行加密
        String inputHash = getSaltedMD5(input, storedSalt);

        // 比较两次加密结果是否相同
        return inputHash.equals(hashedString);
    }

    public static void main(String[] args) {
        String originalString = "HelloWorld";

        try {
            // 生成随机盐值
            byte[] salt = generateSalt();
            System.out.println("Salt is: " + salt.toString());

            // 对原始字符串进行加盐MD5加密
            String hashedString = getSaltedMD5(originalString, salt);
            System.out.println("Salted MD5 Hash: " + hashedString);

            // 验证
            boolean isValid = verifySaltedMD5(originalString, hashedString);
            System.out.println("Verification Result: " + isValid);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


}