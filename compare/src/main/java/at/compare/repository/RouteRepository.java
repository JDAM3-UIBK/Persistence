package at.compare.repository;



import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import at.compare.model.LoggedRoute;

/**
 * Spring Repository for saving Route Data 
 * @author Joachim Rangger
 * 
 */
public interface RouteRepository extends JpaRepository<LoggedRoute, Long>{
	
	

	/**
	 * 
	 * @param userName select all Routes with specified username = ?1
	 * select r from LoggedRoute r where r.userName = ?1
	 * @return List<LoggedRoute> of all found Routes
	 */
	public List<LoggedRoute> findByUserName(String userName);
}
