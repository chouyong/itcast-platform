package com.itcast.common.utils;

import com.itcast.common.dto.Header;
import com.itcast.common.dto.ReqKey;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 请求对象处理工具类
 */
public final class RequestUtils {

    /**
     * 构造方法（静态类禁止创建）
     */
    private RequestUtils() {
    }

    /**
     * 复制请求对象
     *
     * @param target 复制参考对象
     * @return Header 复制的请求对象
     */
    public static Header copyHeader(Header target) {
        return copyHeader(target, false);
    }

    /**
     * 复制请求对象
     *
     * @param target 复制参考对象
     * @param reset  重置请求流水号、时间戳、重复次数
     * @return Request<T> 复制的请求对象
     */
    public static Header copyHeader(Header target, boolean reset) {
        Header ret = new Header();
        ret.setApplication(target.getApplication());
        ret.setService(target.getService());
        ret.setVersion(target.getVersion());
        ret.setChannel(target.getChannel());
        ret.setCallback(target.getCallback());
        ret.setSignType(target.getSignType());
        ret.setSignature(target.getSignature());
        ret.setToken(target.getToken());
        ret.setTenantId(target.getTenantId());
        ret.setUserId(target.getUserId());
        ret.setRoles(target.getRoles());
        ret.setOrgs(target.getOrgs());
        ret.setLoginTime(target.getLoginTime());
        ret.setExpireTime(target.getExpireTime());
        if (!reset) {
            ret.setReqId(target.getReqId());
            ret.setTimestamp(target.getTimestamp());
            ret.setRepeat(target.getRepeat());
        }
        ret.setClient(target.getClient());
        ret.setRemoteIp(target.getRemoteIp());
        ret.setDeviceId(target.getDeviceId());
        ret.setMobile(target.getMobile());
        ret.setWeixin(target.getWeixin());
        ret.setEmail(target.getEmail());
        ret.setGps(target.getGps());
        return ret;
    }

    /**
     * 获取公共参数
     * (基于网关过滤处理后使用)
     *
     * @param request HTTP请求
     * @return String 公共参数
     */
    public static Header getHeader(HttpServletRequest request) {
        Header ret = new Header();
        ret.setApplication(request.getHeader(ReqKey.APPLICATION));
        ret.setService(request.getHeader(ReqKey.SERVICE));
        ret.setVersion(request.getHeader(ReqKey.VERSION));
        ret.setChannel(request.getHeader(ReqKey.CHANNEL));
        ret.setCallback(request.getHeader(ReqKey.CALLBACK));
        ret.setSignType(request.getHeader(ReqKey.SIGN_TYPE));
        ret.setSignature(request.getHeader(ReqKey.SIGNATURE));
        ret.setToken(request.getHeader(ReqKey.TOKEN));
        ret.setTenantId((String) request.getAttribute(ReqKey.TENANT_ID));
        ret.setUserId((String) request.getAttribute(ReqKey.USER_ID));
        ret.setRoles((String) request.getAttribute(ReqKey.ROLES));
        ret.setOrgs((String) request.getAttribute(ReqKey.ORGS));
        ret.setLoginTime((String) request.getAttribute(ReqKey.LOGIN_TIME));
        ret.setExpireTime((String) request.getAttribute(ReqKey.EXPIRE_TIME));
        ret.setReqId((String) request.getAttribute(ReqKey.REQ_ID));
        ret.setTimestamp((String) request.getAttribute(ReqKey.TIMESTAMP));
        ret.setRepeat((String) request.getAttribute(ReqKey.REPEAT));
        ret.setClient((String) request.getAttribute(ReqKey.CLIENT));
        ret.setRemoteIp((String) request.getAttribute(ReqKey.REMOTE_IP));
        ret.setDeviceId(request.getHeader(ReqKey.DEVICE_ID));
        ret.setMobile(request.getHeader(ReqKey.MOBILE));
        ret.setWeixin(request.getHeader(ReqKey.WEIXIN));
        ret.setEmail(request.getHeader(ReqKey.EMAIL));
        ret.setGps(request.getHeader(ReqKey.GPS));
        return ret;
    }

    /**
     * 获取客户端信息
     *
     * @param request 请求
     * @return String 客户端信息
     */
    public static String getClientInfo(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取完整URL地址
     *
     * @param request 请求
     * @param path    路径
     * @return String 获取请求路径
     */
    public static String getRequestPath(HttpServletRequest request, String path) {
        String ret = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath();
        if (path == null || path.length() < 1) {
            return ret;
        }
        return ret + "/" + path;
    }

    /**
     * 获取完整URL地址
     *
     * @param request 请求
     * @param path    路径
     * @return String 获取请求路径
     */
    public static String getLocalPath(HttpServletRequest request, String path) {
        String ret = request.getScheme() + "://" + request.getLocalAddr() + ":" + request.getLocalPort()
                + request.getContextPath();
        if (path == null || path.length() < 1) {
            return ret;
        }
        return ret + "/" + path;
    }

    /**
     * 获取Cookie信息
     *
     * @param request 请求
     * @param name    Cookie名
     * @return Cookie Cookie信息
     */
    public static Cookie getCookieByName(HttpServletRequest request, String name) {
        if (name == null || name.length() < 1) {
            return null;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
        }
        return null;
    }

    /**
     * 获取Cookie值
     *
     * @param request 请求
     * @param name    Cookie名
     * @return String Cookie值
     */
    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie cookie = getCookieByName(request, name);
        if (cookie == null) {
            return null;
        }
        return cookie.getValue();
    }

    /**
     * 按级别获取请求对象：参数、Header、Cookie、请求属性、会话属性
     *
     * @param request 请求对象
     * @param key     属性名称
     * @return String 对象文本
     */
    public static String getTargetValue(HttpServletRequest request, String key) {
        if (request == null || StringUtils.isBlank(key)) {
            return null;
        }
        String ret = request.getParameter(key);
        if (StringUtils.isBlank(ret)) {
            ret = request.getHeader(key);
        }
        if (StringUtils.isBlank(ret)) {
            ret = getCookieValue(request, key);
        }
        return ret;
    }
}
