package at.compare.test.init;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import at.compare.service.RouteService;
import at.compare.service.UserService;


@Configuration
public class TestContext {
	
	/*  @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        messageSource.setBasename("i18n/messages");
        messageSource.setUseCodeAsDefaultMessage(true);

        return messageSource;
    }*/
	
	@Bean
	public UserService userService(){
		return Mockito.mock(UserService.class);
	}
	@Bean
	public RouteService routeService(){
		return Mockito.mock(RouteService.class);
	}
}
