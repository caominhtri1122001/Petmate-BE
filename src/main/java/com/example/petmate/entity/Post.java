package com.example.petmate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post extends BaseEntity {
	@Column(name = "title")
	private String title;

	@Column(name = "content")
	private String content;

	@Column(name = "image")
	private String image;

	@Column(name = "views")
	private Integer views;

	@Column(name = "comments")
	private Integer comments;

	@Column(name = "likes")
	private Integer likes;

	@Column(name = "author")
	private UUID author;
}
