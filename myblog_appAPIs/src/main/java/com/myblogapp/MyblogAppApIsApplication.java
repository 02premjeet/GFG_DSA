 package com.myblogapp;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.myblogapp.config.AppConstants;
import com.myblogapp.entites.Role;
import com.myblogapp.repository.RoleRepository;

@SpringBootApplication
public class MyblogAppApIsApplication implements CommandLineRunner{

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
    private RoleRepository roleRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(MyblogAppApIsApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
		
	}
	@Override
	public void run(String... args) throws Exception {
		System.out.println(passwordEncoder.encode("shiv"));
		
		try {
			Role role=new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ROLE_ADMIN");
			
			Role role1=new Role();
			role1.setId(AppConstants.NORMAL_USER);
			role1.setName("ROLE_NORMAL");
			
			 List<Role> roles = Stream.of(role, role1)
				      .collect(Collectors.toList());
			  List<Role> result = roleRepo.saveAll(roles);
			  result.forEach(r->{
				  System.out.println(r.getName());
			  });
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}

}
