package com.sparta.outsourcing_project.domain.store.entity;

import com.sparta.outsourcing_project.domain.store.dto.request.StoreNoticeRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreNotice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String notice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    public StoreNotice(Store store, String notice) {
        this.notice = notice;
        this.store = store;
    }
}
