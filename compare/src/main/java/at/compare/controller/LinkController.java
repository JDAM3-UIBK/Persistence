package at.compare.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import at.compare.model.Auto;
import at.compare.model.User;
import at.compare.repository.AutoRepository;
import at.compare.repository.RouteRepository;
import at.compare.service.AutoService;
import at.compare.service.AutoServiceImpl;
import at.compare.service.UserService;

@Controller
//@Configuration
//@ComponentScan("at.compare")
public class LinkController {
	
	@Autowired
	AutoService autoService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	RouteRepository routeRepository;
	
	@RequestMapping(value="/")
	public ModelAndView test(HttpServletResponse response) throws IOException{
		return new ModelAndView("home");
	}
	
	@RequestMapping(value="/test")
	public @ResponseBody String test(){
		//Auto auto = autoService.findById(1);
		
		return "test";
	}
	@RequestMapping(value="/test2")
	public @ResponseBody String test2(){
		Auto auto = autoService.findById(1);
		
		return auto.toString();
	}
	@RequestMapping(value="/test3")
	public @ResponseBody String test3(){
		User user = userService.findByNameId("tobi11");
		
		return "test" + user.toString();
	}
	@RequestMapping(value="/JsonTest1" ,produces="application/json")
	public @ResponseBody Auto JsonTest1(){
		Auto auto = autoService.findById(1);
		
		return auto;
	}
	//insert value
	
	
	// Bsp: http://localhost:8080/cp414/autoid?id=2 
	@RequestMapping(value="/autoid", method=RequestMethod.GET)
	public @ResponseBody String id(@RequestParam(value="id", required=false, defaultValue="1" )long id){
		
		Auto auto = autoService.findById(id);
		
		return auto.toString();
	}
	
	
	/*
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView shopListPage() {
		ModelAndView mav = new ModelAndView("auto-list");
		List<Auto> autoList = autoService.findAll();
		mav.addObject("autoList", autoList);
		return mav;
	}
	*/
}
