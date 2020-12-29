package com.cy.pj.common.aspect;

import com.cy.pj.common.annotation.RequiredLog;
import com.cy.pj.common.util.IPUtils;
import com.cy.pj.sys.pojo.SysLog;
import com.cy.pj.sys.pojo.SysUser;
import com.cy.pj.sys.service.SysLogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class SysLogAspect {
       @Autowired
       private SysLogService sysLogService;
       @Pointcut("@annotation(com.cy.pj.common.annotation.RequiredLog)")
       public void doLog(){}

       @Around("doLog()")
       public Object doAround(ProceedingJoinPoint joinPoint)throws Throwable{
              long t1=System.currentTimeMillis();
              Object result=joinPoint.proceed();
              long t2=System.currentTimeMillis();
              doSaveLog(joinPoint,(t2-t1));
              return result;//目标方法的执行结果
       }
       /**记录用户正常行为日志:
        * username  (登录用户)
        * ip (通过工具类获取)
        * operation (一般是通过注解指定或定义)
        * method (目标类型的类全名+方法名)
        * params(执行目标方法时传入的参数)
        * time (执行目标方法时的耗时时长)
        * createdtime (日志的记录时间)
        * */
       private void doSaveLog(ProceedingJoinPoint jointPoint,long time) throws NoSuchMethodException, JsonProcessingException {
          //1.获取用户行为日志
          String ip= IPUtils.getIpAddr();
          //获取目标对象类型(为什么要获取此类型呢?要基于此类型获取目标方法)
          Class<?> targetCls=jointPoint.getTarget().getClass();
          System.out.println("targetCls="+targetCls.getName());
          //获取目标方法对象
          MethodSignature ms=(MethodSignature) jointPoint.getSignature();//获取方法签名(存储了方法信息的一个对象)
          Method targetMethod=
               targetCls.getDeclaredMethod(ms.getName(),ms.getParameterTypes());
          //获取目标方法上的requiredLog注解
          RequiredLog requiredLog=targetMethod.getAnnotation(RequiredLog.class);
          //获取注解中operation属性的值
          String operation=requiredLog.operation();;//操作名
          String method=targetCls.getName()+"."+targetMethod.getName();//目标方法(目标类型的类全名+方法名)
          String params= new ObjectMapper().writeValueAsString(jointPoint.getArgs());//(执行目标方法时传入的实际参数值)
          //2.封装用户行为日志
          SysLog log=new SysLog();
          SysUser user= (SysUser)SecurityUtils.getSubject().getPrincipal();
          log.setUsername(user.getUsername());
          log.setIp(ip);
          log.setOperation(operation);
          log.setMethod(method);
          log.setParams(params);
          log.setTime(time);
          log.setCreatedTime(new Date());
          //3.保存用户行为日志
          sysLogService.saveObject(log);
       }
}
