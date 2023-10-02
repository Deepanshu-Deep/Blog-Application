package com.deep.blog.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.deep.blog.payloads.CategoryDto;

@Service
public interface CategoryService {

	
	public CategoryDto createCategory(CategoryDto categoryDto);
	
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
	
	public void deleteCategory(Integer categoryId);
	
	public CategoryDto getCategory(Integer categoryId);
	
	public List<CategoryDto> getCategories();
	
	
}
