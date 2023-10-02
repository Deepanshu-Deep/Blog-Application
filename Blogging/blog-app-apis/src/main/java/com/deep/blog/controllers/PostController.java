package com.deep.blog.controllers;

import org.springframework.http.MediaType;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
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

import com.deep.blog.config.AppConstants;
import com.deep.blog.payloads.ApiResponse;
import com.deep.blog.payloads.PostDto;
import com.deep.blog.payloads.PostResponse;
import com.deep.blog.services.FileService;
import com.deep.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}") 
	private String path;
	
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(
			@Valid @RequestBody PostDto postDto,
			@PathVariable Integer userId,
			@PathVariable Integer categoryId){
		
		PostDto postDto2 = this.postService.createPost(postDto, userId, categoryId);
		
		return new ResponseEntity<>(postDto2, HttpStatus.CREATED);
		
	}
	
	
	@PutMapping("/{postId}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable Integer postId){
		
		PostDto updatePostDto = this.postService.updatePost(postDto, postId);
		
		return new ResponseEntity<>(updatePostDto, HttpStatus.OK);
		
	}
	
	
	
	@DeleteMapping("/{postId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer postId){
		
		this.postService.deletePost(postId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post deleted successfully", true), HttpStatus.OK);
		
	}
	
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(
				@PathVariable Integer userId){
		
		List<PostDto> post = this.postService.getPostByUser(userId);
	
		return ResponseEntity.ok(post);
		
	}
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
			
			) {

		PostResponse postResponse = this.postService.getAllPost(pageNumber, pageSize,sortBy, sortDir);
		
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}
	
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
		
		PostDto posts = this.postService.getPostById(postId);
		
		return new ResponseEntity<PostDto>(posts, HttpStatus.OK);
	}
	
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(
				@PathVariable Integer categoryId
			){
		
		List<PostDto> post = this.postService.getPostByCategory(categoryId);
	
		return ResponseEntity.ok(post);
		
	}
	
	//search
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keywords){
		
		List<PostDto> posts = this.postService.searchPost(keywords);
		
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}
	
	
	@PostMapping("/posts/images/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@RequestParam MultipartFile image,
			@PathVariable Integer postId) throws IOException{
		
		
		PostDto postDto	= this.postService.getPostById(postId);
		
		String fileName = this.fileService.uploadImage(path, image);
						
		postDto.setImageName(fileName);
		PostDto updatedPost =  this.postService.updatePost(postDto, postId);
		
		return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
		
	}
	
	
	@GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void uploadImage(
			@PathVariable String imageName, HttpServletResponse response) throws IOException{
		
		
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
		
	}
	
	
	
}
