package com.monkeyzi.oauth.annotation.aspect;

import com.monkeyzi.oauth.annotation.LogAnnotation;
import com.monkeyzi.oauth.common.R;
import com.monkeyzi.oauth.entity.dto.LogDto;
import com.monkeyzi.oauth.entity.dto.LoginAuthDto;
import com.monkeyzi.oauth.enums.LogTypeEnum;
import com.monkeyzi.oauth.utils.*;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
@Aspect
public class LogAspect {

    private ThreadLocal<Date> threadLocal=new ThreadLocal<>();

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private TaskExecutor taskExecutor;

    private static final int MAX_SIZE=2000;

    @Pointcut("@annotation(com.monkeyzi.oauth.annotation.LogAnnotation)")
    public void LogAnnotation(){

    }
    /**
     * 前置通知 (在方法执行之前返回)用于拦截Controller层记录用户的操作的开始时间
     */
    @Before("LogAnnotation()")
    public void doBofore(){
        this.threadLocal.set(new Date(System.currentTimeMillis()));
    }

    /**
     * 后置通知(在方法执行之后返回) 用于拦截Controller层操作
     * @param joinPoint 切点
     * @param returnValue 返回值
     */
    @AfterReturning(pointcut = "LogAnnotation()", returning = "returnValue")
    public void doAfter(final JoinPoint joinPoint, final Object returnValue){
            LogAnnotation iFlog=giveController(joinPoint);
            if (iFlog==null){
                return;
            }
            //这里主要是为了区分登录还是其他操作
            if (iFlog!=null&&iFlog.logType().getType().equals(LogTypeEnum.LOGIN_LOG.getType())){
                this.handlerLog(joinPoint,null);
            }
            if (returnValue instanceof R){
              R result= (R) returnValue;
              //只有响应结果不为空并且成功响应才记录
              if (!PublicUtil.isEmpty(result)&&result.getCode().equals(R.ok().getCode())){
                 this.handlerLog(joinPoint,result);
              }
           }

    }


    private void handlerLog(final JoinPoint joinPoint,final Object result){
        log.info("开始记录日志");
        final Date startTime=this.threadLocal.get();
        final Date endTime=new Date(System.currentTimeMillis());
        HttpServletRequest request=RequestUtils.getRequest();
        final UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("user-Agent"));
        String requestURI = request.getRequestURI();
        try {
            LogAnnotation iFlog=giveController(joinPoint);
            if (iFlog==null){
                return;
            }
            //获取登录的用户
            LoginAuthDto loginAuthDto=new LoginAuthDto();
            if (loginAuthDto==null){
                log.error("记录日志时没有获取到用户信息");
            }
            //获取客户端操作系统
            final String os=userAgent.getOperatingSystem().getName();
            //获取浏览器
            final String browser=userAgent.getBrowser().getName();
            //ip
            final String ipAddress = RequestUtils.getRemoteAddr(request);
            //预留---mac地址
            final String mac="";
            LogDto logDto=new LogDto();
            logDto.setBrowser(browser);
            logDto.setOs(os);
            logDto.setIp(ipAddress);
            logDto.setDeptId(loginAuthDto.getDepartmentId());
            logDto.setDeptName(loginAuthDto.getDepartmentName());
            logDto.setMac(mac);
            logDto.setLogName(iFlog.logType().getName());
            logDto.setLogType(iFlog.logType().getType());
            logDto.setClassName(joinPoint.getTarget().getClass().getName());
            logDto.setMethodName(joinPoint.getSignature().getName());
            logDto.setRequestUrl(requestURI);
            logDto.setCreateBy(loginAuthDto.getUserName());
            logDto.setCreateTime(new Date());
            logDto.setUpdateBy(loginAuthDto.getUserName());
            logDto.setUpdateTime(new Date());
            logDto.setStartTime(startTime);
            logDto.setEndTime(endTime);
            logDto.setExcuteTime(endTime.getTime() - startTime.getTime());
            logDto.setId(String.valueOf(SnowFlakeUtil.getFlowIdInstance().nextId()));
            //处理请求和响应
            doRequestAndResponse(iFlog,logDto,result,joinPoint);
            //执行保存
            taskExecutor.execute(()->{
                log.info("开始保存日志");
                this.restTemplate.postForObject("http://127.0.0.1:8888/auth/saveLog",
                        logDto, Integer.class);
            });

        }catch (Exception ex){
            log.error("记录日志时获取注解类出现异常={}", ex.getMessage(), ex);
        }
    }

    /**
     * 获取请求和响应
     * @param relog
     * @param logDto
     * @param result
     * @param joinPoint
     */
    private void doRequestAndResponse(LogAnnotation relog, LogDto logDto, Object result, JoinPoint joinPoint) {

        if (relog.isSaveRequestData()) {
            if (relog.logType().getType().equals(LogTypeEnum.LOGIN_LOG.getType())){
                setRequestDataNotJson(logDto);
            }else {
                setJsonRequestData(logDto, joinPoint);
            }
        }
        if (relog.isSaveResponseData()) {
            setResponseData(logDto, result);
        }
    }
    /**
     *
     */
    /**
     * 设置响应数据---普通请求
     * @param requestLog
     * @param result
     */
    private void setResponseData(LogDto requestLog, Object result) {
        try {
            if (result!=null){
                requestLog.setResponseData(String.valueOf(JacksonUtil.obj2String(result)));
            }else {
                requestLog.setResponseData("");
            }

        } catch (Exception e) {
            log.error("获取响应数据,出现错误={}", e.getMessage(), e);
        }
    }

    /**
     * 设置非json格式请求数据
     * @param logDto
     */
    private void setRequestDataNotJson(LogDto logDto) {
        try {
            Map<String,String[]> logParams=RequestUtils.getRequest().getParameterMap();
            logDto.setRequestData(ObjectUtil.mapToString(logParams));
        } catch (Exception e) {
            log.error("获取请求数据,出现错误={}", e.getMessage(), e);
        }
    }
    /**
     * 设置请求数据----json格式数据请求
     * @param logDto
     * @param joinPoint
     */
    private void setJsonRequestData(LogDto logDto, JoinPoint joinPoint) {

        try {
            Object[] args = joinPoint.getArgs();
            if (args.length == 0) {
                return;
            }
            // 过滤掉 参数校验参数
            Object[] parameter=Arrays.stream(args).filter(a->!(a instanceof BindingResult)).toArray();

            String requestData = JacksonUtil.obj2String(parameter);

            if (requestData.length() > MAX_SIZE) {
                requestData = requestData.substring(MAX_SIZE);
            }
            logDto.setRequestData(requestData);
        } catch (Exception e) {
            log.error("获取请求数据,出现错误={}", e.getMessage(), e);
        }
    }

    /**
     * 获取controller层注解
     * @param joinPoint
     * @return
     */
    private static LogAnnotation giveController(JoinPoint joinPoint){
        Method[] methods = joinPoint.getTarget().getClass().getDeclaredMethods();
        String methodName = joinPoint.getSignature().getName();
        if (null != methods && 0 < methods.length) {
            for (Method met : methods) {
                LogAnnotation relog = met.getAnnotation(LogAnnotation.class);
                if (null != relog && methodName.equals(met.getName())) {
                    return relog;
                }
            }
        }
        return null;
    }

}
