package at.compare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import flexjson.JSONDeserializer;
import at.compare.exception.UserNotFound;
import at.compare.model.User;
import at.compare.repository.RouteRepository;
import at.compare.service.AutoService;
import at.compare.service.UserService;


@Controller
@RequestMapping(value="/usermanagement")
public class UserManagement {
	
	@Autowired
	UserService userService;
	
	// Bsp: http://localhost:8080/cp414/autoid?id=2 
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public @ResponseBody String create(
							@RequestParam(value="username", required=true)String username,
							@RequestParam(value="password", required=true )String password
							){
		User user = new User();
		user.setUser_name_id(username);
		user.setUser_pw(password);
		
		userService.insert(user);
		
		return "User: " + username + " successfully created";
	}
	

	 @RequestMapping(value = "/createPost", method = RequestMethod.POST)
	 public @ResponseBody String createPost(@RequestParam("json") String json,
			 								@RequestBody String json2
			 								) {
		 									
		 //System.out.println("Received POST request:" + json);
    	
	     return json;
	 }
	//http://docs.spring.io/spring-roo/reference/html/base-json.html
	 
	 @RequestMapping(method = RequestMethod.POST)
	 public @ResponseBody User compareUser(@RequestBody String userJson) throws UserNotFound {						
		 //System.out.println("Received POST request:" + json);
	
		 User userClient = new JSONDeserializer<User>().deserialize(userJson, User.class );
		 //search for User, if it exists
		 User userDB = userService.findByNameId(userClient.getUser_name_id());
		 
		 if(userDB == null){
			 
			 throw new UserNotFound();	 
		 }
		 if(userClient.getUser_pw().compareTo(userDB.getUser_pw()) != 0){
			 userDB = null;
		 }
		 
		 return userDB;
		 
	 }
	 
	 @RequestMapping(value = "/jsonTest/{id}", headers = "Accept=application/json")
	
	 public  @ResponseBody ResponseEntity<String> showJson(@PathVariable("id") String id) {
	     User user = userService.findByNameId(id);
	     HttpHeaders headers = new HttpHeaders();
	     headers.add("Content-Type", "application/json; charset=utf-8");
	   
	     if (user == null) {
	         return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
	     }
	  
	     return new ResponseEntity<String>(user.toJson(), headers, HttpStatus.OK);
	 }
	 
	 @RequestMapping(value="/Register", method = RequestMethod.POST)
	 public @ResponseBody User registerUser(@RequestBody String userJson){
		 
		 User userClient = new JSONDeserializer<User>().deserialize(userJson, User.class );
		 
		 //Überprüfung, ob User nicht schon vorhanden
		 //User userDB = userService.findByNameId(userClient.getUser_name_id());
		 /*
		 if(userDB != null){
			 
		 }*/
		 userService.insert(userClient);
		 
		 return null;
	 }
	 
	 @RequestMapping(value="/Anmelden" ,method = RequestMethod.POST)
	 public @ResponseBody User anmeldenUser(@RequestBody String userJson) throws UserNotFound {						
		 //System.out.println("Received POST request:" + json);
	
		 User userClient = new JSONDeserializer<User>().deserialize(userJson, User.class );
		 //search for User, if it exists
		 User userDB = userService.findByNameId(userClient.getUser_name_id());
		 
		 if(userDB == null){
			 
			 throw new UserNotFound();	 
		 }
		 if(userClient.getUser_pw().compareTo(userDB.getUser_pw()) != 0){
			 userDB = null;
		 }
		 
		 return userDB;
		 
	 }
	 
	 @RequestMapping(value="/Delete", method = RequestMethod.POST)
	 public @ResponseBody User deleteUser(@RequestBody String userJson){
		 
		 User userClient = new JSONDeserializer<User>().deserialize(userJson, User.class );
		 
		 //Überprüfung, ob User nicht schon vorhanden
		 //User userDB = userService.findByNameId(userClient.getUser_name_id());
		 /*
		 if(userDB != null){
			 
		 }*/
		 User userDB = null;
		 try {
			userDB = userService.delete(userClient.getUser_name_id());
		} catch (UserNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return userDB;
	 }
	 @RequestMapping(value="/changePassword", method = RequestMethod.POST)
	 public @ResponseBody User changePassword(@RequestBody String userJson){
		 User userClient = new JSONDeserializer<User>().deserialize(userJson, User.class );
		 
		 User userDB = null;
		 try {
			userDB = userService.update(userClient);
		} catch (UserNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return userDB;
	 }
	 
}
