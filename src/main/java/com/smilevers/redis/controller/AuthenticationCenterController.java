package com.smilevers.redis.controller;

import com.alibaba.fastjson.JSON;
import com.smilevers.redis.common.RestfulBody;
import com.smilevers.redis.pojo.User;
import com.smilevers.redis.uitls.JwtHelper;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * 认证中心
 * @Author: smile
 * @Date: 2020/2/3
 */
@RestController
public class AuthenticationCenterController {
    
    @Autowired
    private StringRedisTemplate redisTemplate;
    
    /**
     * 登录接口，生成token
     * @param user 接收用户信息
     * @param request
     * @return
     */
    @PostMapping("login")
    public ResponseEntity loginToken(User user, HttpServletRequest request) {
        // 校验用户名或密码
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
            return new ResponseEntity(new RestfulBody<>(401,"请输入正确的用户名或密码"), HttpStatus.BAD_REQUEST);
        }
        // 查询数据库验证用户名或密码
        if (false) {
            return new ResponseEntity(new RestfulBody<>(401,"用户名或密码错误"), HttpStatus.BAD_REQUEST);
        }
        // 获取浏览器UA，用于下次判断是否同一用户
        String userAgent = request.getHeader("user-agent");
        user.setId(120L);
        user.setUserAgent(userAgent);
        // 使用user信息生成token
        String token = JwtHelper.generateToken("user", user);
        // 将签名保存到redis中，并设置过期时间
        redisTemplate.opsForValue().set("token:"+user.getId(), token, 60*60, TimeUnit.SECONDS);
        return new ResponseEntity(new RestfulBody<User>(200,token,"成功"), HttpStatus.CREATED);
    }
    
    /**
     * 登出接口
     * @param request
     * @return
     */
    @GetMapping("logout")
    public ResponseEntity logout(HttpServletRequest request) {
        // 接收客户端的token
        String token = request.getHeader("token");
        // 解析客户端token
        Claims claims = JwtHelper.verifyJwt(token);
        // 获取token中的用户信息
        if (claims != null) {
            // 从解析出的客户端token获取用户
            Object object = claims.get("user");
            User user = JSON.parseObject(JSON.toJSONString(object), User.class);
            redisTemplate.delete("token:" + user.getId());
        }
        return new ResponseEntity(new RestfulBody<User>(200,"退出成功"), HttpStatus.valueOf(200));
    }
    
    
    @GetMapping("get")
    public ResponseEntity get() {
        return new ResponseEntity(new RestfulBody<>(200,null,"成功","hello world"), HttpStatus.OK);
    }
}
