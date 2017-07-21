package test;

import com.zero.Application;
import com.zero.database.redis.JedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)// 指定spring-boot的启动类
//@SpringApplicationConfiguration(classes = Application.class)// 1.4.0 前版本
public class RedisTest {

    @Autowired
    private JedisUtils jedisUtils;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Test
    public void testRedis() {
        Map<String, String> map = new HashMap<>();

        map.put("name", "李健青");
        jedisUtils.setMap("lijianqing", map, 1800);

        Map<String, String> redisMap = jedisUtils.getMap("lijianqing");
        log.info("获取到的redis Map:{}", redisMap.toString());

    }
}