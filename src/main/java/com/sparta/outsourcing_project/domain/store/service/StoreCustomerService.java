package com.sparta.outsourcing_project.domain.store.service;

import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.domain.store.dto.response.StoreResponseDto;
import com.sparta.outsourcing_project.domain.store.entity.Store;
import com.sparta.outsourcing_project.domain.store.repository.StoreRepository;
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

    public List<StoreResponseDto> getStores(AuthUser authUser, String storeName) {
        List<Store> stores = storeRepository.findByNameAndIsDeletedFalse(storeName);
        return stores.stream()
                .map(StoreResponseDto::new) // StoreResponseDto의 생성자를 통해 변환
                .collect(Collectors.toList());
    }

}
