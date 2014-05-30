package at.compare.repository;



import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import at.compare.model.LoggedRoute;

public interface RouteRepository extends JpaRepository<LoggedRoute, Long>{
	
	
	//creates
	//select r from LoggedRoute r where r.userName = ?1
	public List<LoggedRoute> findByUserName(String userName);
}
