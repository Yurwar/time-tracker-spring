package com.yurwar.trainingcourse.service;

import com.yurwar.trainingcourse.model.Activity;
import com.yurwar.trainingcourse.model.ActivityRequest;
import com.yurwar.trainingcourse.model.User;
import com.yurwar.trainingcourse.repository.ActivityRepository;
import com.yurwar.trainingcourse.repository.ActivityRequestRepository;
import com.yurwar.trainingcourse.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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

    @Transactional
    public void addActivityRequest(Long userId, Long activityId) {
        if (activityRequestRepository.existsByActivityIdAndUserId(activityId, userId)) {
            //TODO Add own exception
            log.info("activity already exists");
        } else {
            User user = userRepository.findById(userId).orElseThrow(() ->
                    new IllegalArgumentException("Invalid user id: " + userId));
            Activity activity = activityRepository.findById(activityId).orElseThrow(() ->
                    new IllegalArgumentException("Invalid activity id: " + activityId));


            ActivityRequest activityRequest = ActivityRequest.builder()
                    .user(user)
                    .activity(activity)
                    .requestDate(LocalDateTime.now())
                    .build();
            user.getActivityRequests().add(activityRequest);
            userRepository.save(user);
            log.info("activity created");
        }
    }

    @Transactional
    public void approveActivityRequest(long activityRequestId) {
        ActivityRequest activityRequest = activityRequestRepository.findById(activityRequestId).orElseThrow(() ->
                new IllegalArgumentException("Invalid request id: " + activityRequestId));

        User user = activityRequest.getUser();
        Activity activity = activityRequest.getActivity();

        activity.setStartTime(LocalDateTime.now());

        user.getActivities().add(activity);
//        activity.getUsers().add(user);

        userRepository.save(user);
        activityRequestRepository.delete(activityRequest);
    }

    public void rejectActivityRequest(long activityRequestId) {
        activityRequestRepository.deleteById(activityRequestId);
    }
}
