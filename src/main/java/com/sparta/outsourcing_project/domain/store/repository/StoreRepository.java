package com.sparta.outsourcing_project.domain.store.repository;

import com.sparta.outsourcing_project.domain.menu.enums.MenuType;
import com.sparta.outsourcing_project.domain.store.entity.Store;
import com.sparta.outsourcing_project.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {

    List<Store> findByNameAndIsDeletedFalse(String storeName);

    List<Store> findAllByUserId(Long id);

    Store findByIdAndUserId(Long storeId, Long userId);

    List<Store> findByUser(User user);

    // 가게 이름 또는 메뉴 이름으로 검색 (가게가 삭제되지 않은 경우)
    @Query("SELECT DISTINCT s FROM Store s JOIN s.menus m WHERE s.isDeleted = false AND (s.name LIKE %:keyword% OR m.name LIKE %:keyword%)")
    List<Store> findByStoreOrMenuNameContaining(@Param("keyword") String keyword);

    // 메뉴 타입을 기준으로 가게와 메뉴를 조회
    @Query("SELECT DISTINCT s FROM Store s JOIN FETCH s.menus m WHERE m.menuType = :type AND m.isDeleted = false")
    List<Store> findByMenuType(@Param("type") MenuType type);
}
