package com.yurwar.trainingcourse.service;

import com.yurwar.trainingcourse.dto.ActivityDTO;
import com.yurwar.trainingcourse.model.Activity;
import com.yurwar.trainingcourse.model.ActivityStatus;
import com.yurwar.trainingcourse.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                .importance(activityDTO.getImportance())
                .status(ActivityStatus.PENDING)
                .build());
    }

    public Activity findActivityById(long id) {
        return activityRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid activity id: " + id));
    }

    public void deleteActivity(Activity activity) {
        activity.getUsers().forEach((user) -> {
            user.getActivities().remove(activity);
        });
        activityRepository.delete(activity);
    }
}
