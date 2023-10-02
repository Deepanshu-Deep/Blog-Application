package com.deep.blog.payloads;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {

	private List<PostDto> content;
	
	private Integer PageNumber;
	
	private Integer pageSize;
	
	private long totalElements;
	
	private Integer totalPages;
	
	private boolean lastPage;
	
}
