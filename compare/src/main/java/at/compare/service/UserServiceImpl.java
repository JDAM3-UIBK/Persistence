package at.compare.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import at.compare.exception.UserNotFound;
import at.compare.model.User;
import at.compare.repository.UserRepository;

/**
*
* Spring Service Implementation 
* @see org.springframework.stereotype.Service
* 
* User Service Implementation 
* @author Joachim Rangger
*/

@Service
public class UserServiceImpl implements UserService{
	
	/**
	 * @see at.compare.repository.UserRepository
	 * @see javax.annotation.Resource
	 */
	
	@Resource
	UserRepository userRepository;
	
	/**
	 * @param user - save User to Database
	 * @return {@link at.compare.repository.UserRepository#save(User)}
	 */
	@Override
	@Transactional
	public User insert(User user) {
		User createdUser = user;
		return userRepository.save(createdUser);
		
	}
	
	/**
	 * @param user - look for User in Database with username
	 * @return {@link at.compare.repository.UserRepository#findOne(String)}
	 */
	@Override
	@Transactional
	public User findByNameId(String nameId) {
		// TODO Auto-generated method stub
		return userRepository.findOne(nameId);
	}
	
	
	/**
	 * find all Users in Database
	 * @return {@link at.compare.repository.UserRepository#findAll()}
	 */
	@Override
	@Transactional
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}
	
	/**
	 * @param user_name_id - delete User from Database, with given username
	 * @throws at.compare.exception.UserNotFound
	 * 
	 * {@link at.compare.repository.UserRepository#findOne(String)}
	 * 
	 * @return user 
	 */
	@Override
	@Transactional(rollbackFor=UserNotFound.class)
	public User delete(String user_name_id) throws UserNotFound {
		User user = userRepository.findOne(user_name_id);
		
		if(user == null){
			throw new UserNotFound();
		}
		userRepository.delete(user);
		
		return user;
	}
	
	/**
	 * @param updatedUser - update User in Database
	 * @throws at.compare.exception.UserNotFound
	 * 
	 * {@link at.compare.repository.UserRepository#findOne(String)}
	 * 
	 * @return {@link at.compare.repository.UserRepository#save(User)}
	 */
	@Override
	@Transactional(rollbackFor=UserNotFound.class)
	public User update(User user) throws UserNotFound {
		User updatedUser = userRepository.findOne(user.getUser_name_id());
		if(updatedUser == null){
			throw new UserNotFound();
		}
		updatedUser.setUser_name_id(user.getUser_name_id());
		updatedUser.setUser_pw(user.getUser_pw());
		
		return userRepository.save(updatedUser);
	}

}
