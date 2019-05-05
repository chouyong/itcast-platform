package com.itcast.common;

import com.itcast.common.dto.RetCode;

/**
 * 业务处理自定义异常
 */
public class BizException extends Exception {

    // 默认返回错误代码
    private RetCode retCode = RetCode.EXCEPTION;

    /**
     * 构造方法
     */
    public BizException() {
        super(RetCode.EXCEPTION.getMsg());
    }

    /**
     * 构造方法
     *
     * @param message 提示信息
     */
    public BizException(String message) {
        super(message);
    }

    /**
     * 构造方法
     *
     * @param message 提示信息
     * @param e       异常对象
     */
    public BizException(String message, Throwable e) {
        super(message, e);
    }

    /**
     * 构造方法
     *
     * @param e 异常对象
     */
    public BizException(Throwable e) {
        super(RetCode.EXCEPTION.getMsg(), e);
    }

    /**
     * 构造方法
     *
     * @param retCode 返回代码
     */
    public BizException(RetCode retCode) {
        super(retCode.getMsg());
        this.retCode = retCode;
    }

    /**
     * 构造方法
     *
     * @param retCode 返回代码
     * @param message 提示信息
     */
    public BizException(RetCode retCode, String message) {
        super(message);
        this.retCode = retCode;
    }

    /**
     * 构造方法
     *
     * @param retCode 返回代码
     * @param e       异常对象
     */
    public BizException(RetCode retCode, Throwable e) {
        super(retCode.getMsg(), e);
        this.retCode = retCode;
    }

    /**
     * 构造方法
     *
     * @param retCode 返回代码
     * @param message 提示信息
     * @param e       异常对象
     */
    public BizException(RetCode retCode, String message, Throwable e) {
        super(message, e);
        this.retCode = retCode;
    }

    public RetCode getRetCode() {
        return retCode;
    }

    public void setRetCode(RetCode retCode) {
        this.retCode = retCode;
    }

}
