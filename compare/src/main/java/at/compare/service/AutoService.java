package at.compare.service;

import java.util.List;

import at.compare.model.Auto;

public interface AutoService {
	public Auto insert(Auto auto);
	//delete
	public Auto findById(long id);
	//update
	public List<Auto> findAll();
}
