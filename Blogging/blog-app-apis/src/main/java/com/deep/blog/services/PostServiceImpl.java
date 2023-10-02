package com.deep.blog.services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.deep.blog.entities.Category;
import com.deep.blog.entities.Post;
import com.deep.blog.entities.User;
import com.deep.blog.exceptions.ResourceNotFoundException;
import com.deep.blog.payloads.PostDto;
import com.deep.blog.payloads.PostResponse;
import com.deep.blog.repositories.CategoryRepo;
import com.deep.blog.repositories.PostRepo;
import com.deep.blog.repositories.UserRepo;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "User id", userId));
		
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category id", categoryId));
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post newPost = this.postRepo.save(post);
		
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		
		Post posts = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Poost Id", postId));
		
		posts.setTitle(postDto.getTitle());
		posts.setContent(postDto.getContent());
		posts.setImageName(postDto.getImageName());
		
		Post post1 = this.postRepo.save(posts);
		
		return this.modelMapper.map(post1, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId));
		
		  this.postRepo.delete(post);
		
	}

	@Override
	public PostDto getPostById(Integer postId) {
		
		Post posts = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId));
		
		PostDto postDto = this.modelMapper.map(posts, PostDto.class);
		
		return postDto;
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		
		Sort sort = null;
		
		if(sortDir.equalsIgnoreCase("asc")) {
			
			sort = Sort.by(sortBy).ascending();
		}else {
			
			sort = Sort.by(sortBy).descending();
		}
		
		PageRequest p = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Post> posts = this.postRepo.findAll(p);
		
		List<Post> post2 = posts.getContent();
		
		List<PostDto> postDtos =  post2.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElements(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLastPage(posts.isLast());
		
		return postResponse;
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Category Id", categoryId));		
		List<Post> posts = this.postRepo.findByCategory(category);
		
		List<PostDto> postDtos = posts.stream().map((post-> this.modelMapper.map(post, PostDto.class))).collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
	
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "User Id", userId));		
		
		List<Post> posts = this.postRepo.findByUser(user);
		
		List<PostDto> postDtos = posts.stream().map((post-> this.modelMapper.map(post, PostDto.class))).collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		
		List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		
		List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

}
