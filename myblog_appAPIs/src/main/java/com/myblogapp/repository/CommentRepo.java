package com.myblogapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myblogapp.entites.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
