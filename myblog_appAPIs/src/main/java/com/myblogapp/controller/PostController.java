package com.myblogapp.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.myblogapp.config.AppConstants;
import com.myblogapp.payload.ApisResponce;
import com.myblogapp.payload.PostDto;
import com.myblogapp.payload.PostResponse;
import com.myblogapp.service.FileService;
import com.myblogapp.service.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;
	
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	//create
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(
			@RequestBody PostDto postDto,
	        @PathVariable Integer userId,
	        @PathVariable Integer categoryId)
	{
		PostDto  createPost=postService.createPost(postDto,userId,categoryId);
		
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
		
	}
	//Get by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>>getPostsByUser(@PathVariable Integer userId){
		List<PostDto> posts = postService.getAllPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK) ;
		
	}
	//get by category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>>getPostsByCategory(@PathVariable Integer categoryId){
		List<PostDto> posts = postService.getAllPostByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK) ;
		
}
	//get all post
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllpost
	(
			@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)
			Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) 
			Integer pageSize,
			@RequestParam(value = "sortBy" ,defaultValue =AppConstants.SORT_BY,required = false) 
			String sortBy ,
			@RequestParam(value = "sortDir" ,defaultValue =AppConstants.SORT_DIR,required = false) 
			String sortDir 
			){
		PostResponse postResponse = postService.getAllPost(pageNumber, pageSize, sortBy,sortDir);
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK) ;
	}
	//get postId
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPost(@PathVariable Integer postId){
		PostDto postDto = postService.getPost(postId);
		return new ResponseEntity<PostDto>(postDto, HttpStatus.OK) ;
	}
	
	//delete 
	@DeleteMapping("/posts/{postId}")
	public ApisResponce  deletePost(@PathVariable Integer postId) {
		postService.deletePost(postId);
		return new ApisResponce("post is deleted successfully",true);
	}
	
	//update
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> upadatePost(@RequestBody PostDto postDto,@PathVariable Integer postId){
		PostDto updatePost = postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	
	//search
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords){
		List<PostDto> searchPosts = postService.searchPosts(keywords);
		return new ResponseEntity<List<PostDto>>(searchPosts,HttpStatus.OK);
		
	}
	//posts image  upload
	@PostMapping("/posts/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId
			) throws IOException{
		PostDto postDto = postService.getPost(postId);
		String fileName = fileService.uploadImage(path, image);
	
		postDto.setImageName(fileName);
		PostDto updatePost = postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	
	//Method to serve file
	@GetMapping(value = "/post/image/{imageNeme}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imageName") String imageName,HttpServletResponse response
			)  throws IOException{
		InputStream resource=fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
		
	}
	
}
