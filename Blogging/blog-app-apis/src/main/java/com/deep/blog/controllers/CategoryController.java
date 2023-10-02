package com.deep.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deep.blog.payloads.ApiResponse;
import com.deep.blog.payloads.CategoryDto;
import com.deep.blog.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		
		CategoryDto categoryDto2 = this.categoryService.createCategory(categoryDto);
		
		return new ResponseEntity<>(categoryDto2, HttpStatus.CREATED);
		
	}
	
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateUser(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId){
		
		CategoryDto updatecategoryDto = this.categoryService.updateCategory(categoryDto, categoryId);
		
		return new ResponseEntity<>(updatecategoryDto, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer categoryId){
		
		this.categoryService.deleteCategory(categoryId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted successfully", true), HttpStatus.OK);
		
	}
	
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllUsers(){
	
		return ResponseEntity.ok(this.categoryService.getCategories());
		
	}
	
	
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getSingleUser(@PathVariable Integer categoryId){
	
		return ResponseEntity.ok(this.categoryService.getCategory(categoryId));
		
	}
	
	
	
	
}
