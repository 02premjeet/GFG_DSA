package com.myblogapp.controller;

import java.util.List;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myblogapp.payload.ApisResponce;
import com.myblogapp.payload.UserDto;
import com.myblogapp.service.UserService;

@RestController
@ RequestMapping("/api/users")
public class UserController {

	@Autowired
	UserService userService;
	
	//Post -Create User
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser( @ Valid @RequestBody UserDto userDto){
		UserDto createUserDto = userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED); 
		
	}
	//PUT- Update user
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") Integer uid){
		UserDto updateUserDto = userService.updateUser(userDto, uid);
		
		return ResponseEntity.ok(updateUserDto);
		
	}
	//Delete- Delete User
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApisResponce> deleteUser(@PathVariable Integer userId){
		userService.deleteUser(userId);
		return new ResponseEntity<ApisResponce>(new ApisResponce("user deleted ", true),HttpStatus.OK);
		
	}
	//GET- get ALL users
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		List<UserDto> allusersDto = userService.getAllUsers();
		return ResponseEntity.ok(allusersDto);	
	}
	//GET-for single user
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUsers(@PathVariable Integer userId){
	 UserDto userById = userService.getUserById(userId);
		return ResponseEntity.ok(userById);
	}
}
