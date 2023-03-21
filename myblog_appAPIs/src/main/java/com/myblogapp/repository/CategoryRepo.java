package com.myblogapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myblogapp.entites.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
