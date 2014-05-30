package at.compare.service;

import java.util.List;
import at.compare.exception.RouteNotFound;
import at.compare.model.LoggedRoute;


public interface RouteService {
	
	public LoggedRoute insert(LoggedRoute route);
	public LoggedRoute delete(Long route_id) throws RouteNotFound;
	public LoggedRoute findById(Long route_id);
	public List<LoggedRoute> findByUsername(String username);
	public LoggedRoute update(LoggedRoute route) throws RouteNotFound;
	public List<LoggedRoute> findAll();
}
