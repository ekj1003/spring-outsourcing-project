package com.sparta.outsourcing_project.domain.store.service;

import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.domain.exception.CannotFindFavoriteStoreException;
import com.sparta.outsourcing_project.domain.exception.CannotFindStoreIdException;
import com.sparta.outsourcing_project.domain.exception.UnauthorizedAccessException;
import com.sparta.outsourcing_project.domain.menu.dto.response.MenuListResponseDto;
import com.sparta.outsourcing_project.domain.store.dto.response.FavoriteStoreResponseDto;
import com.sparta.outsourcing_project.domain.store.dto.response.OneStoreResponseDto;
import com.sparta.outsourcing_project.domain.store.dto.response.StoreResponseDto;
import com.sparta.outsourcing_project.domain.store.entity.FavoriteStore;
import com.sparta.outsourcing_project.domain.store.entity.Store;
import com.sparta.outsourcing_project.domain.store.repository.FavoriteStoreRepository;
import com.sparta.outsourcing_project.domain.store.repository.StoreRepository;
import com.sparta.outsourcing_project.domain.user.entity.User;
import com.sparta.outsourcing_project.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreCustomerService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final FavoriteStoreRepository favoriteStoreRepository;

    public List<StoreResponseDto> getStores(String storeName) {
        List<Store> stores = storeRepository.findByNameAndIsDeletedFalse(storeName);
        return stores.stream()
                .map(StoreResponseDto::new) // StoreResponseDto의 생성자를 통해 변환
                .collect(Collectors.toList());
    }

    public OneStoreResponseDto getStore(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(CannotFindStoreIdException::new);

        List<MenuListResponseDto> menuList = store.getMenus().stream()
                .filter(menu -> !menu.getIsDeleted())
                .map(menu -> new MenuListResponseDto(menu.getId(), menu.getName(), menu.getPrice(), menu.getDescription()))
                .collect(Collectors.toList());

        return new OneStoreResponseDto(new StoreResponseDto(store), menuList);
    }

    public FavoriteStoreResponseDto saveFavorites(AuthUser authUser, Long storeId) {
        User user = userRepository.findById(authUser.getId())
                .orElseThrow(() -> new UnauthorizedAccessException("사용자를 찾을 수 없습니다."));

        Store store = storeRepository.findById(storeId)
                .orElseThrow(CannotFindStoreIdException::new);

        // 이미 즐겨찾기한 가게인지 확인
        if(favoriteStoreRepository.existsByUserAndStore(user, store)) {
            throw new IllegalArgumentException("이미 이 가게를 즐겨찾기 하였습니다.");
        }
        // FavoriteStore 엔티티 생성
        FavoriteStore favoriteStore = new FavoriteStore(user, store);

        // FavoriteStoreRepository를 사용하여 저장
        favoriteStoreRepository.save(favoriteStore);

        return new FavoriteStoreResponseDto(favoriteStore);
    }


    public Long deleteFavorites(AuthUser authUser, Long storeId) {
        User user = userRepository.findById(authUser.getId())
                .orElseThrow(() -> new UnauthorizedAccessException("사용자를 찾을 수 없습니다."));

        Store store = storeRepository.findById(storeId)
                .orElseThrow(CannotFindStoreIdException::new);

        FavoriteStore favoriteStore = favoriteStoreRepository.findByUserAndStore(user, store)
                .orElseThrow(CannotFindFavoriteStoreException::new);

        favoriteStoreRepository.delete(favoriteStore);
        return favoriteStore.getId();
    }
}
