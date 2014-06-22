package at.compare.controller;

import java.io.IOException;


import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import at.compare.repository.RouteRepository;
import at.compare.service.AutoService;
import at.compare.service.UserService;

/**
 * @author Joachim Rangger
 * The Class is responsible for showing start home page
 * and testing functionality
*/
@Controller
public class LinkController {
	/**
	 * @see at.compare.service.AutoService
	 */
	@Autowired
	AutoService autoService;
	/**
	 * @see at.compare.service.UserService
	 */
	@Autowired
	UserService userService;
	
	/**
	 * @see at.compare.service.RouteRepository
	 */
	@Autowired
	RouteRepository routeRepository;
	
	/**
	 * @see at.compare.init.WebAppConfig
	 * @param response jsp file
	 * @throws IOException wrong input
	 * @return ModelAndView
	 */
	@RequestMapping(value="/")
	public ModelAndView test(HttpServletResponse response) throws IOException{
		return new ModelAndView("home");
	}
	/*
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
	*/
	/*
	// Bsp: http://localhost:8080/cp414/autoid?id=2 
	@RequestMapping(value="/autoid", method=RequestMethod.GET)
	public @ResponseBody String id(@RequestParam(value="id", required=false, defaultValue="1" )long id){
		
		Auto auto = autoService.findById(id);
		
		return auto.toString();
	}
	*/
	
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
