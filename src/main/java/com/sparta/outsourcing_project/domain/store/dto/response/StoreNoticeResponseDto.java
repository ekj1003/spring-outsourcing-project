package com.sparta.outsourcing_project.domain.store.dto.response;

import com.sparta.outsourcing_project.domain.store.entity.StoreNotice;
import lombok.Getter;

@Getter
public class StoreNoticeResponseDto {

    private final Long store_id;
    private final String notice;

    public StoreNoticeResponseDto(StoreNotice storeNotice) {
        this.store_id = storeNotice.getStore().getId();
        this.notice = storeNotice.getNotice();
    }
}
