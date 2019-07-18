package com.yurwar.trainingcourse.service;

import com.yurwar.trainingcourse.dto.ActivityDTO;
import com.yurwar.trainingcourse.dto.ActivityDurationDTO;
import com.yurwar.trainingcourse.model.Activity;
import com.yurwar.trainingcourse.model.ActivityStatus;
import com.yurwar.trainingcourse.model.User;
import com.yurwar.trainingcourse.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class ActivityService {
    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }


    public List<Activity> findAllActivities() {
        return activityRepository.findAll();
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
        User user = activity.getUser();
        if (user != null) {
            user.getActivities().remove(activity);
        }
        activityRepository.delete(activity);
    }

    //TODO add user check
    public void markTimeSpent(long activityId, ActivityDurationDTO durationDTO) {
        Activity activity = findActivityById(activityId);
        Duration duration = activity.getDuration();

        duration = duration.plusDays(durationDTO.getDays());
        duration = duration.plusHours(durationDTO.getHours());
        duration = duration.plusMinutes(durationDTO.getMinutes());

        activity.setDuration(duration);

        activityRepository.save(activity);
    }
}
