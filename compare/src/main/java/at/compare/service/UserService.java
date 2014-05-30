package at.compare.service;

import java.util.List;

import at.compare.exception.UserNotFound;
import at.compare.model.User;

public interface UserService {
	public User insert(User user);
	public User delete(String user_name_id) throws UserNotFound;
	public User findByNameId(String id);
	public User update(User user) throws UserNotFound;
	public List<User> findAll();
}
