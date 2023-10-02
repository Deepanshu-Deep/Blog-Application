package com.deep.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryDto {

	
	private Integer categoryId;
	
	@NotBlank
	@Size(min = 4, message = "Minimum size of title is 4")
	private String categoryTitle;
	
	@NotBlank
	@Size(min = 10, message = "Minimum size of title is 10")
	private String categoryDescription;
	
	
	
}
