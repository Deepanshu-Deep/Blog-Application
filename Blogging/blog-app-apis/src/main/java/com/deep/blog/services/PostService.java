package com.deep.blog.services;

import java.util.List;

import com.deep.blog.payloads.PostDto;
import com.deep.blog.payloads.PostResponse;

public interface PostService {

	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	public PostDto updatePost(PostDto postDto, Integer postId);
	
	public void deletePost(Integer postId);
	
	public PostDto getPostById(Integer postId);
	
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	public List<PostDto> getPostByCategory(Integer categoryId);
	
	public List<PostDto> getPostByUser(Integer userId);
	
	public List<PostDto> searchPost(String keyword);
	
	
	
}
