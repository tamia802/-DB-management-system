package com.cy.pj.common.config;

import com.cy.pj.common.web.TimeAccessInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringWebConfig implements WebMvcConfigurer {
    /**注册拦截器*/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
          registry.addInterceptor(new TimeAccessInterceptor())
                   //设置要拦截的路径
                  .addPathPatterns("/user/doLogin");
    }
}
