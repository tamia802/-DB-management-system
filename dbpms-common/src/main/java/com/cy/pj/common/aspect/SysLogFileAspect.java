package com.cy.pj.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
/**@Aspect 注解用于定义切面对象*/
@Aspect
@Component
public class SysLogFileAspect {
     /**切入点的定义
      * 1)使用@Pointcut注解进行切入点的描述
      * 2)使用bean表达式定义切入点,语法:bean(spring容器中管理的某个bean的名字)
      * bean表达式是一种粗粒度的切入点表达式(不能具体到bean中哪个方法)
      * */
     //@Pointcut("bean(categoryServiceImpl)")
     @Pointcut("bean(*ServiceImpl)")
     public void doLog(){}//这里的doLog()方法,方法体内不需要写任何内容,作用是承载切入点

     /**在切入点对应的目标方法执行时,要进行的动作可以以如下方式进行定义
      * @param joinPoint 封装了切入点集合方法中的某个正在执行的目标方法,
      *        ProceedingJoinPoint 类型的连接点只能应用在@Around注解描述的方法参数中
      * @return 为目标方法的返回结果
      * */
     @Around("doLog()") //@Around注解描述在方法可以在目标方法执行之前,之后做一些业务拓展
     public Object doAround(ProceedingJoinPoint joinPoint)throws Throwable{
         long t1=System.currentTimeMillis();
         Object result=joinPoint.proceed();//去调用目标方法
         long t2=System.currentTimeMillis();
         System.out.println("execute time "+(t2-t1));
         //..........
         return result;//目标方法的执行结果
     }
}
