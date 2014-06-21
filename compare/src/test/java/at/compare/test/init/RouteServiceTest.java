package at.compare.test.init;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;

import at.compare.exception.RouteNotFound;
import at.compare.init.WebAppConfig;
import at.compare.model.Auto;
import at.compare.model.LoggedRoute;
import at.compare.model.User;
import at.compare.service.RouteService;
import at.compare.service.UserService;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, WebAppConfig.class})
@WebAppConfiguration
public class RouteServiceTest {
	
	private MockMvc mockMvc;
	
	private ResultMatcher resultBadRequest;
	private ResultMatcher resultOk;
	private ResultMatcher resultNotAcceptable;
	private ResultMatcher resultNotFound;
	
	private Date date;
	private Date dateWrong;
	LoggedRoute route;
	LoggedRoute route1;
	LoggedRoute route2;
	LoggedRoute route3;
	LoggedRoute route5;
	
	List<LoggedRoute> listRoutes;
	String username;
	User user; 
	
	@Autowired
	@Qualifier("routeService")
	private RouteService routeServiceMock;
	
	@Autowired
	@Qualifier("userService")
	private UserService userServiceMock;
	
	//@Autowired
	//Date date;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	@Before
	public void setUp() throws Exception {
		Mockito.reset(routeServiceMock);
		
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		//status
		resultBadRequest = MockMvcResultMatchers.status().isBadRequest();
		resultOk = MockMvcResultMatchers.status().isOk();
		resultNotAcceptable = MockMvcResultMatchers.status().isNotAcceptable();
		resultNotFound = MockMvcResultMatchers.status().isNotFound();
		
		date = new Date(23424);
		dateWrong = new Date(999999429);
		username = "ranger1966";
		route = new LoggedRoute("ranger1966", 234 ,234, "type", 324, 112, date, 24342, 324, 123); 
		route1 = new LoggedRoute("wrongUser", 256, 265, "type", 324, 112, date, 2242, 324, 123);
		route2 = new LoggedRoute("ranger1966", 256, 265, "type", 324, 112, date, 2242, 324, 123);
		route3 = new LoggedRoute("", 256, 265, "type", 324, 112, date, 2242, 324, 123);
		route5 = new LoggedRoute("ranger1966", 256, 265, "type", 324, 112, dateWrong, 2242, 324, 123);
		
		
		listRoutes = new LinkedList<LoggedRoute>();
		listRoutes.add(route);
		listRoutes.add(route2);
		
		user = new User(username,"2432");
	}

