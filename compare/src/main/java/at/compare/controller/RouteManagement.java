package at.compare.controller;


import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import flexjson.JSONDeserializer;
import at.compare.exception.RouteNotFound;
import at.compare.model.LoggedRoute;
import at.compare.model.LoggedRouteValidator;
import at.compare.service.RouteService;
import at.compare.service.UserService;

/**
 * Serveraddress:8080/routemanagement
 * @author Joachim Rangger
 * 
 */

@Controller
@RequestMapping(value="/routemanagement")
public class RouteManagement {
	
	/**
	 * Route Service - manages Route to and from Database
	 * @see at.compare.service.RouteService
	 */
	@Autowired
	RouteService routeService;
	
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
	  * Functionality: find Route = Find Route in Database with id
	  * Serveraddress:8080/routemanagement/findRouteWithId
	  * 
	  * @param id - Request from Client with value="id"- RequestMethod = POST/GET
	  * @return ResponseEntity(user as JsonString or String, HttpHeader=Json, HttpStatus)
	  */
	@RequestMapping(value="/findRouteWithId")
	public @ResponseBody ResponseEntity<String> id(@RequestParam(value="id", required=false, defaultValue="1" )Long id){
		
		LoggedRoute loggedRoute = routeService.findById(id);
		
		if(loggedRoute == null){
			return new ResponseEntity<String>(" User not Found! ", headers, HttpStatus.NOT_FOUND);
		}
		
		if((userService.findByNameId(loggedRoute.getUserName())) == null){
			return new ResponseEntity<String>(" User not Registered! ", headers, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<String>(loggedRoute.toJson(), headers, HttpStatus.OK);
	}
	/**
	  * Functionality: find Route = Find Route in Database with username
	  * Serveraddress:8080/routemanagement/username
	  * 
	  * @param username - String request from Client - RequestMethod = POST
	  * @return ResponseEntity(user as JsonString or String, HttpHeader=Json, HttpStatus)
	  */
	@RequestMapping(value="/username", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> username(@RequestBody String username){
		
		List<LoggedRoute> routeDBList = routeService.findByUsername(username);
		
		if((userService.findByNameId(username)) == null){
			return new ResponseEntity<String>(" User not Registered! ", headers, HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>(LoggedRoute.toJsonArray(routeDBList), headers, HttpStatus.OK);
		
	}
	/**
	  * Functionality: save Route = save Route to Database 
	  * Serveraddress:8080/routemanagement/saveRoute
	  * 
	  * @param routeJson - JsonString request from Client - RequestMethod = POST
	  * @param routeClient  LoggedRouteValidator  @see at.compare.model.LoggedRouteValidator  
	  *
	  * @param result for Validation Error handling 
	  * 
	  * @return ResponseEntity(user as JsonString or String, HttpHeader=Json, HttpStatus)
	  * 
	  */
	
	@RequestMapping(value="/saveRoute", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> saveRoute( @RequestBody String routeJson, 
																@Valid LoggedRoute routeClient, BindingResult result ){
		
		 routeClient = new JSONDeserializer<LoggedRoute>().deserialize(routeJson, LoggedRoute.class ); 
		 new LoggedRouteValidator().validate(routeClient, result);
		 if(result.hasErrors()){
			 return new ResponseEntity<String>("Username too short! ", headers, HttpStatus.NOT_ACCEPTABLE);
		 }
		 if(userService.findByNameId(routeClient.getUserName())== null){
			 return new ResponseEntity<String>("Wrong Username! ", headers, HttpStatus.BAD_REQUEST);
		 }
		 
		 LoggedRoute routeDB = routeService.insert(routeClient);
		 if(routeDB != null){
			 return new ResponseEntity<String>(routeDB.toJson(), headers, HttpStatus.OK);
		 }
		 return new ResponseEntity<String>("Route not Saved! ", headers, HttpStatus.BAD_REQUEST);
		 
	}
	/**
	  * Functionality: show Routes of specified User = Find Routes of specified User in Database
	  * Serveraddress:8080/routemanagement/showRoutePerUser
	  * 
	  * @param routeJson - JsonString request from Client - RequestMethod = POST
	  * @return ResponseEntity(user as JsonString or String, HttpHeader=Json, HttpStatus)
	  */
	
	@RequestMapping(value="/showRoutePerUser", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> showRoutePerUser(@RequestBody String routeJson){ 
		
		 LoggedRoute routeClient = new JSONDeserializer<LoggedRoute>().deserialize(routeJson, LoggedRoute.class );
		 
		 List<LoggedRoute> routeDBList = routeService.findByUsername(routeClient.getUserName());
		 
		 if(routeDBList.isEmpty()){
			 return new ResponseEntity<String>(" Routes not found! ", headers, HttpStatus.NOT_FOUND);
		 }
		 
		 
		 return new ResponseEntity<String>(LoggedRoute.toJsonArray(routeDBList), headers, HttpStatus.OK);
		 
	}
	/**
	  * Functionality: delete Route = Delete Route in Database
	  * Serveraddress:8080/routemanagement/deleteRoute
	  * 
	  * @param routeJson - JsonString request from Client - RequestMethod = POST
	  * @throws RouteNotFound if specified Route not found in Database @see @see at.compare.service.RouteServiceImpl#delete(Long)
	  * @return ResponseEntity(user as JsonString or String, HttpHeader=Json, HttpStatus)
	  */
	@RequestMapping(value="/deleteRoute", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> deleteRoute(@RequestBody String routeJson) throws RouteNotFound{ 
		 
		 LoggedRoute routeClient = new JSONDeserializer<LoggedRoute>().deserialize(routeJson, LoggedRoute.class );
		 
		 List<LoggedRoute> routeTmpList =  routeService.findByUsername(routeClient.getUserName()); 
		 
		 if(routeTmpList.isEmpty()){
				return new ResponseEntity<String>(" Route not found!* ", headers, HttpStatus.NOT_FOUND);
		 }
		 
		LoggedRoute route = null; 
		for(LoggedRoute tmp: routeTmpList){
			if((tmp.getDate().compareTo(routeClient.getDate()))==0){
				route = tmp;
			}
		}
		if(route == null){
			return new ResponseEntity<String>(" Route not found!** ", headers, HttpStatus.NOT_FOUND);
		}
		 
		LoggedRoute routeDB = routeService.delete(route.getId());
		
		if(routeDB != null){
			 return new ResponseEntity<String>(routeDB.toJson(), headers, HttpStatus.OK);
		}
		return new ResponseEntity<String>(" Route not found!*** " + routeClient.getDate() + route.getDate(), headers, HttpStatus.NOT_FOUND);
		 
		
	}
	/**
	 * Functionality: delete Routes = Delete Routes in Database with specified Username
	 * Serveraddress:8080/routemanagement/deleteRoutesWithUsername
	 * @param routeJson - JsonString request from Client - RequestMethod = POST
	 * @return ResponseEntity(user as JsonString or String, HttpHeader=Json, HttpStatus)
	 */
	@RequestMapping(value="/deleteRoutesWithUsername", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> deleteRoutesWithUsername(@RequestBody String routeJson){ 
	     
		 LoggedRoute routeClient = new JSONDeserializer<LoggedRoute>().deserialize(routeJson, LoggedRoute.class );
		 
		 if(routeClient == null){
			 return new ResponseEntity<String>(" Route Object not Valid! ", headers, HttpStatus.BAD_REQUEST);
		 }
		 
		 List<LoggedRoute> routeTmpList =  routeService.findByUsername(routeClient.getUserName()); 
		 if(routeTmpList.isEmpty()){
				return new ResponseEntity<String>("Route not found! ", headers, HttpStatus.NOT_FOUND);
		 }
		 LoggedRoute result = null;
		 for(LoggedRoute tmp: routeTmpList){
			
			try {
				result = routeService.delete(tmp.getId());
			} catch (RouteNotFound e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
		 }
		 if(result != null){
			 return new ResponseEntity<String>("Deleted Routes of User: " + routeClient.getUserName(), headers, HttpStatus.OK);
		 }
		 return new ResponseEntity<String>("No User Deleted!", headers, HttpStatus.NOT_FOUND);
	}
	/*
	@RequestMapping(value="/showCO2", method = RequestMethod.POST)
	public @ResponseBody List<LoggedRoute> showCO2(@RequestBody String routeJson){
		 LoggedRoute routeClient = new JSONDeserializer<LoggedRoute>().deserialize(routeJson, LoggedRoute.class );
		 
		 List<LoggedRoute> routeDB = routeService.findByUsername(routeClient.getUserName());

		 return routeDB;
	} */
	/*
	@RequestMapping(value="/showCost", method = RequestMethod.POST)
	public @ResponseBody List<LoggedRoute> showCost(@RequestBody String routeJson){
		 LoggedRoute routeClient = new JSONDeserializer<LoggedRoute>().deserialize(routeJson, LoggedRoute.class );
		 
		 List<LoggedRoute> routeDB = routeService.findByUsername(routeClient.getUserName());

		 return routeDB;
	} */
}
