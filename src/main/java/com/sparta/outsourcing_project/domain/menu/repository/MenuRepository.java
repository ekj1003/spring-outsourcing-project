package com.sparta.outsourcing_project.domain.menu.repository;

import com.sparta.outsourcing_project.domain.menu.entity.Menu;
import com.sparta.outsourcing_project.domain.menu.enums.MenuType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findAllByStoreIdAndIsDeletedFalse(Long storeId);

    Menu findByIdAndStoreId(Long id, Long menuId);

    // 특정 가게에서 삭제되지 않은 메뉴 중 타입별로 조회
    List<Menu> findByStoreIdAndMenuTypeAndIsDeletedFalse(Long storeId, MenuType menuType);
}
