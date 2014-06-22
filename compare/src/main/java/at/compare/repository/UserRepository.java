package at.compare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import at.compare.model.User;

/**
 * Spring Repository for saving User Data 
 * @author Joachim Rangger
 * 
 */
public interface UserRepository extends JpaRepository<User, String>{
	
	
}
