package at.compare.test.init;

import static org.junit.Assert.assertNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import at.compare.init.WebAppConfig;
import at.compare.model.User;
import at.compare.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
// loads "file:src/main/webapp for Instance web.xml in our Project
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class)
public class UserRepositoryTests {
	
	//Test Hibernate Functionality
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	User user;
	
	@Test
	public void insertUserTest(){
		User createdUser = userRepository.save(user);
		assertNotNull(createdUser);
	}
	
	@Test
	public void findByNameIdTest() {
        User userDB = userRepository.findOne("hans");
        assertNotNull(userDB);
    }
	
	

}
