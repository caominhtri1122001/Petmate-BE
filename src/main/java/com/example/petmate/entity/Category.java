package com.example.petmate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category extends BaseEntity {
	@Column(name = "post_id")
	private UUID postId;

	@Column(name = "tag_id")
	private UUID tagId;

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "post_id", nullable = false, updatable = false, insertable = false)
//	private Post post;
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "tag_id", nullable = false, updatable = false, insertable = false)
//	private Tag tag;
}
