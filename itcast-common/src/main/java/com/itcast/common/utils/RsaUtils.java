package com.itcast.common.utils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA加解密处理器
 * <p>
 * 编码采用基于BASE64的URL安全模式标准
 * 公钥采用X509规格，私钥采用PKCS8规格
 * 超过RSA标准密文长度限制后采用分段加密
 * 默认采用2048密钥长度，UTF-8字符集
 * </p>
 */
public class RsaUtils {

    // 加密算法 RSA
    public static final String ALGORITHM_RSA = "RSA";
    // 默认密钥长度
    public static final int DEFAULT_KEY_LENGTH = 2048;
    // 默认字符集
    public static final String DEFAULT_CHARSET = "UTF-8";
    // 公钥键值
    public static final String KEY_PUBLIC = "publicKey";
    // 私钥键值
    public static final String KEY_PRIVATE = "privateKey";

    /**
     * 构造方法（静态类禁止创建）
     */
    private RsaUtils() {
    }

    /**
     * 创建公私密钥对
     *
     * @return Map 公私密钥对
     */
    public static Map<String, String> createKeys() {
        return createKeys(DEFAULT_KEY_LENGTH);
    }

    /**
     * 创建公私密钥对
     *
     * @param keySize 密钥长度
     * @return Map 公私密钥对
     */
    public static Map<String, String> createKeys(int keySize) {
        //为RSA算法创建一个KeyPairGenerator对象
        KeyPairGenerator kpg = null;
        try {
            kpg = KeyPairGenerator.getInstance(ALGORITHM_RSA);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        //初始化KeyPairGenerator对象,密钥长度
        kpg.initialize(keySize);
        //生成密匙对
        KeyPair keyPair = kpg.generateKeyPair();
        //得到公钥
        Key publicKey = keyPair.getPublic();
        String publicKeyStr = Base64.getUrlEncoder().encodeToString(publicKey.getEncoded());
        //得到私钥
        Key privateKey = keyPair.getPrivate();
        String privateKeyStr = Base64.getUrlEncoder().encodeToString(privateKey.getEncoded());
        Map<String, String> keyPairMap = new HashMap<String, String>();
        keyPairMap.put(KEY_PUBLIC, publicKeyStr);
        keyPairMap.put(KEY_PRIVATE, privateKeyStr);
        return keyPairMap;
    }

    /**
     * 解析公钥文本
     *
     * @param publicKey 公钥字符串
     * @throws Exception 异常错误
     */
    public static RSAPublicKey parsePublicKey(String publicKey) throws Exception {
        //通过X509编码的Key指令获得公钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        byte[] temp = Base64.getUrlDecoder().decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(temp);
        RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
        return key;
    }

    /**
     * 解析私钥文本
     *
     * @param privateKey 私钥字符串
     * @throws Exception 异常错误
     */
    public static RSAPrivateKey parsePrivateKey(String privateKey) throws Exception {
        //通过PKCS#8编码的Key指令获得私钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        byte[] temp = Base64.getUrlDecoder().decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(temp);
        RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
        return key;
    }

    /**
     * 公钥加密
     *
     * @param data      数据
     * @param publicKey 公钥
     * @return String 密文
     * @throws Exception 异常错误
     */
    public static String publicEncrypt(String data, String publicKey) throws Exception {
        return publicEncrypt(data, publicKey, DEFAULT_CHARSET);
    }

    /**
     * 公钥加密
     *
     * @param data      数据
     * @param publicKey 公钥
     * @param charset   字符集
     * @return String 密文
     * @throws Exception 异常错误
     */
    public static String publicEncrypt(String data, String publicKey, String charset) throws Exception {
        RSAPublicKey key = parsePublicKey(publicKey);
        Cipher cipher = getCipher(Cipher.ENCRYPT_MODE, key);
        byte[] temp = data.getBytes(charset);
        temp = splitCodec(cipher, Cipher.ENCRYPT_MODE, temp, key.getModulus().bitLength());
        return Base64.getUrlEncoder().encodeToString(temp);
    }

    /**
     * 公钥解密
     *
     * @param data      数据
     * @param publicKey 公钥
     * @return String 密文
     * @throws Exception 异常错误
     */
    public static String publicDecrypt(String data, String publicKey) throws Exception {
        return publicDecrypt(data, publicKey, DEFAULT_CHARSET);
    }

    /**
     * 公钥解密
     *
     * @param data      数据
     * @param publicKey 公钥
     * @param charset   字符集
     * @return String 密文
     * @throws Exception 异常错误
     */
    public static String publicDecrypt(String data, String publicKey, String charset) throws Exception {
        RSAPublicKey key = parsePublicKey(publicKey);
        Cipher cipher = getCipher(Cipher.DECRYPT_MODE, key);
        byte[] temp = Base64.getUrlDecoder().decode(data);
        temp = splitCodec(cipher, Cipher.DECRYPT_MODE, temp, key.getModulus().bitLength());
        return new String(temp, charset);
    }

    /**
     * 私钥加密
     *
     * @param data       数据
     * @param privateKey 私钥
     * @return String 密文
     * @throws Exception 异常错误
     */
    public static String privateEncrypt(String data, String privateKey) throws Exception {
        return privateEncrypt(data, privateKey, DEFAULT_CHARSET);
    }

    /**
     * 私钥加密
     *
     * @param data       数据
     * @param privateKey 私钥
     * @param charset    字符集
     * @return String 密文
     * @throws Exception 异常错误
     */
    public static String privateEncrypt(String data, String privateKey, String charset) throws Exception {
        RSAPrivateKey key = parsePrivateKey(privateKey);
        Cipher cipher = getCipher(Cipher.ENCRYPT_MODE, key);
        byte[] temp = data.getBytes(charset);
        temp = splitCodec(cipher, Cipher.ENCRYPT_MODE, temp, key.getModulus().bitLength());
        return Base64.getUrlEncoder().encodeToString(temp);
    }

    /**
     * 私钥解密
     *
     * @param data       数据
     * @param privateKey 私钥
     * @return String 密文
     * @throws Exception 异常错误
     */
    public static String privateDecrypt(String data, String privateKey) throws Exception {
        return privateDecrypt(data, privateKey, DEFAULT_CHARSET);
    }

    /**
     * 私钥解密
     *
     * @param data       数据
     * @param privateKey 私钥
     * @param charset    字符集
     * @return String 密文
     * @throws Exception 异常错误
     */
    public static String privateDecrypt(String data, String privateKey, String charset) throws Exception {
        RSAPrivateKey key = parsePrivateKey(privateKey);
        Cipher cipher = getCipher(Cipher.DECRYPT_MODE, key);
        byte[] temp = Base64.getUrlDecoder().decode(data);
        temp = splitCodec(cipher, Cipher.DECRYPT_MODE, temp, key.getModulus().bitLength());
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
        Cipher cipher = Cipher.getInstance(ALGORITHM_RSA);
        cipher.init(mode, key);
        return cipher;
    }

    /**
     * 分段加解密处理
     *
     * @param cipher  加解密处理器
     * @param opmode  加密/解密
     * @param datas   输入数据
     * @param keySize 密钥长度
     * @return byte[] 返回数据
     * @throws Exception 异常错误
     */
    private static byte[] splitCodec(Cipher cipher, int opmode, byte[] datas, int keySize) throws Exception {
        int maxBlock = 0;
        if (opmode == Cipher.DECRYPT_MODE) {
            maxBlock = keySize / 8;
        } else if (opmode == Cipher.ENCRYPT_MODE) {
            maxBlock = keySize / 8 - 11;
        } else {
            throw new RuntimeException("Error Cipher Mode: " + opmode);
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] temp = null;
        int i = 0;
        while (datas.length > offSet) {
            if (datas.length - offSet > maxBlock) {
                temp = cipher.doFinal(datas, offSet, maxBlock);
            } else {
                temp = cipher.doFinal(datas, offSet, datas.length - offSet);
            }
            out.write(temp, 0, temp.length);
            i++;
            offSet = i * maxBlock;
        }
        temp = out.toByteArray();
        out.flush();
        out.close();
        return temp;
    }

    /**
     * 测试类
     *
     * @param args 参数
     * @throws Exception 异常
     */
    public static void main(String[] args) throws Exception {
        Map<String, String> keyMap = RsaUtils.createKeys();
        String publicKey = keyMap.get(RsaUtils.KEY_PUBLIC);
        String privateKey = keyMap.get(RsaUtils.KEY_PRIVATE);
        System.out.println(">>>公钥:\r\n" + publicKey);
        System.out.println(">>>私钥:\r\n" + privateKey);

        String data = "站在大明门前守卫的禁卫军，事先没有接到\n" +
                "有关的命令，但看到大批盛装的官员来临，也就\n" +
                "以为确系举行大典，因而未加询问。进大明门即\n" +
                "为皇城。文武百官看到端门午门之前气氛平静，\n" +
                "城楼上下也无朝会的迹象，既无几案，站队点名\n" +
                "的御史和御前侍卫“大汉将军”也不见踪影，不免\n" +
                "心中揣测，互相询问：所谓午朝是否讹传？\n" +
                "!@#$%^&*()_+-=<>,.;':[]{}\\\"~`";
        System.out.println("\r\n>>>明文内容：" + data.getBytes().length + "bytes\r\n" + data);

        System.out.println("\r\n>>>测试一：公钥加密->私钥解密");
        String encodedData1 = RsaUtils.publicEncrypt(data, publicKey);
        System.out.println(">>>加密后文字：\r\n" + encodedData1);
        String decodedData1 = RsaUtils.privateDecrypt(encodedData1, privateKey);
        System.out.println(">>>解密后文字: \r\n" + decodedData1);

        System.out.println("\r\n>>>测试二：私钥加密->公钥解密");
        String encodedData2 = RsaUtils.privateEncrypt(data, privateKey);
        System.out.println(">>>加密后文字：\r\n" + encodedData2);
        String decodedData2 = RsaUtils.publicDecrypt(encodedData2, publicKey);
        System.out.println(">>>解密后文字: \r\n" + decodedData2);
    }
}
