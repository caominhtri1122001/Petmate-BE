package com.example.petmate.entity.projector;

public interface PostProjector {
	String getId();
	String getPostId();
	String getTagId();
	String getTagName();
	String getPostTitle();
	String getPostImage();
	int getPostViews();
	int getPostLikes();
	int getPostComments();
	String getAuthor();
}
