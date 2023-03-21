package com.myblogapp.service;

import java.util.List;

import com.myblogapp.payload.PostDto;
import com.myblogapp.payload.PostResponse;

public interface PostService {

	//create 
	
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	//Update
	PostDto updatePost(PostDto postDto,Integer postId);
	//Delete
	void deletePost(Integer postId);
	//get
	PostDto getPost(Integer postId);
	//get All
	PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);
	
	//get post by category
	List<PostDto> getAllPostByCategory(Integer categoryId);
	//get all post by user
	List<PostDto> getAllPostByUser(Integer userId);
	
	//search posts
	List<PostDto> searchPosts(String keyword);
}
