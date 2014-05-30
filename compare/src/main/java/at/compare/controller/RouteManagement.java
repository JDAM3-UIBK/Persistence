package at.compare.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@RequestMapping(value="/id", method=RequestMethod.GET)
	public @ResponseBody LoggedRoute id(@RequestParam(value="id", required=false, defaultValue="1" )Long id) throws RouteNotFound{
		
		LoggedRoute route = routeService.findById(id);
		
		if((userService.findByNameId(route.getUserName())) == null){
			throw new RouteNotFound();
		}
		
		
		return route;
		
	}
	
	@RequestMapping(value="/username", method=RequestMethod.GET)
	public @ResponseBody LoggedRoute username(@RequestParam(value="username", required=false, defaultValue="tobi11" )String username) throws RouteNotFound{
		
		List<LoggedRoute> route = routeService.findByUsername(username);
		/*
		if((userService.findByNameId(route.getUserName())) == null){
			throw new RouteNotFound();
		}
		*/
		return route.get(0);
		
	}
	@RequestMapping(value="/saveRoute", method = RequestMethod.POST)
	public @ResponseBody LoggedRoute saveRoute(@RequestBody String routeJson){
		 LoggedRoute routeClient = new JSONDeserializer<LoggedRoute>().deserialize(routeJson, LoggedRoute.class ); 
		
		 LoggedRoute routeDB = routeService.insert(routeClient);
		 
		 return routeDB;
	}
	@RequestMapping(value="/showRoutePerUser", method = RequestMethod.POST)
	public @ResponseBody List<LoggedRoute> showRoutePerUser(@RequestBody String routeJson){
		 LoggedRoute routeClient = new JSONDeserializer<LoggedRoute>().deserialize(routeJson, LoggedRoute.class );
		 
		 List<LoggedRoute> routeDB = routeService.findByUsername(routeClient.getUserName());

		 return routeDB;
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
