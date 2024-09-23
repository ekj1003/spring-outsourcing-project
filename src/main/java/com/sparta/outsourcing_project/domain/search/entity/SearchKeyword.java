package com.sparta.outsourcing_project.domain.search.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String keyword;

    @Column(nullable = false)
    private Integer count = 0;

    @Column(nullable = false)
    private LocalDateTime updatedAt; // 마지막 갱신 시간

    public SearchKeyword(String keyword) {
        this.keyword = keyword;
        this.count = 0;
        this.updatedAt = LocalDateTime.now();
    }

    public void incrementCount() {
        this.count++;
        this.updatedAt = LocalDateTime.now(); // 갱신 시점 업데이트
    }

    // 초기화 메서드
    public void resetCount() {
        this.count = 0;
        this.updatedAt = LocalDateTime.now();
    }

    // 1시간 이상 지났는지 확인하는 메서드
    public boolean isExpired() {
        return updatedAt.isBefore(LocalDateTime.now().minusHours(1));
    }
}
