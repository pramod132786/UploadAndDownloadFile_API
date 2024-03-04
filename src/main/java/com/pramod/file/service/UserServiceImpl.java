package com.pramod.file.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pramod.file.entity.LoginForm;
import com.pramod.file.entity.UserEntity;
import com.pramod.file.exception.InvalidInputFields;
import com.pramod.file.exception.UserNotFoundException;
import com.pramod.file.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public UserEntity createUser(UserEntity user) {
		if (user != null) {
			return userRepo.save(user);
		}
		return null;
	}

	@Override
	public  UserEntity login(LoginForm login) {
		if (login.getEmail() != null||login.getEmail().equals("") && login.getPazz() != null||login.getPazz().equals("")){
			Optional<UserEntity> findByEmailAndPazz = userRepo.findByEmailAndPazz(login.getEmail(), login.getPazz());
			if (findByEmailAndPazz.isEmpty()) {
				throw new UserNotFoundException("User Not Found With email "+login.getEmail());
			}
			return findByEmailAndPazz.get();

		}
		throw new InvalidInputFields("Try To pass Valid input.");
	}

	@Override
	public UserEntity getByUserId(Long userId) {
		if(userId!=null) {
			Optional<UserEntity> findById = userRepo.findById(userId);
			if(findById.isEmpty()) {
				throw new UserNotFoundException("User Not Found With Id "+userId);
			}
			return findById.get();
		}
	     throw new InvalidInputFields("Try To pass any input for this method like User Id.");
	}

}
