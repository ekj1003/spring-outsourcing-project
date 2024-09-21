package com.sparta.outsourcing_project.domain.store.repository;

import com.sparta.outsourcing_project.domain.store.entity.FavoriteStore;
import com.sparta.outsourcing_project.domain.store.entity.Store;
import com.sparta.outsourcing_project.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteStoreRepository extends JpaRepository<FavoriteStore, Long> {
    boolean existsByUserAndStore(User user, Store store);
}
