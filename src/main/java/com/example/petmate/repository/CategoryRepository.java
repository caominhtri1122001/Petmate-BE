package com.example.petmate.repository;

import com.example.petmate.entity.Category;
import com.example.petmate.entity.projector.CategoryProjector;
import com.example.petmate.entity.projector.PostProjector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

	@Query(nativeQuery = true, value = """
			 SELECT *
			 FROM categories ca
			 WHERE ca.post_id = :postId
			""")
	List<Category> findTagsOfPost(@Param("postId") UUID postId);

	@Query(nativeQuery = true, value = """
			 SELECT *
			 FROM categories ca
			 WHERE ca.tag_id = :	tagId
			""")
	List<Category> findPostsOfTag(@Param("tagId") UUID tagId);

	@Query(nativeQuery = true, value = """
			 SELECT cast(c.id AS varchar) as id,
			        cast(c.post_id AS varchar) as postId,
			        cast(c.tag_id AS varchar) as tagId,
			        t.name AS tagName,
			        p.title AS postTitle
			 FROM categories c
			 INNER JOIN tags t ON c.tag_id = t.id
			 INNER JOIN posts p ON c.post_id = p.id
			 WHERE p.id = :postId
			""")
	List<CategoryProjector> findAllTagsOfPost(@Param("postId") UUID postId);

	@Query(nativeQuery = true, value = """
			 SELECT cast(c.id AS varchar) as id,
			        cast(c.post_id AS varchar) as postId,
			        cast(c.tag_id AS varchar) as tagId,
			        t.name AS tagName,
			        p.title AS postTitle,
			        p.image AS postImage,
			        p.views AS postViews,
			        p.comments AS postComments,
			        p.likes AS postLikes,
			        u.email AS author
			 FROM categories c
			 INNER JOIN tags t ON c.tag_id = t.id
			 INNER JOIN posts p ON c.post_id = p.id
			 INNER JOIN users u ON p.author = u.id
			 WHERE c.tag_id = :tagId
			""")
	List<PostProjector> findAllPostsByTag(@Param("tagId") UUID tagId);

	List<Category> findByPostId(UUID postId);
}
