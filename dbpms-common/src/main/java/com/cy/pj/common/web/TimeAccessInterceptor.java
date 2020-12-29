package com.cy.pj.common.web;

import com.cy.pj.common.exception.ServiceException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalTime;

/**定义时间访问拦截器
 * Spring MVC 模块中拦截器的标准为HandlerInterceptor接口.这接口中的
 * 方法可以实现对目标Handler(@Controller)方法的访问拦截.
 * */
public class TimeAccessInterceptor implements HandlerInterceptor {
    /***
     * preHandle方法会在@Controller或@RestController描述的类中的请求响应处理方法
     * 之前执行.
     * @param request
     * @param response
     * @param handler
     * @return 返回值为true表示放行,false表示请求到此结束.
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("==preHandle==");
        LocalTime time=LocalTime.now();//LocalTime为jdk8中的一个时间对象
        int hour=time.getHour();//获取当前时间对应的小时
        System.out.println("hour="+hour);
        if(hour>=9&&hour<=18)return true;
        throw new ServiceException("不在规定的访问时间:9~18:00");
    }

}
//接口中所有的方法都是抽象吗?(在JDK8以后已经不成立了)