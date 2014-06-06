package at.compare.test.init;

import static org.junit.Assert.*;

import javax.servlet.ServletContext;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

public class MockHttpServletRequestBuilderTests {
	
	//bsp: https://github.com/spring-projects/spring-test-mvc/blob/master/src/test/java/org/springframework/test/web/server/request/MockHttpServletRequestBuilderTests.java
	
	
	private MockHttpServletRequestBuilder builder;

	private ServletContext servletContext;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
