package com.sparta.outsourcing_project.domain.store.service;

import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.domain.exception.CannotFindNoticeIdException;
import com.sparta.outsourcing_project.domain.exception.CannotFindStoreIdException;
import com.sparta.outsourcing_project.domain.exception.MaxStoreLimitException;
import com.sparta.outsourcing_project.domain.exception.UnauthorizedAccessException;
import com.sparta.outsourcing_project.domain.order.entity.Order;
import com.sparta.outsourcing_project.domain.order.repository.OrderRepository;
import com.sparta.outsourcing_project.domain.store.dto.request.StoreDailyDashboardRequestDto;
import com.sparta.outsourcing_project.domain.store.dto.request.StoreNoticeRequestDto;
import com.sparta.outsourcing_project.domain.store.dto.request.StorePatchRequestDto;
import com.sparta.outsourcing_project.domain.store.dto.request.StoreRequestDto;
import com.sparta.outsourcing_project.domain.store.dto.response.StoreDashboardResponseDto;
import com.sparta.outsourcing_project.domain.store.dto.request.StoreMonthlyDashboardRequestDto;
import com.sparta.outsourcing_project.domain.store.dto.response.StoreNoticeResponseDto;
import com.sparta.outsourcing_project.domain.store.dto.response.StoreResponseDto;
import com.sparta.outsourcing_project.domain.store.entity.Store;
import com.sparta.outsourcing_project.domain.store.entity.StoreNotice;
import com.sparta.outsourcing_project.domain.store.repository.StoreNoticeRepository;
import com.sparta.outsourcing_project.domain.store.repository.StoreRepository;
import com.sparta.outsourcing_project.domain.user.dto.response.UserResponseDto;
import com.sparta.outsourcing_project.domain.user.entity.User;
import com.sparta.outsourcing_project.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class StoreOwnerService {
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final StoreNoticeRepository storeNoticeRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public StoreResponseDto saveStore(AuthUser authUser, StoreRequestDto storeRequestDto) {
        // userRepository에서 직접 user entity 가져오기
        User user = userRepository.findById(authUser.getId())
                .orElseThrow(() -> new UnauthorizedAccessException("사용자를 찾을 수 없습니다."));

        // 찾은 user로 storeRepository에서 user를 column으로 가진 store 리스트로 가져오기
        List<Store> stores = storeRepository.findByUser(user);


        // 사장님이 운영할 수 있는 가게 수가 3개 초과인지 확인
        if (stores.size() >= 3) {
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
        userRepository.save(user);

        return new StoreResponseDto(
                savedStore.getId(),
                savedStore.getName(),
                savedStore.getOpenAt(),
                savedStore.getCloseAt(),
                savedStore.getMinPrice(),
                new UserResponseDto(user.getId(), user.getEmail())
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

    public StoreNoticeResponseDto saveNotice(AuthUser authUser, Long storeId, StoreNoticeRequestDto storeNoticeRequestDto) {
        User user = userRepository.findById(authUser.getId())
                .orElseThrow(() -> new UnauthorizedAccessException("사용자를 찾을 수 없습니다."));

        Store store = storeRepository.findById(storeId)
                .orElseThrow(CannotFindStoreIdException::new);

        if(!store.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException("이 가게의 공지를 생성할 권한이 없습니다.");
        }

        StoreNotice storeNotice = new StoreNotice(store, storeNoticeRequestDto.getNotice());

        storeNoticeRepository.save(storeNotice);

        return new StoreNoticeResponseDto(storeNotice);


    }

    public Long deleteNotice(AuthUser authUser, Long storeId, Long noticeId) {

        User user = userRepository.findById(authUser.getId())
                .orElseThrow(() -> new UnauthorizedAccessException("사용자를 찾을 수 없습니다."));

        Store store = storeRepository.findById(storeId)
                .orElseThrow(CannotFindStoreIdException::new);

        if(!store.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException("이 가게의 공지를 삭제할 권한이 없습니다.");
        }

        StoreNotice storeNotice = storeNoticeRepository.findByIdAndStore(noticeId, store)
                .orElseThrow(CannotFindNoticeIdException::new);

        storeNoticeRepository.delete(storeNotice);

        return storeNotice.getId();
    }

    public StoreDashboardResponseDto getDailyDashboard(AuthUser authUser, StoreDailyDashboardRequestDto storeDailyDashboardRequestDto) {
        User user = userRepository.findById(authUser.getId())
                .orElseThrow(() -> new UnauthorizedAccessException("사용자를 찾을 수 없습니다."));

        Store store = storeRepository.findById(storeDailyDashboardRequestDto.getStoreId())
                .orElseThrow(CannotFindStoreIdException::new);

        if(!store.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException("이 가게의 일일 대시보드를 조회할 권한이 없습니다.");
        }

        // 해당 날짜의 시작 시간과 끝 시간 설정
        LocalDate day = storeDailyDashboardRequestDto.getDay();
        LocalDateTime startOfDay = day.atStartOfDay();
        LocalDateTime endOfDay = day.atTime(23, 59, 59);

        // 해당 날짜의 주문 리스트
        List<Order> orderList = orderRepository.findAllByStoreIdAndCreatedAtBetween(store.getId(), startOfDay, endOfDay);

        // 해당 날짜의 주문을 한 고객 유저 리스트
        List<User> userList = orderList.stream()
                .map(Order::getUser) // 각 Order에서 User를 추출
                .distinct() // 중복된 유저 제외
                .collect(Collectors.toList());

        // 일별 고객 유저 수
        long dailyUserCount = userList.size();

        // 일별 매출 금액
        long totalSales = orderList.stream()
                .mapToLong(order -> order.getTotalPrice())
                .sum();

        return new StoreDashboardResponseDto(store.getId(), dailyUserCount, totalSales);

    }

    public StoreDashboardResponseDto getMonthlyDashboard(AuthUser authUser, StoreMonthlyDashboardRequestDto storeMonthlyDashboardRequestDto) {
        User user = userRepository.findById(authUser.getId())
                .orElseThrow(() -> new UnauthorizedAccessException("사용자를 찾을 수 없습니다."));

        Store store = storeRepository.findById(storeMonthlyDashboardRequestDto.getStoreId())
                .orElseThrow(CannotFindStoreIdException::new);

        if(!store.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException("이 가게의 일일 대시보드를 조회할 권한이 없습니다.");
        }

        // 해당 월의 시작일과 마지막일 설정 (월은 1 ~ 12 기준)
        int year = storeMonthlyDashboardRequestDto.getYear();
        int month = storeMonthlyDashboardRequestDto.getMonth();
        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());

        // storeId와 해당 월의 주문 목록을 가져옴
        List<Order> orderList = orderRepository.findAllByStoreIdAndCreatedAtBetween(
                store.getId(),
                startOfMonth.atStartOfDay(),
                endOfMonth.atTime(23, 59, 59)
        );

        // 해당 월에 주문을 한 고객 유저 리스트
        List<User> userList = orderList.stream()
                .map(Order::getUser) // 각 Order에서 User를 추출
                .distinct() // 중복된 유저 제외
                .collect(Collectors.toList());

        // 월별 고객 유저 수
        long monthlyUserCount = userList.size();


        // 월별 매출 금액
        long totalSales = orderList.stream()
                .mapToLong(order -> order.getTotalPrice())
                .sum();

        return new StoreDashboardResponseDto(store.getId(), monthlyUserCount, totalSales);

    }
}

