package com.zxc.config;

import com.zxc.shiro.CustomAccessControlFilter;
import com.zxc.shiro.CustomHashedCredentialsMatcher;
import com.zxc.shiro.CustomRealm;
import com.zxc.shiro.ShiroCacheManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    public ShiroCacheManager shiroCacheManager(){
        return new ShiroCacheManager();
    }
    @Bean
    public CustomHashedCredentialsMatcher getCustomHashedCredentialsMatcher(){
        return new CustomHashedCredentialsMatcher();
    }
    @Bean
    public CustomRealm getCustomRealm(@Qualifier(value = "getCustomHashedCredentialsMatcher") CustomHashedCredentialsMatcher matcher){
        CustomRealm realm=new CustomRealm();
        realm.setCacheManager(shiroCacheManager());
        realm.setCredentialsMatcher(matcher);
        return realm;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(@Qualifier(value = "getCustomRealm")CustomRealm customRealm){
        DefaultWebSecurityManager defaultWebSecurityManager=new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(customRealm);
        return  defaultWebSecurityManager;
    }

    @Bean
    public ShiroFilterFactoryBean factoryBean(@Qualifier(value = "securityManager")DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        LinkedHashMap<String, Filter> filterLinkedHashMap=new LinkedHashMap<>();
        filterLinkedHashMap.put("token",new CustomAccessControlFilter());
        shiroFilterFactoryBean.setFilters(filterLinkedHashMap);
        Map<String,String> map=new LinkedHashMap<>();
        map.put("/user/login","anon");
        map.put("/swagger-ui.html/**","anon");
        map.put("/swagger-resources/**","anon");
        map.put("/v2/**","anon");
        map.put("/druid/**","anon");
        map.put("/**","token,authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }
}
