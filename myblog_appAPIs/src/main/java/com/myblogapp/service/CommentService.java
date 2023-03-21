
package com.myblogapp.service;

import com.myblogapp.payload.CommentDto;

public interface CommentService {

	
	CommentDto createComment(CommentDto commentDto, Integer postId);
	
	void deleteComment(Integer commentId);
}
