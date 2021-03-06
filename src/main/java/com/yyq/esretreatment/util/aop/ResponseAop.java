package com.yyq.esretreatment.util.aop;


import com.yyq.esretreatment.entity.vo.CommonData;
import com.yyq.esretreatment.service.ExceptionHandle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * create by XiangChao on 2018/10/10
 */
@Component
@Aspect
public class ResponseAop {

    Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    ExceptionHandle exceptionHandle;

    @Pointcut("execution(public * com.yyq.esretreatment.controller..*.*(..))")
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
            result = proceedingJoinPoint.proceed();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(exceptionHandle.handle(e));
        }

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
