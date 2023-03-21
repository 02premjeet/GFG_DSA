package com.myblogapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myblogapp.entites.Role;

public interface RoleRepository extends JpaRepository<Role,Integer> {

}
