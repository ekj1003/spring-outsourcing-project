package com.sparta.outsourcing_project.domain.search.repository;

import com.sparta.outsourcing_project.domain.search.entity.SearchKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SearchKeywordRepository extends JpaRepository<SearchKeyword, Long> {
    Optional<SearchKeyword> findByKeyword(String keyword);

    // 검색 횟수가 많은 상위 10개의 검색어 조회
    List<SearchKeyword> findTop10ByOrderByCountDesc();

    // 모든 검색어 초기화
    @Modifying
    @Query("UPDATE SearchKeyword sk SET sk.count = 0, sk.updatedAt = :now")
    void resetAllSearchCounts(@Param("now") LocalDateTime now);
}
