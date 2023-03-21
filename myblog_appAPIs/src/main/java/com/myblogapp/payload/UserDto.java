package com.myblogapp.payload;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.Size;

import com.myblogapp.entites.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor

@Setter
@Getter
@AllArgsConstructor  
public class UserDto {
	
	
	private int id;
	@NotEmpty
	@Size(min=3,message = "Username must be minimun 3 character")
	private String name;
	@Email(message = "Email address not valid")
	private String email;
	@NotEmpty
	@Size(min = 3,max = 10,message = "password is not valid")
//	@Pattern(regexp = "A-Z")
	private String password;
	@NotEmpty
	private String about;
	
	private Set<RoleDto> roles=new HashSet<>();
}
