package com.yzbbanban.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AccessFilter extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String filterType() {
        //请求前执行
        return "pre";
    }

    @Override
    public int filterOrder() {
        //顺序
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        //是否过滤
        return true;
    }

    @Override
    public Object run() {
        //测试地址：http://localhost:8787/api-app/app/get?token=111

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        logger.info("send:{} to:{}", request.getMethod(), request.getRequestURL().toString());
        Object token = request.getParameter("token");
        if (token == null) {
            logger.info("token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            return null;
        }
        logger.info("token is ok");
        return null;
    }
}
