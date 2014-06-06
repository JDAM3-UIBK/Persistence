package at.compare.test.init;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import at.compare.init.WebAppConfig;
import at.compare.model.User;
import at.compare.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, WebAppConfig.class})
@WebAppConfiguration
public class ServiceTests {
	
	//http://docs.spring.io/spring/docs/3.0.0.M3/reference/html/ch10s03.html
	
	
	private MockMvc mockMvc;
	
	@Autowired
	@Qualifier("userService")
	private UserService userServiceMock;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	
	@Before
	public void setUp(){
		Mockito.reset(userServiceMock);
		
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	/*
	 * 	@Test
		public void tilesDefinitions() throws Exception {
		this.mockMvc.perform(get("/"))
									.andExpect(status().isOk())
									.andExpect(forwardedUrl("/WEB-INF/layouts/standardLayout.jsp"));
		}
	 */
	@Test
	public void userRegisterTest(){
		
		User user = new User("tobi11","2432");
		String jsonTest = user.toJson();
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/usermanagement/Register").accept(MediaType.APPLICATION_JSON).content(jsonTest);	
		ResultMatcher resultMatcher = MockMvcResultMatchers.status().isOk();
		
		try {
			this.mockMvc.perform(requestBuilder).andExpect(resultMatcher);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
