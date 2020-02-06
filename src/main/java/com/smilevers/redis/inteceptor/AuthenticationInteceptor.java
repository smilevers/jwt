package com.smilevers.redis.inteceptor;

import com.alibaba.fastjson.JSON;
import com.smilevers.redis.common.RestfulBody;
import com.smilevers.redis.pojo.User;
import com.smilevers.redis.uitls.JwtHelper;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * 认证拦截
 * @Author: smile
 * @Date: 2020/2/4
 */
@Component
public class AuthenticationInteceptor implements HandlerInterceptor{
    
    @Autowired
    private StringRedisTemplate redisTemplate;
    
    /**
     * 拦截所有需要认证的请求，验证token
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        // 允许跨域
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 接收客户端的token
        String token = request.getHeader("token");
        // 解析客户端token
        Claims claims = JwtHelper.verifyJwt(token);
        
        // token验证1-判断token是否能被解析，解析失败，返回失败信息
        if (claims == null) {
            Object json =  JSON.toJSON(new RestfulBody<>(404, "token校验失败，请重新登录"));
            this.responseWriter(json, response);
            return false;
        }
        // 从解析出的客户端token获取用户
        Object object = claims.get("user");
        User user = JSON.parseObject(JSON.toJSONString(object), User.class);
        System.out.println(user.getUserAgent());
        // 根据客户端token中的信息从redis中查询token
        String redistoken = redisTemplate.opsForValue().get("token:" + user.getId());
        
        // token验证2-判断token是否存在,不存在返回失败信息
        if (StringUtils.isBlank(redistoken)) {
            Object json =  JSON.toJSON(new RestfulBody<>(404, "token超时，请重新登录"));
            this.responseWriter(json, response);
            return false;
        }
        // 解析redis中的token，获取user信息
        Claims redisClaims = JwtHelper.verifyJwt(redistoken);
        Object redisObject = redisClaims.get("user");
        User redisUser = JSON.parseObject(JSON.toJSONString(redisObject), User.class);
        
        // token验证3-判断客户端token中的UA和redis中的token的UA是否一致，不一致则返回错误信息
        if (!redisUser.getUserAgent().equals(user.getUserAgent())) {
            Object json =  JSON.toJSON(new RestfulBody<>(404, "token无效，请重新登录"));
            this.responseWriter(json, response);
            return false;
        }
        return true;
    }
    
    /**
     * 给客户端响应json对象封装的方法
     */
    public void responseWriter(Object json, HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = null;
        try {
            writer =  response.getWriter();
            writer.print(json);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            writer.close();
        }
    }
}
