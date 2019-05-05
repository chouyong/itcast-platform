package com.itcast.common.dto;

import org.apache.commons.lang3.StringUtils;

/**
 * 响应对象
 *
 * @param <T> 数据类型
 */
public class Response<T> {

    // 返回代码
    private String code;

    // 返回信息
    private String msg;

    // 附加描述
    private String desc;

    // 签名方式
    private String signType;

    // 签名证书
    private String signature;

    // 返回时间戳
    private long timestamp = System.currentTimeMillis();

    // 数据对象
    private T data;

    /**
     * 构造方法
     */
    public Response() {
    }

    /**
     * 构造方法
     *
     * @param code 返回代码
     */
    public Response(String code) {
        this.code = code;
    }

    /**
     * 构造方法
     *
     * @param code 返回代码
     * @param data 数据对象
     */
    public Response(String code, T data) {
        this.code = code;
        this.data = data;
    }

    /**
     * 构造方法
     *
     * @param code 返回代码
     * @param msg  返回信息
     */
    public Response(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 构造方法
     *
     * @param code 返回代码
     * @param msg  返回信息
     * @param data 数据对象
     */
    public Response(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 构造方法
     *
     * @param code 返回代码
     * @param msg  返回信息
     * @param desc 附加描述
     */
    public Response(String code, String msg, String desc) {
        this.code = code;
        this.msg = msg;
        this.desc = desc;
    }

    /**
     * 构造方法
     *
     * @param code 返回代码
     * @param msg  返回信息
     * @param desc 附加描述
     * @param data 数据对象
     */
    public Response(String code, String msg, String desc, T data) {
        this.code = code;
        this.msg = msg;
        this.desc = desc;
        this.data = data;
    }

    /**
     * 是否为成功响应
     *
     * @return boolean 是否
     */
    public boolean success() {
        return !StringUtils.isBlank(this.code) && this.code.equals(RetCode.SUCCESS.getCode());
    }

    public String getCode() {
        return code;
    }

    public Response setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Response setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public Response setDesc(String description) {
        this.desc = description;
        return this;
    }

    public String getSignType() {
        return signType;
    }

    public Response setSignType(String signType) {
        this.signType = signType;
        return this;
    }

    public String getSignature() {
        return signature;
    }

    public Response setSignature(String signature) {
        this.signature = signature;
        return this;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Response setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public T getData() {
        return data;
    }

    public Response setData(T data) {
        this.data = data;
        return this;
    }
}
