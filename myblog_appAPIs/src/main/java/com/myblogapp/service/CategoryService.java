package com.myblogapp.service;


import java.util.List;

import com.myblogapp.payload.CategoryDto;

public interface CategoryService {

	
	//Create 
	CategoryDto createCategory(CategoryDto categoryDto);
	//update
	CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
	//delete
	void deleteCategory(Integer categoryId);
	//get
	CategoryDto getCategory(Integer categoryId);
	//getAll
    List<CategoryDto> getAllCategory();
}
