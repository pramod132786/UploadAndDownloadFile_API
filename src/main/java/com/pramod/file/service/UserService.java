package com.pramod.file.service;

import com.pramod.file.entity.LoginForm;
import com.pramod.file.entity.UserEntity;

public interface UserService {
	
	public UserEntity createUser(UserEntity user);
	
	public UserEntity login(LoginForm login);
	
	public UserEntity getByUserId(Long userId);
	

}
