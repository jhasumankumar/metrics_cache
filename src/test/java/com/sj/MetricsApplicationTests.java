package com.sj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MetricsApplicationTests {

	@Test
	public void contextLoads() {
		final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("com.sj");
		for (final String beanName : context.getBeanDefinitionNames()) {
			System.out.println(beanName);
		}
	}

}
