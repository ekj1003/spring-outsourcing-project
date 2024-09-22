package com.sparta.outsourcing_project.domain.review.repository;

import com.sparta.outsourcing_project.domain.review.entity.ReviewOwnerComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewOwnerCommentRepository extends JpaRepository<ReviewOwnerComment, Long> {
}
