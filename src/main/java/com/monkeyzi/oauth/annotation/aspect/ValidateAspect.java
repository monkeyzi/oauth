package com.monkeyzi.oauth.annotation.aspect;

import com.monkeyzi.oauth.annotation.ValidateAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 参数验证
 */
@Component
@Aspect
@Slf4j
public class ValidateAspect {

    @Pointcut("@annotation(com.monkeyzi.oauth.annotation.ValidateAnnotation)")
    public void ValidateAnnotation(){

    }


    /**
     * 前置通知 (在方法执行之前返回)用于拦截Controller层记录用户的操作的开始时间
     */
    @Before("ValidateAnnotation()")
    public void doBofore(){
    }

    /**
     * 后置通知
     */
    @AfterReturning(pointcut = "ValidateAnnotation()")
    public void doAfter(final JoinPoint joinPoint){

        //获取方法名
        String methodName=joinPoint.getSignature().getName();
        //拦截目标
        Object target=joinPoint.getTarget();
        //根据类名和方法名得到拦截的方法
        Method method=getMethodByClassAndName(target.getClass(),methodName);
        //参数
        Object[] args=joinPoint.getArgs();
        assert method!=null;

        //根据方法名得到拦截的注解
        ValidateAnnotation annotation = (ValidateAnnotation) getAnnotationByMethod(method, ValidateAnnotation.class);
        if (annotation!=null){
            BindingResult bindingResult = null;
            for (Object arg : args) {
                if (arg instanceof BindingResult) {
                    bindingResult = (BindingResult) arg;
                }
            }
            if (bindingResult != null && bindingResult.hasErrors()) {
                String errorInfo = bindingResult.getFieldError().getDefaultMessage();
                throw new IllegalArgumentException(errorInfo);
            }
        }





    }

    /**
     * 根据目标方法和注解类型  得到该目标方法的指定注解
     */
    private Annotation getAnnotationByMethod(Method method, Class annoClass) {
        Annotation[] all = method.getAnnotations();
        for (Annotation annotation : all) {
            if (annotation.annotationType() == annoClass) {
                return annotation;
            }
        }
        return null;
    }


    /**
     * 根据类和方法名得到方法
     */
    private Method getMethodByClassAndName(Class c, String methodName) {
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }
}
