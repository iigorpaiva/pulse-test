package br.com.pulse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PulseTestApplicationTests {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Test
	void contextLoads() {
		assertThat(applicationContext).isNotNull();
	}
	
	@Test
	void testProdutoServiceBean() {
		boolean produtoServiceBeanPresent = applicationContext.containsBean("produtoServiceImpl");
		assertThat(produtoServiceBeanPresent).isTrue();
	}
	
	@Test
	void testProdutoControllerBean() {
		boolean produtoControllerBeanPresent = applicationContext.containsBean("produtoController");
		assertThat(produtoControllerBeanPresent).isTrue();
	}
}
