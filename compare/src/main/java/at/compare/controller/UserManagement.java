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
	 public @ResponseBody ResponseEntity<String> registerUser(@RequestBody String userJson){
		 
		 HttpHeaders headers = new HttpHeaders();
	     headers.add("Content-Type", "application/json; charset=utf-8");
		 
		 User userClient = new JSONDeserializer<User>().deserialize(userJson, User.class );
		 
		 //Check, if User already exists
		 User userTmp = userService.findByNameId(userClient.getUser_name_id());
		 
		 if(userTmp == null){
			 return new ResponseEntity<String>(" User Not Found! " ,headers, HttpStatus.NOT_FOUND);
		 }
		 
		 if(userTmp.getUser_name_id().compareTo(userClient.getUser_name_id())!=0){
			 User userDB = userService.insert(userClient);
			 return new ResponseEntity<String>(userDB.toJson(),headers,HttpStatus.OK);
			 	 
		 }
		 return new ResponseEntity<String>(" User is already Registered! " ,headers, HttpStatus.NOT_ACCEPTABLE);	 
		 
	 }
	 
	 @RequestMapping(value="/Anmelden" ,method = RequestMethod.POST)
	 public @ResponseBody ResponseEntity<String> anmeldenUser(@RequestBody String userJson) throws UserNotFound {						
		 
		 HttpHeaders headers = new HttpHeaders();
	     headers.add("Content-Type", "application/json; charset=utf-8");
	     
		 User userClient = new JSONDeserializer<User>().deserialize(userJson, User.class );
		 
		 //search for User, if it exists
		 User userDB = userService.findByNameId(userClient.getUser_name_id());
		 
		 if(userDB == null){
			 return new ResponseEntity<String>(" User Not Found! " ,headers, HttpStatus.NOT_FOUND);
		 }
		 
		 if(userClient.getUser_pw().compareTo(userDB.getUser_pw()) != 0){
			 return new ResponseEntity<String>(" Password or User wrong!! ", headers, HttpStatus.NOT_FOUND);
		 }
		
	         
		 return new ResponseEntity<String>(userDB.toJson(),headers,HttpStatus.OK);
		 
	 }
	 
	 @RequestMapping(value="/Delete", method = RequestMethod.POST)
	 public @ResponseBody ResponseEntity<String> deleteUser(@RequestBody String userJson){
		 
		 HttpHeaders headers = new HttpHeaders();
	     headers.add("Content-Type", "application/json; charset=utf-8");
		 
		 User userClient = new JSONDeserializer<User>().deserialize(userJson, User.class );
		 
		//search for User, if it exists
		 User userTmp = userService.findByNameId(userClient.getUser_name_id());
		 
		 User userDB = null;
		 
		 if(userTmp == null){
			 return new ResponseEntity<String>("User not Found",headers, HttpStatus.NOT_FOUND);
		 }
		 try {
				userDB = userService.delete(userClient.getUser_name_id());
		 } catch (UserNotFound e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		 }
		
		
		 
		 return new ResponseEntity<String>(userDB.toJson(),headers,HttpStatus.OK);
	 }
	 @RequestMapping(value="/changePassword", method = RequestMethod.POST)
	 public @ResponseBody ResponseEntity<String> changePassword(@RequestBody String userJson){
		 
		 HttpHeaders headers = new HttpHeaders();
	     headers.add("Content-Type", "application/json; charset=utf-8");
	     /*
		 User userClient = new JSONDeserializer<User>().deserialize(userJson, User.class );
		 
		 User userDB = null;
		 User userTmp = userService.findByNameId(userClient.getUser_name_id());
		 
		 if(userTmp.getUser_pw().compareTo(userClient.getUser_pw())==0){
			 try {
				userDB = userService.update(userClient);
				
				return new ResponseEntity<String>(headers, HttpStatus.OK);
			 } catch (UserNotFound e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 }
		 }
		 */
		 return new ResponseEntity<String>(" NOT IMPLEMENTED!! ", headers, HttpStatus.NOT_FOUND);
	 }
	 
}
