package com.deep.blog.services;

import com.deep.blog.payloads.CommentDto;

public interface CommentService {

	
	public CommentDto createComment(CommentDto commentDto, Integer postId);
	
	public void deleteComment(Integer commentId);
	
}
