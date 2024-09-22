package com.sparta.outsourcing_project.domain.store.dto.response;

import com.sparta.outsourcing_project.domain.store.entity.FavoriteStore;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FavoriteStoreResponseDto {
    private Long id;
    private Long user_id;
    private Long store_id;

    public FavoriteStoreResponseDto(FavoriteStore favoriteStore) {
        this.id = favoriteStore.getId();
        this.user_id = favoriteStore.getUser().getId();
        this.store_id = favoriteStore.getStore().getId();
    }
}
