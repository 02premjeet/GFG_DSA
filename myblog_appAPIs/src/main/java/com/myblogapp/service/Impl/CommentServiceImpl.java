package com.myblogapp.service.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myblogapp.entites.Comment;
import com.myblogapp.entites.Post;
import com.myblogapp.exceptions.ResourceNotFoundException;
import com.myblogapp.payload.CommentDto;
import com.myblogapp.repository.CommentRepo;
import com.myblogapp.repository.PostRepo;
import com.myblogapp.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	PostRepo postRepo;
	
	
	@Autowired
	CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		
		Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));
		Comment comment = modelMapper.map(commentDto, Comment.class);
		
		comment.setPost(post);
		Comment savedComment= commentRepo.save(comment);
		return modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		
		Comment com = commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "commentId", commentId));
          commentRepo.delete(com);
	}

}
