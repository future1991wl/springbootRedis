package com.zw.zwpp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zw.zwpp.utils.RedisUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZwppApplicationTests {
@Autowired
private RedisUtil redisUtil;
	@Test
	public void contextLoads() {
		redisUtil.set("heihei", "hehe");
		Object object = redisUtil.get("heihei");
		System.out.println(object);
	}

}
