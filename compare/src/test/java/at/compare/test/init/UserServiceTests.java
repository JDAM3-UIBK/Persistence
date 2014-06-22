package at.compare.test.init;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import at.compare.exception.UserNotFound;
import at.compare.model.User;
import at.compare.service.UserService;


//@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
//@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, WebAppConfigTest.class})
@WebAppConfiguration
public class UserServiceTests {
	
	//http://docs.spring.io/spring/docs/3.0.0.M3/reference/html/ch10s03.html
	
	private MockMvc mockMvc;
	
	private ResultMatcher resultBadRequest;
	private ResultMatcher resultOk;
	private ResultMatcher resultNotAcceptable;
	private ResultMatcher resultNotFound;
	
	
	@Autowired
	@Qualifier("userService")
	private UserService userServiceMock;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	
	@Before
	public void setUp(){
		Mockito.reset(userServiceMock);
		
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		//status
		resultBadRequest = MockMvcResultMatchers.status().isBadRequest();
		resultOk = MockMvcResultMatchers.status().isOk();
		resultNotAcceptable = MockMvcResultMatchers.status().isNotAcceptable();
		resultNotFound = MockMvcResultMatchers.status().isNotFound();
		
	}/*
	@BeforeTransaction
	public void beforeTransaction(){
		User userDB = new User("jochen","2432");
		when(userServiceMock.findByNameId("jochen")).thenReturn(userDB);
	}
	*/
	//@Rollback(false)
	@Test 
	public void userAnmeldenTest(){
		User userDB = new User("jochen","2432");
		String jsonTest = userDB.toJson();
		
		//WrongPassword
		User userClient = new User("jochen","2433");
		String jsonTestClient = userClient.toJson();
		//WrongUsername
		User userClient2 = new User("wrongjochen","2433");
		String jsonTestClient2 = userClient2.toJson();
		
		when(userServiceMock.findByNameId("jochen")).thenReturn(userDB);
		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/usermanagement/Anmelden").accept(MediaType.APPLICATION_JSON).content(jsonTestClient);
		RequestBuilder requestBuilder2 = MockMvcRequestBuilders.post("/usermanagement/Anmelden").accept(MediaType.APPLICATION_JSON).content(jsonTest);
		RequestBuilder requestBuilder3 = MockMvcRequestBuilders.post("/usermanagement/Anmelden").accept(MediaType.APPLICATION_JSON).content(jsonTestClient2);
		
		try {
			ResultActions test = this.mockMvc.perform(requestBuilder).andExpect(resultBadRequest);
			ResultActions test2 = this.mockMvc.perform(requestBuilder2).andExpect(resultOk);
			ResultActions test3 = this.mockMvc.perform(requestBuilder3).andExpect(resultBadRequest);
			
			verify(userServiceMock, times(2)).findByNameId("jochen");
			
			System.out.println("---Anmelden Test---");
			System.out.println(test.andReturn().getResponse().getContentAsString());
			System.out.println(test2.andReturn().getResponse().getContentAsString());
			System.out.println(test3.andReturn().getResponse().getContentAsString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test	
	public void userRegisterTest(){	
		
		User userDB = new User("jochen","2432");
		String jsonTest = userDB.toJson();
		User userClient = new User("tobi11","2433");
		String jsonTestClient = userClient.toJson();
		
		when(userServiceMock.findByNameId("jochen")).thenReturn(userDB);
		when(userServiceMock.insert(any(User.class))).thenReturn(userClient);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/usermanagement/Register").accept(MediaType.APPLICATION_JSON).content(jsonTest);
		RequestBuilder requestBuilder2 = MockMvcRequestBuilders.post("/usermanagement/Register").accept(MediaType.APPLICATION_JSON).content(jsonTestClient);
		
		
		try {
			ResultActions test = this.mockMvc.perform(requestBuilder).andExpect(resultNotAcceptable);
			ResultActions test2 = this.mockMvc.perform(requestBuilder2).andExpect(resultOk);
			
			//verify(userServiceMock, times(1)).findByNameId("jochen");
			System.out.println("---Register Test---");
			System.out.println(test.andReturn().getResponse().getContentAsString());
			System.out.println(test2.andReturn().getResponse().getContentAsString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test	
	public void userRegisterTest2(){	
		
		User userDB = new User("jochen","2432");
		String jsonTest = userDB.toJson();
		
		//when(userServiceMock.findByNameId("jochen")).thenReturn(userDB);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/usermanagement/Register").accept(MediaType.APPLICATION_JSON).content(jsonTest);
		
		try {
			ResultActions test = this.mockMvc.perform(requestBuilder).andExpect(resultBadRequest);
			
			System.out.println("---Register Test2---");
			System.out.println(test.andReturn().getResponse().getContentAsString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void userDeleteTest(){
		
		User userDB = new User("jochen","2432");
		String jsonTest = userDB.toJson();
		
		User userDB2 = new User("klaus","2432");
		String jsonTest2 = userDB2.toJson();
		
		User userDB3 = new User("hans","2432");
		String jsonTest3 = userDB3.toJson();
		
		when(userServiceMock.findByNameId("jochen")).thenReturn(userDB);
		when(userServiceMock.findByNameId("hans")).thenReturn(userDB3);
		
		try {
			when(userServiceMock.delete("jochen")).thenReturn(userDB);
		} catch (UserNotFound e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/usermanagement/Delete").accept(MediaType.APPLICATION_JSON).content(jsonTest);
		RequestBuilder requestBuilder2 = MockMvcRequestBuilders.post("/usermanagement/Delete").accept(MediaType.APPLICATION_JSON).content(jsonTest2);
		RequestBuilder requestBuilder3 = MockMvcRequestBuilders.post("/usermanagement/Delete").accept(MediaType.APPLICATION_JSON).content(jsonTest3);
		
		try {
			ResultActions test = this.mockMvc.perform(requestBuilder).andExpect(resultOk);
			ResultActions test2 = this.mockMvc.perform(requestBuilder2).andExpect(resultNotFound);
			ResultActions test3 = this.mockMvc.perform(requestBuilder3).andExpect(resultNotFound);
			
			System.out.println("---Delete Test---");
			System.out.println(test.andReturn().getResponse().getContentAsString());
			System.out.println(test2.andReturn().getResponse().getContentAsString());
			System.out.println(test3.andReturn().getResponse().getContentAsString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//http://docs.spring.io/spring/docs/3.2.x/spring-framework-reference/html/testing.html
}
