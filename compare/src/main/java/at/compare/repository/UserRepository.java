package at.compare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import at.compare.model.User;

public interface UserRepository extends JpaRepository<User, String>{
	
	
}
