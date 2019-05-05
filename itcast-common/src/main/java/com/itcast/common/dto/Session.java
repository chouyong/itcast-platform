package com.itcast.common.dto;

/**
 * 会话对象
 */
public class Session {

    // 无限制时间
    public final static long NO_LIMIT_TIME = -1;

    // 会话租户标识
    private String tenantId;

    // 会话用户标识
    private String userId;

    // 会话角色标识
    private String roles;

    // 会话组织标识
    private String orgs;

    // 会话登录方式
    private String loginType;

    // 会话登录标识
    private String loginId;

    // 会话登录时间
    private long loginTime = System.currentTimeMillis();

    // 会话过期时间
    private long expireTime = 0;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getOrgs() {
        return orgs;
    }

    public void setOrgs(String orgs) {
        this.orgs = orgs;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(long loginTime) {
        this.loginTime = loginTime;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }
}
