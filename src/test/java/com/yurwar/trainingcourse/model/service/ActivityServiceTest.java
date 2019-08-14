package com.yurwar.trainingcourse.model.service;

import com.yurwar.trainingcourse.dto.ActivityDTO;
import com.yurwar.trainingcourse.dto.ActivityDurationDTO;
import com.yurwar.trainingcourse.model.entity.Activity;
import com.yurwar.trainingcourse.model.entity.ActivityImportance;
import com.yurwar.trainingcourse.model.entity.ActivityStatus;
import com.yurwar.trainingcourse.model.entity.User;
import com.yurwar.trainingcourse.model.repository.ActivityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivityServiceTest {
    @Autowired
    private ActivityService activityService;

    @MockBean
    private ActivityRepository activityRepository;

    @Test
    public void markTimeSpent() {
        User user = User.builder()
                .username("testuser")
                .build();

        Activity activity = Activity.builder()
                .name("Test activity")
                .duration(Duration.ZERO)
                .status(ActivityStatus.ACTIVE)
                .users(Set.of(user))
                .build();

        ActivityDurationDTO activityDurationDTO = new ActivityDurationDTO();
        activityDurationDTO.setDays(1);
        activityDurationDTO.setHours(1);
        activityDurationDTO.setMinutes(1);

        Duration expectedDuration = Duration.ZERO
                .plusDays(1)
                .plusHours(1)
                .plusMinutes(1);

        doReturn(Optional.of(activity))
                .when(activityRepository).findById(ArgumentMatchers.anyLong());

        activityService.markTimeSpent(1, user, activityDurationDTO);

        assertEquals(expectedDuration, activity.getDuration());

        verify(activityRepository, times(1)).save(activity);
    }

    @Test
    public void markTimeSpentFail() {
        User user = User.builder()
                .username("testuser")
                .build();

        Activity activityWithMissingUser = Activity.builder()
                .name("Missing user activity")
                .duration(Duration.ZERO)
                .status(ActivityStatus.ACTIVE)
                .users(Collections.emptySet())
                .build();

        Activity completedActivity = Activity.builder()
                .name("Completed activity")
                .duration(Duration.ZERO)
                .status(ActivityStatus.COMPLETED)
                .users(Set.of(user))
                .build();

        Activity pendingActivity = Activity.builder()
                .name("Pending activity")
                .duration(Duration.ZERO)
                .status(ActivityStatus.PENDING)
                .users(Set.of(user))
                .build();

        ActivityDurationDTO activityDurationDTO = new ActivityDurationDTO();
        activityDurationDTO.setDays(1);
        activityDurationDTO.setHours(1);
        activityDurationDTO.setMinutes(1);

        Duration expectedDuration = Duration.ZERO;

        doReturn(Optional.of(activityWithMissingUser))
                .when(activityRepository).findById(1L);
        doReturn(Optional.of(completedActivity))
                .when(activityRepository).findById(2L);
        doReturn(Optional.of(pendingActivity))
                .when(activityRepository).findById(3L);

        activityService.markTimeSpent(1, user, activityDurationDTO);
        activityService.markTimeSpent(2, user, activityDurationDTO);
        activityService.markTimeSpent(3, user, activityDurationDTO);

        assertEquals(expectedDuration, activityWithMissingUser.getDuration());
        assertEquals(expectedDuration, completedActivity.getDuration());
        assertEquals(expectedDuration, pendingActivity.getDuration());

        verify(activityRepository, times(0)).save(activityWithMissingUser);
        verify(activityRepository, times(0)).save(completedActivity);
        verify(activityRepository, times(0)).save(pendingActivity);
    }

    @Test
    public void createActivity() {
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setName("Test activity");
        activityDTO.setDescription("Description");
        activityDTO.setImportance(ActivityImportance.LOW);

        Activity savedActivity = Activity.builder()
                .name(activityDTO.getName())
                .description(activityDTO.getDescription())
                .importance(activityDTO.getImportance())
                .status(ActivityStatus.PENDING)
                .duration(Duration.ZERO)
                .build();

        activityService.createActivity(activityDTO);

        verify(activityRepository, times(1)).save(savedActivity);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findActivityByIdMissing() {
        long missingActivityId = 1;

        doReturn(Optional.empty()).when(activityRepository).findById(missingActivityId);

        activityService.findActivityById(missingActivityId);
    }
}