package com.myblogapp.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.myblogapp.config.AppConstants;
import com.myblogapp.entites.Role;
import com.myblogapp.entites.User;
import com.myblogapp.exceptions.ResourceNotFoundException;
import com.myblogapp.payload.UserDto;
import com.myblogapp.repository.RoleRepository;
import com.myblogapp.repository.UserRepo;
import com.myblogapp.service.UserService;
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired 
	private RoleRepository roleRepo;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		
		User user=this.dtoToUser(userDto);
		 User savedUser =this.userRepo.save(user);
		
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		
		User user=userRepo.findById(userId).
				orElseThrow(()-> new ResourceNotFoundException("User","Id",userId)); 
		
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout() );
		
		User updateUser=userRepo.save(user);
		UserDto userDto1=userToDto(updateUser);
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user=userRepo.findById(userId).
				orElseThrow(()-> new ResourceNotFoundException("User","Id",userId)); 
		
		
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId)); 
		userRepo.delete(user);
	}
	//to change UserDto to User
	public User dtoToUser(UserDto userDto) {
		
		User user=modelMapper.map(userDto, User.class);
//		
//		user.setId(userDto.getId());
//		user.setEmail(userDto.getEmail());
//		user.setName(userDto.getName());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout() );
		
		
		return user;
		
	}
	public UserDto userToDto(User user) {
		UserDto userDto=modelMapper.map(user, UserDto.class);
//		
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
		
		return userDto;
		
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user = modelMapper.map(userDto, User.class);
		 
		//Encoded password
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		//roles
		Role role = roleRepo.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		User newUser = userRepo.save(user);
		 return modelMapper.map(newUser,UserDto.class);
	}

}
