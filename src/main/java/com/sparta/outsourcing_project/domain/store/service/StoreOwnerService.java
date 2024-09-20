package com.sparta.outsourcing_project.domain.store.service;

import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.domain.exception.CannotFindStoreIdException;
import com.sparta.outsourcing_project.domain.exception.MaxStoreLimitException;
import com.sparta.outsourcing_project.domain.exception.UnauthorizedAccessException;
import com.sparta.outsourcing_project.domain.store.dto.request.StorePatchRequestDto;
import com.sparta.outsourcing_project.domain.store.dto.request.StoreRequestDto;
import com.sparta.outsourcing_project.domain.store.dto.response.StoreResponseDto;
import com.sparta.outsourcing_project.domain.store.entity.Store;
import com.sparta.outsourcing_project.domain.store.repository.StoreRepository;
import com.sparta.outsourcing_project.domain.user.dto.response.UserResponse;
import com.sparta.outsourcing_project.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreOwnerService {
    private final StoreRepository storeRepository;

    @Transactional
    public StoreResponseDto saveStore(AuthUser authUser, StoreRequestDto storeRequestDto) {
        User user = User.fromAuthUser(authUser);

        // 사장님이 운영할 수 있는 가게 수가 3개 초과인지 확인
        if (user.getStore_number() >= 3) {
            throw new MaxStoreLimitException("사장님은 가게를 최대 3개까지만 운영할 수 있습니다.");
        }

        Store newStore = new Store(
                storeRequestDto.getName(),
                storeRequestDto.getOpenAt(),
                storeRequestDto.getCloseAt(),
                storeRequestDto.getMinPrice(),
                user
        );

        Store savedStore = storeRepository.save(newStore);

        // 가게 저장 후 가게 수 증가
        user.incrementStoreNumber();

        return new StoreResponseDto(
                savedStore.getId(),
                savedStore.getName(),
                savedStore.getOpenAt(),
                savedStore.getCloseAt(),
                savedStore.getMinPrice(),
                savedStore.getIsDeleted(),
                new UserResponse(user.getId(), user.getEmail())
        );
    }

    public StoreResponseDto patchStore(Long storeId, AuthUser authUser, StorePatchRequestDto storePatchRequestDto) {
        Store store = storeRepository.findById(storeId).orElseThrow(CannotFindStoreIdException::new);
        User user = User.fromAuthUser(authUser);

        // 사용자 ID 비교
        if (!store.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException("이 가게를 수정할 권한이 없습니다."); // 권한이 없을 경우 예외 처리
        }

        if (storePatchRequestDto.getIsDeleted()) {
            store.delete(); // soft delete 처리
            user.decrementStoreNumber(); // 가게 폐업(삭제) 후 가게 수 감소
            return null;
        }

        Store patchStore = store.patchStore(storePatchRequestDto);  // 가게 정보 수정

        return new StoreResponseDto(patchStore);
    }

}

