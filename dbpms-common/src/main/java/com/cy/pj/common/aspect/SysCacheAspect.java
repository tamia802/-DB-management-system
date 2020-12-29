package com.cy.pj.common.aspect;

import com.cy.pj.common.cache.SimpleCacheKey;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**在此切面对象中为目标对象做cache功能增强*/
@Aspect
@Component
public class SysCacheAspect {
    //本次基于map对象作为cache,存储从数据库查询到数据
    private Map<Object,Object> cache=new ConcurrentHashMap<>();//线程安全的hashmap

    //@Pointcut("bean(*ServiceImpl)")
    //@Pointcut("execution(* com.cy.pj..*.findBrands(..))") //细粒度切入点
    @Pointcut("@annotation(com.cy.pj.common.annotation.RequiredCache)")
    public void doCache(){}

    //@Pointcut("execution(* com.cy.pj..*.deleteById(..))")
    @Pointcut("@annotation(com.cy.pj.common.annotation.ClearCache)")
    public void doClearCache(){}


    @AfterReturning("doClearCache()")
    public void doAfterReturnning(){//在目标方法成功结束以后执行此方法
        cache.clear();
    }

//    @Around("doClearCache()")
//    public Object doClearAround(ProceedingJoinPoint jp)throws  Throwable{
//        Object obj=jp.proceed();//执行目标方法
//        cache.clear();
//        return obj;
//    }

    @Around("doCache()")
    public Object doCacheAround(ProceedingJoinPoint jp)throws  Throwable{
        //1.从缓存取数据,假如缓存中有则直接返回,没有则查询数据库
        Object []args=jp.getArgs();//这里获取的是目标方法执行时传入的实际参数
//        System.out.println("args="+args);
//        System.out.println(Arrays.toString(args));
        SimpleCacheKey key=new SimpleCacheKey(args);
        Object result=cache.get(key);//暂时先将可以设计为固定值
        if(result!=null)return result;
        //2.查询数据库
        result=jp.proceed();
        //3.将数据存储到缓存对象
        cache.put(key,result);
        return result;//目标方法执行结果
    }

}