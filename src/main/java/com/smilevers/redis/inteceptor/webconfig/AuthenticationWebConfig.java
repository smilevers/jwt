package com.smilevers.redis.inteceptor.webconfig;

import com.smilevers.redis.inteceptor.AuthenticationInteceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *  添加拦拦截器的截路径和排除拦截路径
 * @Author: smile
 * @Date: 2020/2/4
 */
@Configuration
public class AuthenticationWebConfig implements WebMvcConfigurer {
    
    /**
     * 注入拦截器
     */
    @Autowired
    private AuthenticationInteceptor authenticationInteceptor;
    
    /**
     * 添加拦截规则
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInteceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login")
                .excludePathPatterns("/logout");
    }
}
