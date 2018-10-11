package com.yyq.es_retreatment.util.aop;


import com.yyq.es_retreatment.entity.vo.CommonData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author Liujuhao
 * @date 2018/8/2.
 */

@Component
@Aspect
public class ResponseAOP {

    Log logger = LogFactory.getLog(this.getClass());

    @Pointcut("execution(public * com.yyq.es_retreatment.controller..*.*(..))")
    public void reponseDo() {
    }

    @Before("reponseDo()")
    public void deBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "ret", pointcut = "reponseDo()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("METHOD_RETURN : " + ret);
    }

    @Around("reponseDo()")
    public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result;
        logger.info("AROUND_TARGET_METHOD : " + proceedingJoinPoint.getSignature().getName());
        try {

        } catch (Exception e) {
            return e;
        }
        result = proceedingJoinPoint.proceed();
        if (result instanceof ResponseEntity) {
            Object body = ((ResponseEntity) result).getBody();
            if (body instanceof Integer && (int) body == 1) {
                return ResponseEntity.ok().body(new CommonData(){{
                    this.setCode("200");
                    this.setMessage("success");
                }});
            }
        }
        return result;
    }
}
