package com.cy.pj.common.config;
import com.cy.pj.sys.service.realm.ShiroUserRealm;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration //此注解描述的类为spring中的配置类
public class SpringShiroConfig {

    @Bean
    public Realm realm() {
      return new ShiroUserRealm();
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        LinkedHashMap<String,String> map=new LinkedHashMap<>();
        map.put("/bower_components/**","anon");
        map.put("/build/**","anon");
        map.put("/dist/**","anon");
        map.put("/plugins/**","anon");
        map.put("/user/doLogin","anon");
        map.put("/doLogout","logout");
        map.put("/**","authc");
        chainDefinition.addPathDefinitions(map);
        return chainDefinition;
    }
    @Bean
    protected CacheManager shiroCacheManager() {
        return new MemoryConstrainedCacheManager();
    }
}
