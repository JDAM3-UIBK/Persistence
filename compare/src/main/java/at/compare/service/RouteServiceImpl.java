package at.compare.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import at.compare.exception.RouteNotFound;
import at.compare.model.LoggedRoute;
import at.compare.repository.RouteRepository;

@Service
public class RouteServiceImpl implements RouteService{

	@Resource
	RouteRepository routeRepository;
	
	@Override
	@Transactional
	public LoggedRoute insert(LoggedRoute route) {
		// TODO Auto-generated method stub
		LoggedRoute createdRoute = route;
		return routeRepository.save(createdRoute);
	}

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

	@Override
	@Transactional
	public LoggedRoute findById(Long route_id) {
		
		return routeRepository.findOne(route_id);
	}

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
		
		
		return updatedRoute;
	}

	@Override
	@Transactional
	public List<LoggedRoute> findAll() {
	
		return routeRepository.findAll();
	}

	@Override
	@Transactional
	public List<LoggedRoute> findByUsername(String username) {
		// TODO Auto-generated method stub
		
		return routeRepository.findByUserName(username);
	}

	
}
