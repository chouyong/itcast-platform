package com.itcast.common.utils;

import com.itcast.common.BizException;
import com.itcast.common.dto.Response;
import com.itcast.common.dto.RetCode;

/**
 * 响应对象处理工具类
 */
public class ResponseUtils {

    // 字符集：UTF-8
    public static final String CHARSET_UTF8 = "UTF-8";

    /**
     * 构造方法（静态类禁止创建）
     */
    private ResponseUtils() {
    }

    /**
     * 创建成功响应对象
     *
     * @return Response 响应对象
     */
    public static Response success() {
        return response(RetCode.SUCCESS);
    }

    /**
     * 创建成功响应对象
     *
     * @param message 消息信息
     * @return Response 响应对象
     */
    public static Response success(String message) {
        return response(RetCode.SUCCESS, message);
    }

    /**
     * 创建成功响应对象
     *
     * @param message 消息信息
     * @param desc    附加描述
     * @return Response 响应对象
     */
    public static Response success(String message, String desc) {
        return response(RetCode.SUCCESS, message, desc);
    }

    /**
     * 创建成功响应对象
     *
     * @param data 数据对象
     * @param <T>  参考对象类型
     * @return Response<T> 响应对象
     */
    public static <T> Response<T> success(T data) {
        return response(RetCode.SUCCESS, data);
    }

    /**
     * 创建成功响应对象
     *
     * @param message 消息信息
     * @param data    数据对象
     * @param <T>     参考对象类型
     * @return Response<T> 响应对象
     */
    public static <T> Response<T> success(String message, T data) {
        return response(RetCode.SUCCESS, message, data);
    }

    /**
     * 创建成功响应对象
     *
     * @param message 消息信息
     * @param desc    附加描述
     * @param data    数据对象
     * @param <T>     参考对象类型
     * @return Response<T> 响应对象
     */
    public static <T> Response<T> success(String message, String desc, T data) {
        return response(RetCode.SUCCESS, message, desc, data);
    }

    /**
     * 创建失败响应对象
     *
     * @return Response 响应对象
     */
    public static Response failure() {
        return response(RetCode.FAILURE);
    }

    /**
     * 创建失败响应对象
     *
     * @param message 消息信息
     * @return Response 响应对象
     */
    public static Response failure(String message) {
        return response(RetCode.FAILURE, message);
    }

    /**
     * 创建失败响应对象
     *
     * @param message 消息信息
     * @param desc    附加描述
     * @return Response 响应对象
     */
    public static Response failure(String message, String desc) {
        return response(RetCode.FAILURE, message, desc);
    }

    /**
     * 创建失败响应对象
     *
     * @param data 数据对象
     * @param <T>  参考对象类型
     * @return Response<T> 响应对象
     */
    public static <T> Response<T> failure(T data) {
        return response(RetCode.FAILURE, data);
    }

    /**
     * 创建失败响应对象
     *
     * @param message 消息信息
     * @param data    数据对象
     * @param <T>     参考对象类型
     * @return Response<T> 响应对象
     */
    public static <T> Response<T> failure(String message, T data) {
        return response(RetCode.FAILURE, message, data);
    }

    /**
     * 创建失败响应对象
     *
     * @param message 消息信息
     * @param desc    附加描述
     * @param data    数据对象
     * @param <T>     参考对象类型
     * @return Response<T> 响应对象
     */
    public static <T> Response<T> failure(String message, String desc, T data) {
        return response(RetCode.FAILURE, message, desc, data);
    }

    /**
     * 创建异常响应对象
     *
     * @param message 异常信息
     * @return Response 响应对象
     */
    public static Response exception(String message) {
        return response(RetCode.EXCEPTION, message);
    }

    /**
     * 创建异常响应对象
     *
     * @param message 异常信息
     * @param desc    附加描述
     * @return Response 响应对象
     */
    public static Response exception(String message, String desc) {
        return response(RetCode.EXCEPTION, message, desc);
    }

    /**
     * 创建异常响应对象
     *
     * @param e 异常信息
     * @return Response 响应对象
     */
    public static Response exception(Throwable e) {
        StackTraceElement[] steArray = e.getStackTrace();
        if (steArray == null || steArray.length < 1) {
            return response(RetCode.EXCEPTION, e.getMessage());
        }
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement ste : steArray) {
            sb.append(ste.toString()).append("\r\n");
        }
        return response(RetCode.EXCEPTION, e.getMessage(), sb.toString());
    }

    /**
     * 创建异常响应对象
     *
     * @param message 消息信息
     * @param e       异常信息
     * @return Response 响应对象
     */
    public static Response exception(String message, Throwable e) {
        StackTraceElement[] steArray = e.getStackTrace();
        if (steArray == null || steArray.length < 1) {
            return response(RetCode.EXCEPTION, message);
        }
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement ste : steArray) {
            sb.append(ste.toString()).append("\r\n");
        }
        return response(RetCode.EXCEPTION, message, sb.toString());
    }

    /**
     * 创建异常响应对象
     *
     * @param e 异常信息
     * @return Response 响应对象
     */
    public static Response exception(BizException e) {
        StackTraceElement[] steArray = e.getStackTrace();
        if (steArray == null || steArray.length < 1) {
            return response(e.getRetCode(), e.getMessage());
        }
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement ste : steArray) {
            sb.append(ste.toString()).append("\r\n");
        }
        return response(e.getRetCode(), e.getMessage(), sb.toString());
    }

    /**
     * 创建响应对象
     *
     * @param retCode 返回代码
     * @return Response 响应对象
     */
    public static Response response(RetCode retCode) {
        return response(retCode, null, null, null);
    }

    /**
     * 创建响应对象
     *
     * @param retCode 返回代码
     * @param message 消息信息
     * @return Response 响应对象
     */
    public static Response response(RetCode retCode, String message) {
        return response(retCode, message, null, null);
    }

    /**
     * 创建响应对象
     *
     * @param retCode 返回代码
     * @param message 消息信息
     * @param desc    附加描述
     * @return Response 响应对象
     */
    public static Response response(RetCode retCode, String message, String desc) {
        return response(retCode, message, desc, null);
    }

    /**
     * 创建响应对象
     *
     * @param retCode 返回代码
     * @param data    数据对象
     * @param <T>     参考对象类型
     * @return Response<T> 响应对象
     */
    public static <T> Response<T> response(RetCode retCode, T data) {
        return response(retCode, null, null, data);
    }

    /**
     * 创建响应对象
     *
     * @param retCode 返回代码
     * @param message 消息信息
     * @param data    数据对象
     * @param <T>     参考对象类型
     * @return Response<T> 响应对象
     */
    public static <T> Response<T> response(RetCode retCode, String message, T data) {
        return response(retCode, message, null, data);
    }

    /**
     * 创建响应对象
     *
     * @param retCode 返回代码
     * @param message 消息信息
     * @param desc    附加描述
     * @param data    数据对象
     * @param <T>     参考对象类型
     * @return Response<T> 响应对象
     */
    public static <T> Response<T> response(RetCode retCode, String message, String desc, T data) {
        Response<T> response = new Response<T>();
        response.setCode(retCode.getCode());
        response.setMsg(message == null ? retCode.getMsg() : message);
        response.setDesc(desc);
        response.setData(data);
        return response;
    }
}
