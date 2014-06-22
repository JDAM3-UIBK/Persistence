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
import org.springframework.web.bind.annotation.ResponseBody;
import flexjson.JSONDeserializer;
import at.compare.exception.UserNotFound;
import at.compare.model.User;
import at.compare.service.UserService;
/**
 * Serveraddress:8080/usermanagement
 * @author Joachim Rangger
 * 
 */

@Controller
@RequestMapping(value="/usermanagement")
public class UserManagement {
	
	/**
	 * User Service - manages User to and from Database
	 * @see at.compare.service.UserService
	 */
	@Autowired
	UserService userService;
	
	/**
	 * Json HttpHeader
	 * @see at.compare.init.WebAppConfig#headers()
	 */	
	@Autowired
	HttpHeaders headers;
	
	/**
	 * Only for Testing Functionality
	 * Serveraddress:8080/usermanagement/jsonTest/{id}
	 * @param id of User
	 * @return ResponseEntity(user as JsonString or String, HttpHeader=Json, HttpStatus)
	 */
	 @RequestMapping(value = "/jsonTest/{id}", headers = "Accept=application/json")
	
	 public  @ResponseBody ResponseEntity<String> showJson(@PathVariable("id") String id) {
		 
	     User user = userService.findByNameId(id);
	   
	     if (user == null) {
	         return new ResponseEntity<String>("User not Found!", headers, HttpStatus.BAD_REQUEST);
	     }
	     
	     return new ResponseEntity<String>(user.toJson(), headers, HttpStatus.OK);
	 }
	 /**
	  * Functionality: Register User = Save User to Database
	  * Serveraddress:8080/usermanagement/Register
	  * 
	  * @param userJson - JsonString request from Client - RequestMethod = POST
	  * @return ResponseEntity(user as JsonString or String, HttpHeader=Json, HttpStatus)
	  */
	 @RequestMapping(value="/Register", method = RequestMethod.POST)
	 public @ResponseBody ResponseEntity<String> registerUser(@RequestBody String userJson){
		  
		 User userClient = new JSONDeserializer<User>().deserialize(userJson, User.class );
		 
		 //Check, if User already exists
		 User userTmp = userService.findByNameId(userClient.getUser_name_id());
		 
		 if(userTmp != null){
			 return new ResponseEntity<String>(" User is already Registered! " ,headers, HttpStatus.NOT_ACCEPTABLE);
		 }
		 
		 User userDB = userService.insert(userClient);
		 if(userDB != null){
			 return new ResponseEntity<String>(userDB.toJson(),headers,HttpStatus.OK);
		 }	 	 
		 return new ResponseEntity<String>("Error: Creating User!",headers,HttpStatus.BAD_REQUEST);
		 	 
		 
	 }
	 /**
	  * Functionality: Login User = Search User in Database and Compare 
	  * Serveraddress:8080/usermanagement/Anmelden
	  * 
	  * @param userJson - JsonString request from Client - RequestMethod = POST
	  * @return ResponseEntity(user as JsonString or String, HttpHeader=Json, HttpStatus)
	  */
	 
	 @RequestMapping(value="/Anmelden" ,method = RequestMethod.POST)
	 public @ResponseBody ResponseEntity<String> anmeldenUser(@RequestBody String userJson){						
	     
		 User userClient = new JSONDeserializer<User>().deserialize(userJson, User.class );
		 
		 //search for User, if it exists
		 User userDB = userService.findByNameId(userClient.getUser_name_id());
		 
		 if(userDB == null){
			 return new ResponseEntity<String>(" User Not Found! " ,headers, HttpStatus.BAD_REQUEST);
		 }
		 
		 if(userClient.getUser_pw().compareTo(userDB.getUser_pw()) == 0){
			 return new ResponseEntity<String>(userDB.toJson(),headers,HttpStatus.OK);
		 }
		
		 return new ResponseEntity<String>(" Password or User wrong!! ", headers, HttpStatus.BAD_REQUEST);    
		 
		 
	 }
	 
	 /**
	  * Functionality: Delete User = Delete User in Database
	  * Serveraddress:8080/usermanagement/Delete
	  * 
	  * @param userJson - JsonString request from Client - RequestMethod = POST
	  * @return ResponseEntity(user as JsonString or String, HttpHeader=Json, HttpStatus)
	  * @throws UserNotFound if User not found in Database @see at.compare.service.UserServiceImpl#delete(String)
	  */
	 
	 @RequestMapping(value="/Delete", method = RequestMethod.POST)
	 public @ResponseBody ResponseEntity<String> deleteUser(@RequestBody String userJson) throws UserNotFound{
		 
		 User userClient = new JSONDeserializer<User>().deserialize(userJson, User.class );
		 
		//search for User, if it exists
		 User userTmp = userService.findByNameId(userClient.getUser_name_id());
		 
		 if(userTmp == null){
			 return new ResponseEntity<String>("User not Found",headers, HttpStatus.NOT_FOUND);
		 }
		 User userDB = userService.delete(userClient.getUser_name_id());
		 if(userDB != null){
			 return new ResponseEntity<String>(userDB.toJson(),headers,HttpStatus.OK);
		 }
		 return new ResponseEntity<String>("User not Deleted",headers, HttpStatus.NOT_FOUND);
		 
		 
	 }
	 /**
	  * Functionality: Change User Password = Update User Data in Database
	  * Serveraddress:8080/usermanagement/changePassword
	  * 
	  * @param userJson - JsonString request from Client - RequestMethod = POST
	  * @return ResponseEntity(user as JsonString or String, HttpHeader=Json, HttpStatus)
	  * @throws UserNotFound if User not found in Database @see at.compare.service.UserServiceImpl#update(User)
	  */
	 @RequestMapping(value="/changePassword", method = RequestMethod.POST)
	 public @ResponseBody ResponseEntity<String> changePassword(@RequestBody String userJson) throws UserNotFound{
		 
		 User userClient = new JSONDeserializer<User>().deserialize(userJson, User.class );
		 
		 User userDB = userService.update(userClient);
		
		 if(userDB != null){
			 return new ResponseEntity<String>(userDB.toJson(), headers, HttpStatus.OK);
		 }	
		 		
		 return new ResponseEntity<String>("User not found!", headers, HttpStatus.NOT_FOUND);	 
		 
	 }
	 
}
