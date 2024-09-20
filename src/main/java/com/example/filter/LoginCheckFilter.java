package com.example.filter;

import com.alibaba.fastjson.JSONObject;
import com.example.pojo.Result;
import com.example.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
//@WebFilter(urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        // 1.获取请求的url
        String url = httpRequest.getRequestURI().toString();
        log.info("请求接口: {}",url);

        // 2.判段是否为登录请求
        if(url.contains("login")){
            log.info("登录操作");
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }

        // 3.获取请求头里的token
        String jwt = httpRequest.getHeader("token");
        log.info("jwt: {}",jwt);

        // 4.判断是否有token,
        if(!StringUtils.hasLength(jwt)){
            log.info("token为空");
            Result err = Result.error("NOT_LOGIN");
            // 对象转成json
            String jsonErr = JSONObject.toJSONString(err);
            httpResponse.getWriter().write(jsonErr);
            return;
        }

        // 5.解析jwt
        try {
            JwtUtils.parseJwt(jwt);
        } catch (Exception e) {
            log.info("jwt解析失败");
            Result err = Result.error("NOT_LOGIN");
            String jsonerr = JSONObject.toJSONString(err);
            httpResponse.getWriter().write(jsonerr);
            return;
        }

        // 6.jwt解析成功, 放行
        log.info("jwt解析成功");
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
