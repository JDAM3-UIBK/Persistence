package at.compare.test.init;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import at.compare.repository.UserRepository;
import at.compare.service.RouteService;
import at.compare.service.UserService;


@Configuration
public class TestContext {
	
	@Bean
	public UserService userService(){
		
		return Mockito.mock(UserService.class);
		
	}
	@Bean
	public UserRepository userRepository(){
		
		return Mockito.mock(UserRepository.class);
	}
	@Bean
	public RouteService routeService(){
		return Mockito.mock(RouteService.class);
	}
}
