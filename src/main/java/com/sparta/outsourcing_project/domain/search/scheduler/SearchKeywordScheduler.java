package com.sparta.outsourcing_project.domain.search.scheduler;

import com.sparta.outsourcing_project.domain.search.repository.SearchKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class SearchKeywordScheduler {

    private final SearchKeywordRepository searchKeywordRepository;

    // 매 시간마다 인기 검색어를 초기화하는 작업 (cron 표현식으로 1시간마다 실행)
    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void resetAllSearchCounts() {
        searchKeywordRepository.resetAllSearchCounts(LocalDateTime.now());
    }
}