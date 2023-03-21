package com.myblogapp.service.Impl;

import java.util.Date;
import java.util.List;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.myblogapp.entites.Category;
import com.myblogapp.entites.Post;
import com.myblogapp.entites.User;
import com.myblogapp.exceptions.ResourceNotFoundException;
import com.myblogapp.payload.PostDto;
import com.myblogapp.payload.PostResponse;
import com.myblogapp.repository.CategoryRepo;
import com.myblogapp.repository.PostRepo;
import com.myblogapp.repository.UserRepo;
import com.myblogapp.service.PostService;
@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	   
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto,Integer userId, Integer categoryId) {
		User user=userRepo.findById(userId).orElseThrow(()->
		new ResourceNotFoundException("User","Id",userId));
		
		Category category=categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		Post post=modelMapper.map(postDto, Post.class);
		
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post newPost=postRepo.save(post);
		
		return modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		
		Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));
		
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post updatePost = postRepo.save(post);
	
		return modelMapper.map(updatePost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) { 
    
		Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));
	     postRepo.delete(post);
	}

	@Override
	public PostDto getPost(Integer postId) {
	 Post posts= postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));
		return modelMapper.map(posts, PostDto.class);
	}
//get all posts
	
	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		Sort sort=(sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
	
		Pageable p=PageRequest.of(pageNumber, pageSize, sort);
		
		
		 Page<Post> pagePost = postRepo.findAll(p);
		
		List<Post> allPosts = pagePost.getContent();
	
	List<PostDto> postDtos = allPosts.stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
	PostResponse postResponse=new PostResponse();
	 
	postResponse.setContent(postDtos);
	postResponse.setPageNumber(pagePost.getNumber());
	postResponse.setPageSize(pagePost.getSize());
	postResponse.setTotalElements( pagePost.getTotalElements());
    postResponse.setTotalPage(pagePost.getTotalPages());
    postResponse.setLastPage(pagePost.isLast());
	
	return postResponse;
	}

	@Override
	public List<PostDto> getAllPostByCategory(Integer categoryId) {
		Category cat=categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));
	 List<Post> posts = postRepo.findByCategory(cat);
	 List<PostDto> postDtos = posts.stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getAllPostByUser(Integer userId) {
		User user=userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));
	 List<Post> posts = postRepo.findByUser(user);
	 List<PostDto> postDtos = posts.stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts = postRepo.findByTitle("%"+keyword+"%");
		List<PostDto> postDtos = posts.stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return  postDtos;
	}

}
