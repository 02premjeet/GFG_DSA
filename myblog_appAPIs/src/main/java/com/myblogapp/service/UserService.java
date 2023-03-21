package com.myblogapp.service;

import java.util.List;

import com.myblogapp.payload.UserDto;

public interface UserService {
	
	
	UserDto registerNewUser (UserDto user);
	
	UserDto createUser(UserDto user);
	
	UserDto updateUser(UserDto user, Integer userId);
	
	UserDto getUserById(Integer userId);
	
	List<UserDto> getAllUsers();
	
	void deleteUser(Integer userId);
}
