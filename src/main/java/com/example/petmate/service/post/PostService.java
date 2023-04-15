package com.example.petmate.service.post;

import com.example.petmate.entity.Post;
import com.example.petmate.model.request.PostRequest;
import com.example.petmate.model.response.PostResponse;

import java.util.List;

public interface PostService {
	List<Post> getAllPosts();

	PostResponse createPost(PostRequest request);

	boolean updatePost(String postId, PostRequest request);

	Post getPostById(String postId);

	boolean deletePost(String postId);
}
