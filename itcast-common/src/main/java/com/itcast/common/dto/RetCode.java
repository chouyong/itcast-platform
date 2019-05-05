package com.itcast.common.dto;

/**
 * 响应返回代码枚举
 */
public enum RetCode {

    // 系统级通用返回代码，范围：[0,9]
    SUCCESS("0", "成功"),
    FAILURE("1", "失败"),
    DUPLICATE("2", "请求重复提交"),
    FREQUENT("3", "过高频次访问"),
    LOCKED("4", "资源占用锁定"),
    OFFLINE("7", "网络异常断线"),
    TIMEOUT("8", "连接超时错误"),
    EXCEPTION("9", "未知系统异常"),

    // 业务级通用错误代码，范围：[10,99]
    ERR_PROTOCOL("10", "协议约定错误"),
    ERR_SIGNATURE("11", "签名验证错误"),
    ERR_ENCRYPT("12", "加密解密异常"),
    ERR_AUTHORITY("13", "访问认证错误"),
    ERR_PERMISSION("14", "权限许可不足"),
    ERR_VALID_PARAM("21", "参数校验错误"),
    ERR_VALID_DATA("22", "数据校验错误"),
    ERR_VALID_LOGIC("23", "逻辑校验错误"),

    // 登录会话错误代码，范围：[100,199]
    ERR_LOGIN_NONE("100", "未登录"),
    ERR_LOGIN_VCODE("101", "登录验证码错误"),
    ERR_LOGIN_USERNAME("102", "登录用户名错误"),
    ERR_LOGIN_PASSWORD("103", "登录密码错误"),
    ERR_LOGIN_CERTIFICATE("104", "登录证书错误"),
    ERR_LOGIN_EXPIRED("105", "登录时间已过期"),
    ERR_LOGIN_LIMIT("107", "登录错误次数超限"),
    ERR_LOGIN_DUPLICATE("106", "用户异地重复登录"),
    ERR_LOGIN_LOCKED("108", "登录用户已锁定");

    // 代码
    private String code;

    // 默认信息
    private String msg;

    /**
     * 构造方法
     *
     * @param code 代码
     * @param msg  默认信息
     */
    private RetCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
