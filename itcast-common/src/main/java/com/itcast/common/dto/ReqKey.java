package com.itcast.common.dto;

/**
 * Http请求头信息键值
 */
public class ReqKey {

    // 请求接入方式信息
    public final static String APPLICATION = "x-application";  // 调用应用标识
    public final static String SERVICE = "x-service";  // 调用服务地址
    public final static String VERSION = "x-version";  // 调用方版本号
    public final static String CHANNEL = "x-channel";  // 渠道来源标识
    public final static String CALLBACK = "x-callback";  // 通知回调地址

    // 请求签名证书信息
    public final static String SIGN_TYPE = "x-sign-type";  // 签名方式类型
    public final static String SIGNATURE = "x-signature";  // 签名证书内容

    // 请求会话相关信息
    public final static String TOKEN = "x-token";  // 会话访问令牌
    public final static String TENANT_ID = "x-tenant-id";  // 会话租户标识
    public final static String USER_ID = "x-user-id";  // 会话用户标识
    public final static String ROLES = "x-roles";  // 会话角色标识
    public final static String ORGS = "x-orgs";  // 会话组织标识
    public final static String LOGIN_TIME = "x-login-time";  // 会话令牌时间
    public final static String EXPIRE_TIME = "x-expire-time";  // 会话过期时间

    // 请求审计辅助信息
    public final static String REQ_ID = "x-req-id";  // 请求流水号
    public final static String TIMESTAMP = "x-timestamp";  // 请求时间戳
    public final static String REPEAT = "x-repeat";  // 请求重复次数
    public final static String CLIENT = "x-client";  // 请求客户端
    public final static String REMOTE_IP = "x-remote-ip";  // 请求IP地址

    // 请求设备客户端信息
    public final static String DEVICE_ID = "x-device";  // 硬件设备码
    public final static String MOBILE = "x-mobile";  // 关联手机号
    public final static String WEIXIN = "x-weixin";  // 关联微信号
    public final static String EMAIL = "x-email";  // 关联邮件地址
    public final static String GPS = "x-gps";  // 关联位置信息

    /**
     * 构造方法
     */
    private ReqKey() {
    }
}
