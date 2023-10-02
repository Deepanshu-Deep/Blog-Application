package com.deep.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.deep.blog.entities.Category;
import com.deep.blog.entities.Post;
import com.deep.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer>, PagingAndSortingRepository<Post, Integer>{

	List<Post> findByUser(User user);
	
	List<Post> findByCategory(Category category);

	
	List<Post> findByTitleContaining(String title);
	
}
