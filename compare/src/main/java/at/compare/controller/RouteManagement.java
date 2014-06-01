package at.compare.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import flexjson.JSONDeserializer;
import at.compare.exception.RouteNotFound;
import at.compare.exception.UserNotFound;
import at.compare.model.LoggedRoute;
import at.compare.model.User;
import at.compare.service.RouteService;
import at.compare.service.UserService;

@Controller
@RequestMapping(value="/routemanagement")
public class RouteManagement {
	
	@Autowired
	RouteService routeService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/findRouteWithId")
	public @ResponseBody ResponseEntity<String> id(@RequestParam(value="id", required=false, defaultValue="1" )Long id) throws RouteNotFound{
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Type", "application/json; charset=utf-8");
		
		LoggedRoute route = routeService.findById(id);
		
		if((userService.findByNameId(route.getUserName())) == null){
			return new ResponseEntity<String>(" User not Registered! ", headers, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<String>(route.toJson(), headers, HttpStatus.OK);
	}
	
	@RequestMapping(value="/username", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> username(@RequestBody String username) throws RouteNotFound{
		
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Type", "application/json; charset=utf-8");
		
		List<LoggedRoute> routeDBList = routeService.findByUsername(username);
		
		if((userService.findByNameId(username)) == null){
			return new ResponseEntity<String>(" User not Registered! ", headers, HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>(LoggedRoute.toJsonArray(routeDBList), headers, HttpStatus.OK);
		
	}
	@RequestMapping(value="/saveRoute", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> saveRoute(@RequestBody String routeJson){
		 HttpHeaders headers = new HttpHeaders();
	     headers.add("Content-Type", "application/json; charset=utf-8");
		
		 LoggedRoute routeClient = new JSONDeserializer<LoggedRoute>().deserialize(routeJson, LoggedRoute.class ); 
		
		 LoggedRoute routeDB = routeService.insert(routeClient);
		 if(routeDB == null){
			 return new ResponseEntity<String>(" Route not Saved! ", headers, HttpStatus.BAD_REQUEST);
		 }
		 
		 return new ResponseEntity<String>(routeDB.toJson(), headers, HttpStatus.OK);
	}
	@RequestMapping(value="/showRoutePerUser", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> showRoutePerUser(@RequestBody String routeJson){ 
		
		 HttpHeaders headers = new HttpHeaders();
	     headers.add("Content-Type", "application/json; charset=utf-8");
	     
		 LoggedRoute routeClient = new JSONDeserializer<LoggedRoute>().deserialize(routeJson, LoggedRoute.class );
		 
		 List<LoggedRoute> routeDBList = routeService.findByUsername(routeClient.getUserName());
		 
		 if(routeDBList == null){
			 return new ResponseEntity<String>(" Route not found! ", headers, HttpStatus.NOT_FOUND);
		 }
		 
		 
		 return new ResponseEntity<String>(LoggedRoute.toJsonArray(routeDBList), headers, HttpStatus.OK);
		 
	}
	@RequestMapping(value="/deleteRouteById", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> deleteRouteById(@RequestBody String routeJson){ 
		
		 HttpHeaders headers = new HttpHeaders();
	     headers.add("Content-Type", "application/json; charset=utf-8");
	     /*
		 LoggedRoute routeClient = new JSONDeserializer<LoggedRoute>().deserialize(routeJson, LoggedRoute.class );
		 
		 LoggedRoute routeDB = null;
		try {
			routeDB = routeService.delete(routeClient.getId());
		} catch (RouteNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 if(routeDB == null){
			 return new ResponseEntity<String>(" Route not found! ", headers, HttpStatus.NOT_FOUND);
		 }
		 
		 return new ResponseEntity<String>(routeDB.toJson(), headers, HttpStatus.OK);
		 */
	     return new ResponseEntity<String>(" NOT IMPLEMENTED!! ", headers, HttpStatus.NOT_FOUND);
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
