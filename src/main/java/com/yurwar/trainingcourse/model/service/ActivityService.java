package com.yurwar.trainingcourse.model.service;

import com.yurwar.trainingcourse.dto.ActivityDTO;
import com.yurwar.trainingcourse.dto.ActivityDurationDTO;
import com.yurwar.trainingcourse.model.entity.Activity;
import com.yurwar.trainingcourse.model.entity.ActivityStatus;
import com.yurwar.trainingcourse.model.entity.User;
import com.yurwar.trainingcourse.model.repository.ActivityRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Set;

@Service
@Log4j2
public class ActivityService {
    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public Page<Activity> findAllActivitiesPageable(Pageable pageable) {
        return activityRepository.findAll(pageable);
    }

    public void addNewActivity(ActivityDTO activityDTO) {
        activityRepository.save(Activity.builder()
                .name(activityDTO.getName())
                .description(activityDTO.getDescription())
                .importance(activityDTO.getImportance())
                .duration(Duration.ZERO)
                .status(ActivityStatus.PENDING)
                .build());
    }

    public Activity findActivityById(long activityId) {
        return activityRepository.findById(activityId).orElseThrow(() ->
                new IllegalArgumentException("Invalid activity id: " + activityId));
    }

    public void deleteActivity(Activity activity) {
        Set<User> users = activity.getUsers();
        for (User user : users) {
            user.getActivities().remove(activity);
        }
        activityRepository.delete(activity);
    }

    public void markTimeSpent(long activityId, User user, ActivityDurationDTO durationDTO) {
        Activity activity = findActivityById(activityId);
        log.info("Try to mark time spent to activity: " + activity + " with duration: " + durationDTO);

        if (activity.getStatus().equals(ActivityStatus.ACTIVE) && activity.getUsers().contains(user)) {
            log.info("Marking time spent");
            Duration duration = activity.getDuration();

            duration = duration
                    .plusDays(durationDTO.getDays())
                    .plusHours(durationDTO.getHours())
                    .plusMinutes(durationDTO.getMinutes());

            activity.setDuration(duration);

            activityRepository.save(activity);
        }
    }
}
