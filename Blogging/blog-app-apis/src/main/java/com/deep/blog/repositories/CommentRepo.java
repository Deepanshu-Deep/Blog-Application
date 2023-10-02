package com.deep.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deep.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
