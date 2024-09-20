package com.example.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.example.pojo.Result;
import com.example.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Object handler) throws Exception {
        log.info("preHandle -----");
        // 1.获取请求的url
        String url = httpRequest.getRequestURI().toString();
        log.info("请求接口: {}",url);

        // 2.判段是否为登录请求
        if(url.contains("login")){
            log.info("登录操作");
            return true;
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
            return false;
        }

        // 5.解析jwt
        try {
            JwtUtils.parseJwt(jwt);
        } catch (Exception e) {
            log.info("jwt解析失败");
            Result err = Result.error("NOT_LOGIN");
            String jsonerr = JSONObject.toJSONString(err);
            httpResponse.getWriter().write(jsonerr);
            return false;
        }

        // 6.jwt解析成功, 放行
        log.info("jwt解析成功");
       return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    log.info("postHandle -------");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
   log.info("afterCompletion --------");
    }
}
