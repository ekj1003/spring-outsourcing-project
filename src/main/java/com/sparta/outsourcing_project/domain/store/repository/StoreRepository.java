package com.sparta.outsourcing_project.domain.store.repository;

import com.sparta.outsourcing_project.domain.store.entity.Store;
import com.sparta.outsourcing_project.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {

    List<Store> findByNameAndIsDeletedFalse(String storeName);

    List<Store> findAllByUserId(Long id);

    Store findByIdAndUserId(Long storeId, Long userId);

    List<Store> findByUser(User user);
}
