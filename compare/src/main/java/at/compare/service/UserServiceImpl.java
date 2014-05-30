package at.compare.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import at.compare.exception.UserNotFound;
import at.compare.model.User;
import at.compare.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Resource
	UserRepository userRepository;
	
	@Override
	@Transactional
	public User insert(User user) {
		User createdUser = user;
		return userRepository.save(createdUser);
		
	}

	@Override
	@Transactional
	public User findByNameId(String nameId) {
		// TODO Auto-generated method stub
		return userRepository.findOne(nameId);
	}

	@Override
	@Transactional
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

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

	@Override
	@Transactional(rollbackFor=UserNotFound.class)
	public User update(User user) throws UserNotFound {
		User updatedUser = userRepository.findOne(user.getUser_name_id());
		if(updatedUser == null){
			throw new UserNotFound();
		}
		updatedUser.setUser_name_id(user.getUser_name_id());
		updatedUser.setUser_pw(user.getUser_pw());
		
		return updatedUser;
	}

}
