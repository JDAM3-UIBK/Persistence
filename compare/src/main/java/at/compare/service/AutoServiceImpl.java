package at.compare.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import at.compare.exception.UserNotFound;
import at.compare.model.Auto;
import at.compare.model.User;
import at.compare.repository.AutoRepository;

@Service
public class AutoServiceImpl implements AutoService{

	@Resource
	private AutoRepository autoRepository;
	
	@Override
	@Transactional
	public Auto insert(Auto auto) {
		Auto createdAuto = auto;
		return autoRepository.save(createdAuto);
	}

	@Override
	@Transactional
	public Auto findById(long id) {
		
		return autoRepository.findOne(id);
	}

	@Override
	@Transactional
	public List<Auto> findAll() {
		// TODO Auto-generated method stub
		return autoRepository.findAll();
	}
	

}
