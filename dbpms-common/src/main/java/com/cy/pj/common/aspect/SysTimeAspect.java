package com.cy.pj.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 通过此对象演示切面中各种通知(Advice)方法的执行
 */
@Aspect
@Component
public class SysTimeAspect {

      @Pointcut("bean(categoryServiceImpl)")
      public void doTime(){}

      @Before("doTime()")
      public void doBefore(){
          System.out.println("@Before");
      }
      @After("doTime()")
      public void doAfter(){
          System.out.println("@After");
      }
      @AfterReturning("doTime()")
      public void doAfterReturning(){
          System.out.println("@AfterReturning");
      }

      @AfterThrowing("doTime()")
      public void doAfterThrowing(){
          System.out.println("@AfterThrowing");
      }

     @Around("doTime()")
     public Object doAround(ProceedingJoinPoint jp)throws Throwable{
            System.out.println("@Around.before");
        try {
            Object result = jp.proceed();//假如有before通知先执行before,没有则目标方法
            System.out.println("@Around.after");
            return result;
        }catch (Throwable e){
            System.out.println("@Around.exception");
            throw  e;
        }
     }


}