	@Test
	public void idTest(){
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/routemanagement/findRouteWithId?id=1");
		RequestBuilder requestBuilder2 = MockMvcRequestBuilders.get("/routemanagement/findRouteWithId?id=2");
		RequestBuilder requestBuilder3 = MockMvcRequestBuilders.get("/routemanagement/findRouteWithId?id=3");
		
		when(routeServiceMock.findById(1L)).thenReturn(route);
		when(routeServiceMock.findById(3L)).thenReturn(route3);
		when(userServiceMock.findByNameId(username)).thenReturn(user);
		
		try {
			ResultActions test = this.mockMvc.perform(requestBuilder).andExpect(resultOk);
			ResultActions test2 = this.mockMvc.perform(requestBuilder2).andExpect(resultNotFound);
			ResultActions test3 = this.mockMvc.perform(requestBuilder3).andExpect(resultBadRequest);
			
			System.out.println("---id Test---");
			System.out.println(test.andReturn().getResponse().getContentAsString());
			System.out.println(test2.andReturn().getResponse().getContentAsString());
			System.out.println(test3.andReturn().getResponse().getContentAsString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void deleteRouteTest() throws RouteNotFound{
		//fail("not implemented");
		String jsonTest = route.toJson();
		
		when(routeServiceMock.findByUsername(username)).thenReturn(listRoutes);
		when(routeServiceMock.delete(anyLong())).thenReturn(route);
	
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/routemanagement/deleteRoute").accept(MediaType.APPLICATION_JSON).content(jsonTest);
		
		try {
			ResultActions test = this.mockMvc.perform(requestBuilder).andExpect(resultOk);
			
			System.out.println("---deleteRoute Test---");
			System.out.println(test.andReturn().getResponse().getContentAsString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void deleteRouteTest2() throws RouteNotFound{
		
		
		String jsonTest = route1.toJson();
		String jsonTest2 = route5.toJson();
		String jsonTest3 = route.toJson();
		
		when(routeServiceMock.findByUsername(username)).thenReturn(listRoutes);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/routemanagement/deleteRoute").accept(MediaType.APPLICATION_JSON).content(jsonTest);
		RequestBuilder requestBuilder2 = MockMvcRequestBuilders.post("/routemanagement/deleteRoute").accept(MediaType.APPLICATION_JSON).content(jsonTest2);
		RequestBuilder requestBuilder3 = MockMvcRequestBuilders.post("/routemanagement/deleteRoute").accept(MediaType.APPLICATION_JSON).content(jsonTest3);
		
		
		try {
			ResultActions test = this.mockMvc.perform(requestBuilder).andExpect(resultNotFound);
			ResultActions test2 = this.mockMvc.perform(requestBuilder2).andExpect(resultNotFound);
			ResultActions test3 = this.mockMvc.perform(requestBuilder3).andExpect(resultNotFound);
			
			System.out.println("---deleteRoute Test2---");
			System.out.println(test.andReturn().getResponse().getContentAsString());
			System.out.println(test2.andReturn().getResponse().getContentAsString());
			System.out.println(test3.andReturn().getResponse().getContentAsString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void deleteRoutesWithUsernameTest() throws RouteNotFound{

		String jsonTest = route.toJson();
		String jsonTest2 = route3.toJson();
		
		when(routeServiceMock.findByUsername(username)).thenReturn(listRoutes);
		
		when(routeServiceMock.delete(anyLong())).thenReturn(route);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/routemanagement/deleteRoutesWithUsername").accept(MediaType.APPLICATION_JSON).content(jsonTest);
		RequestBuilder requestBuilder2 = MockMvcRequestBuilders.post("/routemanagement/deleteRoutesWithUsername").accept(MediaType.APPLICATION_JSON).content(jsonTest2);
		
		try {
			ResultActions test = this.mockMvc.perform(requestBuilder).andExpect(resultOk);
			ResultActions test2 = this.mockMvc.perform(requestBuilder2).andExpect(resultNotFound);
			
			System.out.println("---deleteRoutesWithUsername Test---");
			System.out.println(test.andReturn().getResponse().getContentAsString());
			System.out.println(test2.andReturn().getResponse().getContentAsString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Test
	public void deleteRoutesWithUsernameTest2() throws RouteNotFound{

		
		String jsonTest = route.toJson();
		
		when(routeServiceMock.findByUsername(username)).thenReturn(listRoutes);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/routemanagement/deleteRoutesWithUsername").accept(MediaType.APPLICATION_JSON).content(jsonTest);
		
		try {
			ResultActions test = this.mockMvc.perform(requestBuilder).andExpect(resultNotFound);
			
			System.out.println("---deleteRoutesWithUsername Test2---");
			System.out.println(test.andReturn().getResponse().getContentAsString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Test
	public void usernameTest(){
		
		when(routeServiceMock.findByUsername(username)).thenReturn(listRoutes);
		when(userServiceMock.findByNameId(username)).thenReturn(user);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/routemanagement/username").content(username);
		RequestBuilder requestBuilder2 = MockMvcRequestBuilders.post("/routemanagement/username").content("");
		try {
			ResultActions test = this.mockMvc.perform(requestBuilder).andExpect(resultOk);
			ResultActions test2 = this.mockMvc.perform(requestBuilder2).andExpect(resultBadRequest);
			
			System.out.println("---username Test---");
			System.out.println(test.andReturn().getResponse().getContentAsString());
			System.out.println(test2.andReturn().getResponse().getContentAsString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void dateTest(){
		Date date1 = new Date(23000);
		Date date2 = new Date(23424);
		assertEquals(date.compareTo(date2), 0);
		assertEquals(date2.compareTo(date1), 1);
	}
	
	@Test
	public void saveRoute(){
		
		//LoggedRoute spyRoute = spy(route);
		
		String jsonTest = route.toJson();

		when(routeServiceMock.insert(Mockito.any(LoggedRoute.class))).thenReturn(route);
		when(userServiceMock.findByNameId(username)).thenReturn(user);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/routemanagement/saveRoute").accept(MediaType.APPLICATION_JSON).content(jsonTest);

		try {
			ResultActions test = this.mockMvc.perform(requestBuilder).andExpect(resultOk);
			
			System.out.println("---saveRoute Test---");
			System.out.println(test.andReturn().getResponse().getContentAsString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Mockito.verify(routeServiceMock);
	}
	@Test
	public void saveRoute2(){
		
		//LoggedRoute spyRoute = spy(route);
		
		String jsonTest = route3.toJson();
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/routemanagement/saveRoute").accept(MediaType.APPLICATION_JSON).content(jsonTest);


		try {
			ResultActions test = this.mockMvc.perform(requestBuilder).andExpect(resultNotAcceptable);
			
			System.out.println("---saveRoute Test2---");
			System.out.println(test.andReturn().getResponse().getContentAsString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Mockito.verify(routeServiceMock);
	}
	@Test
	public void saveRoute3(){
		
		//LoggedRoute spyRoute = spy(route);
		
		when(routeServiceMock.insert(Mockito.any(LoggedRoute.class))).thenReturn(route);
		
		String jsonTest = route1.toJson();
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/routemanagement/saveRoute").accept(MediaType.APPLICATION_JSON).content(jsonTest);


		try {
			ResultActions test = this.mockMvc.perform(requestBuilder).andExpect(resultBadRequest);
			
			System.out.println("---saveRoute Test3---");
			System.out.println(test.andReturn().getResponse().getContentAsString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Mockito.verify(routeServiceMock);
	}
	@Test
	public void showRoutePerUser(){
		
		String jsonTest = route.toJson();
		String jsonTest2 = route1.toJson();
		
		when(routeServiceMock.findByUsername(username)).thenReturn(listRoutes);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/routemanagement/showRoutePerUser").accept(MediaType.APPLICATION_JSON).content(jsonTest);
		RequestBuilder requestBuilder2 = MockMvcRequestBuilders.post("/routemanagement/showRoutePerUser").accept(MediaType.APPLICATION_JSON).content(jsonTest2);
		
		try {
			ResultActions test = this.mockMvc.perform(requestBuilder).andExpect(resultOk);
			ResultActions test2 = this.mockMvc.perform(requestBuilder2).andExpect(resultNotFound);
			
			System.out.println("---showRoutePerUser Test---");
			System.out.println(test.andReturn().getResponse().getContentAsString());
			System.out.println(test2.andReturn().getResponse().getContentAsString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	@After
	public void tearDown() throws Exception {
	}

}
