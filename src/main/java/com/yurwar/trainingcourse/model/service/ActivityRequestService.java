package com.yurwar.trainingcourse.model.service;

import com.yurwar.trainingcourse.model.entity.*;
import com.yurwar.trainingcourse.model.repository.ActivityRepository;
import com.yurwar.trainingcourse.model.repository.ActivityRequestRepository;
import com.yurwar.trainingcourse.model.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Log4j2
@Service
public class ActivityRequestService {
    private final ActivityRequestRepository activityRequestRepository;
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;

    public ActivityRequestService(ActivityRequestRepository activityRequestRepository, UserRepository userRepository, ActivityRepository activityRepository) {
        this.activityRequestRepository = activityRequestRepository;
        this.userRepository = userRepository;
        this.activityRepository = activityRepository;
    }

    public Page<ActivityRequest> findAllRequestsPageable(Pageable pageable) {
        return activityRequestRepository.findAll(pageable);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void makeAddActivityRequest(long userId, long activityId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("Invalid user id: " + userId));
        Activity activity = activityRepository.findById(activityId).orElseThrow(() ->
                new IllegalArgumentException("Invalid activity id: " + activityId));

        long currentActivityRequestsCount = activityRequestRepository
                .findByActivityIdAndUserId(activityId, userId)
                .stream()
                .filter(activityRequest ->
                        activityRequest.getAction().equals(ActivityRequestAction.ADD) &&
                                activityRequest.getStatus().equals(ActivityRequestStatus.PENDING))
                .count();
        if (currentActivityRequestsCount > 0) {
            log.info("User already send activity request");
            return;
        }

        switch (activity.getStatus()) {
            case COMPLETED:
                log.info("Activity already completed");
                break;
            case ACTIVE: {
                if (activity.getUsers().contains(user)) {
                    log.info("User already in activity");
                    break;
                }
                ActivityRequest activityRequest = ActivityRequest.builder()
                        .user(user)
                        .activity(activity)
                        .action(ActivityRequestAction.ADD)
                        .status(ActivityRequestStatus.PENDING)
                        .requestDate(LocalDateTime.now())
                        .build();
                activityRequestRepository.save(activityRequest);
                break;
            }
            case PENDING: {
                ActivityRequest activityRequest = ActivityRequest.builder()
                        .user(user)
                        .activity(activity)
                        .action(ActivityRequestAction.ADD)
                        .status(ActivityRequestStatus.PENDING)
                        .requestDate(LocalDateTime.now())
                        .build();
                activityRequestRepository.save(activityRequest);
                break;
            }
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void makeCompleteActivityRequest(long userId, long activityId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("Invalid user id: " + userId));
        Activity activity = activityRepository.findById(activityId).orElseThrow(() ->
                new IllegalArgumentException("Invalid activity id: " + activityId));

        long currentActivityRequestsCount = activityRequestRepository
                .findByActivityIdAndUserId(activityId, userId)
                .stream()
                .filter(activityRequest ->
                        activityRequest.getAction().equals(ActivityRequestAction.REMOVE) &&
                                activityRequest.getStatus().equals(ActivityRequestStatus.PENDING))
                .count();
        if (currentActivityRequestsCount > 0) {
            log.info("User already sent activity request");
            return;
        }

        switch (activity.getStatus()) {
            case COMPLETED:
                log.info("Activity already completed");
                break;
            case ACTIVE:
                if (activity.getUsers().contains(user)) {
                    ActivityRequest activityRequest = ActivityRequest.builder()
                            .user(user)
                            .activity(activity)
                            .action(ActivityRequestAction.REMOVE)
                            .status(ActivityRequestStatus.PENDING)
                            .requestDate(LocalDateTime.now())
                            .build();
                    activityRequestRepository.save(activityRequest);
                    return;
                }
                break;
            case PENDING:
                log.info("User can not complete pending activity");
                break;
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void approveAddActivityRequest(long activityRequestId) {
        ActivityRequest activityRequest = findActivityRequestById(activityRequestId);

        Activity activity = activityRequest.getActivity();
        User user = activityRequest.getUser();

        switch (activity.getStatus()) {
            case PENDING: {
                activity.setStartTime(LocalDateTime.now());
                activity.setStatus(ActivityStatus.ACTIVE);
                user.getActivities().add(activity);
                activityRequest.setStatus(ActivityRequestStatus.APPROVED);

                activityRepository.save(activity);
                log.info("Activity request approved");
                break;
            }
            case ACTIVE: {
                user.getActivities().add(activity);
                activityRequest.setStatus(ActivityRequestStatus.APPROVED);

                activityRepository.save(activity);
                log.info("Activity request approved");
                break;
            }
            case COMPLETED: {
                log.info("Can not approve add request for completed activity");
                activityRequest.setStatus(ActivityRequestStatus.REJECTED);
                break;
            }
        }
        activityRequestRepository.save(activityRequest);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void approveCompleteActivityRequest(long activityRequestId) {
        ActivityRequest activityRequest = findActivityRequestById(activityRequestId);

        Activity activity = activityRequest.getActivity();

        switch (activity.getStatus()) {
            case PENDING: {
                log.info("Can not approve complete request for pending activity");
                break;
            }
            case ACTIVE: {
                activity.setEndTime(LocalDateTime.now());
                activity.setStatus(ActivityStatus.COMPLETED);
                activityRequest.setStatus(ActivityRequestStatus.APPROVED);
                activityRepository.save(activity);
                activityRequestRepository.save(activityRequest);
                break;
            }
            case COMPLETED: {
                log.info("Can not approve complete request for completed activity");
                activityRequest.setStatus(ActivityRequestStatus.REJECTED);
                activityRequestRepository.save(activityRequest);
                break;
            }
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void rejectActivityRequest(long activityRequestId) {
        ActivityRequest activityRequest = findActivityRequestById(activityRequestId);
        activityRequest.setStatus(ActivityRequestStatus.REJECTED);
        activityRequestRepository.save(activityRequest);
    }

    public ActivityRequest findActivityRequestById(long activityRequestId) {
        return activityRequestRepository.findById(activityRequestId).orElseThrow(() ->
                new IllegalArgumentException("Invalid activity request id: " + activityRequestId));
    }
}
