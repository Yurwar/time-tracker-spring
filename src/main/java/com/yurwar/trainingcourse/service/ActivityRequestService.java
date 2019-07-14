package com.yurwar.trainingcourse.service;

import com.yurwar.trainingcourse.model.*;
import com.yurwar.trainingcourse.repository.ActivityRepository;
import com.yurwar.trainingcourse.repository.ActivityRequestRepository;
import com.yurwar.trainingcourse.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class ActivityRequestService {
    private final ActivityRequestRepository activityRequestRepository;
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityRequestService(ActivityRequestRepository activityRequestRepository, UserRepository userRepository, ActivityRepository activityRepository) {
        this.activityRequestRepository = activityRequestRepository;
        this.userRepository = userRepository;
        this.activityRepository = activityRepository;
    }

    public List<ActivityRequest> findAllRequests() {
        return activityRequestRepository.findAll();
    }

    public void addActivityRequest(Long userId, Long activityId, String action) {
        Optional<ActivityRequest> activityRequestOpt = activityRequestRepository
                .findByActivityIdAndUserId(activityId, userId);

        //TODO Fix adding new activity requests
        if (activityRequestOpt.isPresent()
                && activityRequestOpt.get().getStatus() == ActivityRequestStatus.APPROVED
                && activityRequestOpt.get().getAction() == ActivityRequestAction.ADD) {
            //TODO Add exception
            log.info("Activity request already exists");
        } else {
            Activity activity = activityRepository.findById(activityId).orElseThrow(() ->
                    new IllegalArgumentException("Invalid activity id: " + activityId));
            User user = userRepository.findById(userId).orElseThrow(() ->
                    new IllegalArgumentException("Invalid user id: " + userId));

            ActivityRequest activityRequest = ActivityRequest.builder()
                    .user(user)
                    .activity(activity)
                    .requestDate(LocalDateTime.now())
                    .action(ActivityRequestAction.valueOf(action))
                    .build();
            user.getActivityRequests().add(activityRequest);
            userRepository.save(user);
            log.info("activity created");
        }
    }

    @Transactional
    public void approveAddActivityRequest(long activityRequestId) {
        ActivityRequest activityRequest = findActivityRequestById(activityRequestId);

        User user = activityRequest.getUser();
        Activity activity = activityRequest.getActivity();

        activity.setStartTime(LocalDateTime.now());
        activity.setStatus(ActivityStatus.ACTIVE);
        activity.setUser(user);

        activityRepository.save(activity);

        activityRequest.setStatus(ActivityRequestStatus.APPROVED);
        activityRequestRepository.save(activityRequest);
    }

    public void approveRemoveActivityRequest(long activityRequestId) {
        ActivityRequest activityRequest = findActivityRequestById(activityRequestId);

        User user = activityRequest.getUser();
        Activity activity = activityRequest.getActivity();

        user.getActivities().remove(activity);
        activity.setEndTime(LocalDateTime.now());
        activity.setStatus(ActivityStatus.COMPLETED);

        activityRepository.save(activity);
        activityRequest.setStatus(ActivityRequestStatus.APPROVED);
        activityRequestRepository.save(activityRequest);
    }

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
