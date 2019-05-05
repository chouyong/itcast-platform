package com.itcast.common.utils;

import com.fasterxml.uuid.Generators;

import java.util.Random;
import java.util.UUID;

/**
 * 主键辅助工具类
 */
public class KeyUtils {

    /**
     * 基础字符集
     */
    public final static char[] BASE_CHARS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'};

    /**
     * 构造方法（静态类禁止创建）
     */
    private KeyUtils() {
    }

    /**
     * 生成主键（基于时间算法可排序）
     *
     * @return String 主键
     */
    public static String generate() {
        UUID uuid = Generators.timeBasedGenerator().generate();
        return uuid.toString().replaceAll("-", "");
    }

    /**
     * 8位时间随机序列
     *
     * @return String 8位时间随机序列
     */
    public static String timeRandom8() {
        return generate().substring(0, 8);
    }

    /**
     * 获取32进制时间戳(9位长度)
     *
     * @return String 32进制时间戳
     */
    public static String time32bit() {
        return Long.toUnsignedString(System.currentTimeMillis(), 32);
    }

    /**
     * 获取指定长度随机字符串
     *
     * @param length 长度
     * @return String 随机字符串
     */
    public static String charRandom(int length) {
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(BASE_CHARS[r.nextInt(62)]);
        }
        return sb.toString();
    }
}
