package com.smilevers.redis;

import com.alibaba.fastjson.JSON;
import com.smilevers.redis.common.RestfulBody;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * json测试
 * @Author: smile
 * @Date: 2020/2/4
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JsonTest {
    
    @Test
    public void json() {
        RestfulBody restfulBody = new RestfulBody(200, "成功");
        Object json = JSON.toJSON(restfulBody);
        System.out.println(json);
    }
}
