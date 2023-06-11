package com.example.petmate.service.post;

import com.example.petmate.dto.PostDto;
import com.example.petmate.entity.Post;
import com.example.petmate.model.request.PostRequest;
import com.example.petmate.model.response.PostResponse;

import java.io.IOException;
import java.util.List;

public interface PostService {
	List<PostDto> getAllPosts();

	PostResponse createPost(PostRequest request) throws IOException;

	boolean updatePost(String postId, PostRequest request) throws IOException;

	PostDto getPostById(String postId);

	boolean deletePost(String postId);
}
