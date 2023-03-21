package com.myblogapp.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myblogapp.entites.Category;
import com.myblogapp.exceptions.ResourceNotFoundException;
import com.myblogapp.payload.CategoryDto;
import com.myblogapp.repository.CategoryRepo;
import com.myblogapp.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		 
		Category cat = modelMapper.map(categoryDto, Category.class);
		Category addedcat = categoryRepo.save(cat);
		return modelMapper.map(addedcat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		 
		Category cat=categoryRepo.findById(categoryId).
				orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		Category updatedcat=categoryRepo.save(cat);
		return modelMapper.map(updatedcat, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		
		Category cat= categoryRepo.findById(categoryId).
				orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
		categoryRepo.delete(cat);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		 Category cat= categoryRepo.findById(categoryId)
				 .orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
		 
		return modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		 List<Category> categories = categoryRepo.findAll();
		 List<CategoryDto> getAllcat = categories.stream().map((cat)-> modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		return getAllcat;
	}

}
