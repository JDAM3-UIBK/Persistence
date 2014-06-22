package at.compare.model;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * 
 * @author Joachim Rangger
 * Validation of LoggedRoute
 */

public class LoggedRouteValidator implements Validator{
	
	@Override
	public boolean supports(Class<?> clazz){
		
		return LoggedRoute.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors e) {
		//ValidationUtils.rejectIfEmpty(e, "userName", "userName.empty");
		
		LoggedRoute logRoute = (LoggedRoute) obj;
		if(logRoute.getUserName().length()<=0){
			e.rejectValue("userName", "empty");
		}
		
	}
	
}
