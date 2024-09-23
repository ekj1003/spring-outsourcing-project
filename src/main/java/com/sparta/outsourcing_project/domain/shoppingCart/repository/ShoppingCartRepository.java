package com.sparta.outsourcing_project.domain.shoppingCart.repository;

import com.sparta.outsourcing_project.domain.shoppingCart.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    void deleteAllByUserId(Long userId);

    List<ShoppingCart> findAllByUserId(Long id);

    ShoppingCart findByUserIdAndMenuId(Long userId, Long menuId);

    ShoppingCart findByUserId(Long userId);
}
