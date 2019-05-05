package com.itcast.platform.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import javax.servlet.http.HttpServletRequest;

public class AuthZuulFilter extends ZuulFilter {
    // 日志输出器
    private final static Logger LOGGER = LoggerFactory.getLogger(AuthZuulFilter.class);

    @Value("${server.port}")
    private String serverPort;

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        //从请求头中获取token信息
        String userToken = request.getHeader("userToken");
        LOGGER.debug("网关端口："+serverPort);
        if(StringUtils.isEmpty(userToken)){
            //设置为false就不会继续执行服务代码
            ctx.setSendZuulResponse(false);
            //设置状态码
            ctx.setResponseStatusCode(401);
            //设置相应信息
            ctx.setResponseBody("userToken is null");
            return null;
        }
        return null;
    }

    @Override
    public boolean shouldFilter() {
        return RequestContext.getCurrentContext().getThrowable() == null;
    }

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER;
    }

}
