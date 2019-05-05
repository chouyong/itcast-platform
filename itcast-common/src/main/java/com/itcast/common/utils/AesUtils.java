package com.itcast.common.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * AES加解密处理器
 * <p>
 * 编码采用基于BASE64的URL安全模式标准
 * 默认采用UTF-8字符集
 * </p>
 */
public class AesUtils {

    // 加密算法 RSA
    public static final String ALGORITHM_AES = "AES";
    // 默认密钥长度
    public static final int DEFAULT_KEY_LENGTH = 128;
    // 默认字符集
    public static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 构造方法（静态类禁止创建）
     */
    private AesUtils() {
    }

    /**
     * 解析密钥
     *
     * @param password 密码
     * @return SecretKeySpec 密钥
     * @throws Exception 异常错误
     */
    public static SecretKeySpec parseSecretKey(String password) throws Exception {
        return parseSecretKey(password, DEFAULT_CHARSET);
    }

    /**
     * 解析密钥
     *
     * @param password 密码
     * @param charset  字符集
     * @return SecretKeySpec 密钥
     * @throws Exception 异常错误
     */
    public static SecretKeySpec parseSecretKey(String password, String charset) throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM_AES);
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(password.getBytes());
        kg.init(DEFAULT_KEY_LENGTH, random);
        SecretKey secretKey = kg.generateKey();
        return new SecretKeySpec(secretKey.getEncoded(), ALGORITHM_AES);
    }

    /**
     * 加密处理
     *
     * @param data     数据
     * @param password 密码
     * @return String 密文
     * @throws Exception 异常错误
     */
    public static String encrypt(String data, String password) throws Exception {
        return encrypt(data, password, DEFAULT_CHARSET);
    }

    /**
     * 加密处理
     *
     * @param data     数据
     * @param password 密码
     * @param charset  字符集
     * @return String 密文
     * @throws Exception 异常错误
     */
    public static String encrypt(String data, String password, String charset) throws Exception {
        Cipher cipher = getCipher(Cipher.ENCRYPT_MODE, parseSecretKey(password, charset));
        byte[] temp = data.getBytes(charset);
        temp = cipher.doFinal(temp);
        return Base64.getUrlEncoder().encodeToString(temp);
    }

    /**
     * 解密处理
     *
     * @param data     数据
     * @param password 密码
     * @return String 密文
     * @throws Exception 异常错误
     */
    public static String decrypt(String data, String password) throws Exception {
        return decrypt(data, password, DEFAULT_CHARSET);
    }

    /**
     * 解密处理
     *
     * @param data     数据
     * @param password 密码
     * @param charset  字符集
     * @return String 密文
     * @throws Exception 异常错误
     */
    public static String decrypt(String data, String password, String charset) throws Exception {
        Cipher cipher = getCipher(Cipher.DECRYPT_MODE, parseSecretKey(password, charset));
        byte[] temp = data.getBytes(charset);
        temp = Base64.getUrlDecoder().decode(temp);
        temp = cipher.doFinal(temp);
        return new String(temp, charset);
    }

    /**
     * 获取加解密处理器
     *
     * @param mode 模式
     * @param key  密钥
     * @return Cipher 加解密处理器
     * @throws Exception 异常错误
     */
    private static Cipher getCipher(int mode, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM_AES);
        cipher.init(mode, key);
        return cipher;
    }

    /**
     * 测试类
     *
     * @param args 参数
     * @throws Exception 异常
     */
    public static void main(String[] args) throws Exception {
        String password = "ABC@xyz.com";
        System.out.println(">>>密码:\r\n" + password);

        String data = "中文测试内容文本AES：1234567890\r\n!@#$%^&*()_+-=<>,.;':[]{}\\\"~`";
        System.out.println("\r\n>>>明文内容：" + data.getBytes().length + "bytes\r\n" + data);

        String encodedData = AesUtils.encrypt(data, password);
        System.out.println("\r\n>>>加密后文字：\r\n" + encodedData);

        String decodedData = AesUtils.decrypt(encodedData, password);
        System.out.println("\r\n>>>解密后文字: \r\n" + decodedData);
    }
}
