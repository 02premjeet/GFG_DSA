package com.myblogapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myblogapp.payload.ApisResponce;
import com.myblogapp.payload.CommentDto;
import com.myblogapp.service.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {
     
	@Autowired
	CommentService commentService;
	
	@PostMapping("/post/{postId}/comments")
	public  ResponseEntity<CommentDto>  createComment(@RequestBody CommentDto comment,@PathVariable("postId") Integer postId ) {
		
		CommentDto commentDto = commentService.createComment(comment, postId);
		return new ResponseEntity<CommentDto>(commentDto,HttpStatus.OK);
	}
	
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApisResponce> deleteComment(Integer commentId){
		
		commentService.deleteComment(commentId);
		return new ResponseEntity<ApisResponce>(new ApisResponce("comment deleted successfully!",true),HttpStatus.CREATED);
		
	}
	
}
