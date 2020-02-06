package com.smilevers.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author: smile
 * @Date: 2020/2/3
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ZsetTest {
    
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    
    /**
     * 存值，存排序分数
     */
    @Test
    public void set() {
        redisTemplate.boundZSetOps("hero").add("关羽", 2);
        redisTemplate.boundZSetOps("hero").add("张飞", 3);
        redisTemplate.boundZSetOps("hero").add("诸葛亮", 1);
        redisTemplate.boundZSetOps("hero").add("赵云", 4);
    }
    /**
     * 取值
     */
    @Test
    public void get() {
        Set<Object> hero = redisTemplate.boundZSetOps("hero").range(0, -1);
        System.out.println(hero);
    }
    
    /**
     * 删除集合中的一个值
     */
    public void del() {
        redisTemplate.boundZSetOps("hero").remove("关羽");
        
    }
}
