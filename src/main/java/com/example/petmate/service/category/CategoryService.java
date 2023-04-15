package com.example.petmate.service.category;

import com.example.petmate.dto.CategoryDto;
import com.example.petmate.entity.Category;
import com.example.petmate.entity.projector.CategoryProjector;
import com.example.petmate.entity.projector.PostProjector;

import java.util.List;

public interface CategoryService {

	List<Category> getTagsOfPost(String postId);

	List<Category> getPostOfTags(String tagId);

	CategoryDto getAllTagsOfPost(String postId);

	List<PostProjector> getAllPostsByTag(String tagId);
}
