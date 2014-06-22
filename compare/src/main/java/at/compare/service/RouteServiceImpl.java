package at.compare.service;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import at.compare.exception.RouteNotFound;
import at.compare.model.LoggedRoute;
import at.compare.repository.RouteRepository;

/**
 *
 * Spring Service Implementation 
 * @see org.springframework.stereotype.Service
 * 
 * Route Service Implementation
 * @author Joachim Rangger
 */

@Service
public class RouteServiceImpl implements RouteService{

	/**
	 * @see at.compare.repository.RouteRepository
	 * @see javax.annotation.Resource
	 */
	@Resource
	RouteRepository routeRepository;
	
	/**
	 * @param route - save LoggedRoute to Database
	 * @return at.compare.repository.RouteRepository#save(LoggedRoute)
	 */
	
	@Override
	@Transactional
	public LoggedRoute insert(LoggedRoute route) {
		// TODO Auto-generated method stub
		LoggedRoute createdRoute = route;
		return routeRepository.save(createdRoute);
	}
	/**
	 * @param route_id - delete LoggedRoute from Database, with given Route id
	 * @throws at.compare.exception.RouteNotFound Routeno
	 * @return at.compare.repository.RouteRepository#findOne(Long)
	 */
	
	@Override
	@Transactional(rollbackFor=RouteNotFound.class)
	public LoggedRoute delete(Long route_id) throws RouteNotFound {
			LoggedRoute deletedRoute = routeRepository.findOne(route_id);
			
			if(deletedRoute == null){
				throw new RouteNotFound();
			}
			routeRepository.delete(deletedRoute);
		return deletedRoute;
	}
	/**
	 * @param route_id id of Logged Route
	 * @return at.compare.repository.RouteRepository#findOne(Long)
	 */
	
	@Override
	@Transactional
	public LoggedRoute findById(Long route_id) {
		
		return routeRepository.findOne(route_id);
	}
	
	/**
	 * @param route - update LoggedRoute in Database
	 * @throws at.compare.exception.RouteNotFound if Route not found
	 * @return at.compare.repository.RouteRepository#findOne(Long)
	 */
	@Override
	@Transactional(rollbackFor=RouteNotFound.class)
	public LoggedRoute update(LoggedRoute route) throws RouteNotFound {
		
		LoggedRoute updatedRoute = routeRepository.findOne(route.getId());
		
		if(updatedRoute == null){
			throw new RouteNotFound();
		}
		updatedRoute.setCO2(route.getCO2());
		updatedRoute.setCosts(route.getCosts());
		updatedRoute.setDuration(route.getDuration());
		updatedRoute.setLength(route.getLength());
		updatedRoute.setReferenceco2(route.getReferenceco2());
		updatedRoute.setReferencecosts(route.getReferencecosts());
		updatedRoute.setReferencelength(route.getReferencelength());
		updatedRoute.setType(route.getType());
		
		
		return routeRepository.save(updatedRoute);
	}
	/**
	 * find all saved Routes in Database
	 * @return at.compare.repository.RouteRepository#findAll()
	 */
	@Override
	@Transactional
	public List<LoggedRoute> findAll() {
	
		return routeRepository.findAll();
	}
	
	/**
	 * @param username - look for Route with given username
	 * @return at.compare.repository.RouteRepository#findByUserName(String)
	 */
	
	@Override
	@Transactional
	public List<LoggedRoute> findByUsername(String username) {
		// TODO Auto-generated method stub
		
		return routeRepository.findByUserName(username);
	}

	
}
