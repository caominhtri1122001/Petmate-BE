package com.example.petmate.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;
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
	private int views;

	@Column(name = "comments")
	private int comments;

	@Column(name = "likes")
	private int likes;

	@Column(name = "author")
	private UUID userId;

	@ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JsonIgnoreProperties({ "id", "createdAt", "updatedAt", "password", "userImgUrl", "dateOfBirth" })
	@JoinColumn(name = "author", insertable = false, updatable = false)
	private User author;
}
