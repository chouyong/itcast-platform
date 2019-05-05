package com.itcast.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * 摘要签名工具类
 */
public class DigestUtils {

    /**
     * 构造方法（静态类禁止创建）
     */
    private DigestUtils() {
    }

    /**
     * MD5签名（Base64 URL 编码）
     *
     * @param text 文本
     * @return String 签名
     */
    public static String md5Base64Url(String text) {
        return md5Base64Url(text.getBytes());
    }

    /**
     * MD5签名（Base64 URL 编码）
     *
     * @param data 数据
     * @return String 签名
     */
    public static String md5Base64Url(byte[] data) {
        return digest(data, "MD5");
    }

    /**
     * SHA1签名（Base64 URL 编码）
     *
     * @param text 文本
     * @return String 签名
     */
    public static String sha1Base64Url(String text) {
        return sha1Base64Url(text.getBytes());
    }

    /**
     * SHA1签名（Base64 URL 编码）
     *
     * @param data 数据
     * @return String 签名
     */
    public static String sha1Base64Url(byte[] data) {
        return digest(data, "SHA-1");
    }

    /**
     * SHA256签名（Base64 URL 编码）
     *
     * @param text 文本
     * @return String 签名
     */
    public static String sha256Base64Url(String text) {
        return sha256Base64Url(text.getBytes());
    }

    /**
     * SHA256签名（Base64 URL 编码）
     *
     * @param data 数据
     * @return String 签名
     */
    public static String sha256Base64Url(byte[] data) {
        return digest(data, "SHA-256");
    }

    /**
     * SHA512签名（Base64 URL 编码）
     *
     * @param text 文本
     * @return String 签名
     */
    public static String sha512Base64Url(String text) {
        return sha512Base64Url(text.getBytes());
    }

    /**
     * SHA512签名（Base64 URL 编码）
     *
     * @param data 数据
     * @return String 签名
     */
    public static String sha512Base64Url(byte[] data) {
        return digest(data, "SHA-512");
    }


    /**
     * 签名（Base64 URL 编码）
     *
     * @param data      数据
     * @param algorithm 算法
     * @return String 签名
     */
    private static String digest(byte[] data, String algorithm) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            digest.update(data);
            return Base64.getUrlEncoder().encodeToString(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String text = "测试文本123456~!@#$%^&*()_+=[]';,./abcdefg";
        System.out.println("md5=" + md5Base64Url(text));
        System.out.println("sha1=" + sha1Base64Url(text));
        System.out.println("sha256=" + sha256Base64Url(text));
        System.out.println("sha512=" + sha512Base64Url(text));
    }
}
