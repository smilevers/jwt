package com.smilevers.redis;

import com.smilevers.redis.pojo.User;
import com.smilevers.redis.uitls.JwtHelper;
import io.jsonwebtoken.Claims;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: smile
 * @Date: 2020/2/4
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TokenTest {
    
    @Test
    public void tokenTest() throws InterruptedException {
        User user = new User();
        user.setId(153L);
        user.setUsername("白旭峰");
        user.setPassword("465");
        String token = JwtHelper.generateToken("user",user);
        System.out.println(token);
        String resolveSignture = JwtHelper.resolveSignture(token);
        System.out.println(resolveSignture);
        Claims claims = JwtHelper.verifyJwt(token);
        System.out.println(claims);
    }
    
    @Test
    public void signatureTest() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1ODA5MTI4MzEsInVzZXJJZCI6MTIwLCJpYXQiOjE1ODA5MTI4MjYsImp0aSI6InRva2VuSWQifQ.IdpT7_8P1Y0Z6HsNf9bIg1x8X-nEp4kr0PEefxE86Ug";
        boolean b = JwtHelper.isSignture(token);
        System.out.println(b);
    }
}
