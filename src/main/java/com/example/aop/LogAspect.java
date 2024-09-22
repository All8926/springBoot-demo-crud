package com.example.aop;

import com.alibaba.fastjson.JSONObject;
import com.example.mapper.OperateLogMapper;
import com.example.pojo.OperateLog;
import com.example.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Insert;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Component
@Aspect // 切面类
public class LogAspect {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Around("@annotation(com.example.anno.Log)")    // 环绕通知，指定切入点为注解的方式
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取操作人员Id
        String token = request.getHeader("token");
        Claims claims = JwtUtils.parseJwt(token);
        Integer operateUser = (Integer) claims.get("id");

        // 操作时间
        LocalDateTime operateTime = LocalDateTime.now();

        // 操作类法
        String className = joinPoint.getTarget().getClass().getName();

        // 操作方法名
        String methodName = joinPoint.getSignature().getName();

        // 操作方法的参数
        Object[] args = joinPoint.getArgs();
        String methodParmas = Arrays.toString(args);

        long begin = System.currentTimeMillis();
        // 调用原始目标方法运行
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();

        // 方法返回值
        String returnValue = JSONObject.toJSONString(result);

        // 操作耗时
        Long contTime = end - begin;

        OperateLog operateLog = new OperateLog(null,operateUser,operateTime,className,methodName,methodParmas,returnValue,contTime);
        log.info("AOP记录操作日志：{}", operateLog);
        operateLogMapper.insert(operateLog);
        return result;
    }
}
