package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
// 选择需要的配置
// @ContextConfiguration(classes = DataSource1Config.class)
// 排除一些不需要的自动配置
// @EnableAutoConfiguration(exclude=SecurityAutoConfiguration.class)
public class DemoApplicationTests {
	@Value("${com.sabo.value}")
	private int value;

	@Test
	public void contextLoads() {
	}

	@Test
	public void test() {
		assertEquals(200, value);
	}

}
