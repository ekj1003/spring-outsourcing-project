package com.sparta.outsourcing_project.domain.store.repository;

import com.sparta.outsourcing_project.domain.store.entity.Store;
import com.sparta.outsourcing_project.domain.store.entity.StoreNotice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreNoticeRepository extends JpaRepository<StoreNotice, Long> {

    Optional<StoreNotice> findByIdAndStore(Long noticeId, Store store);
}
