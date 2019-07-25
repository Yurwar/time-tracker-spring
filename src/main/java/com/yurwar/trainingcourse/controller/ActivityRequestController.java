package com.yurwar.trainingcourse.controller;

import com.yurwar.trainingcourse.entity.ActivityRequest;
import com.yurwar.trainingcourse.entity.ActivityRequestAction;
import com.yurwar.trainingcourse.entity.User;
import com.yurwar.trainingcourse.service.ActivityRequestService;
import com.yurwar.trainingcourse.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ActivityRequestController {
    private final ActivityRequestService activityRequestService;
    private final UserService userService;
    private final ActivityController activityController;

    public ActivityRequestController(ActivityRequestService activityRequestService, UserService userService, ActivityController activityController) {
        this.activityRequestService = activityRequestService;
        this.userService = userService;
        this.activityController = activityController;
    }

    @GetMapping("/activities/request")
    public String getActivityRequests(Model model) {
        model.addAttribute("activityRequests", activityRequestService.findAllRequests());
        return "activity-requests";
    }


    @PostMapping("/activities/request/{id}")
    public String makeActivityRequest(@AuthenticationPrincipal User user,
                                      @PathVariable("id") long activityId,
                                      @RequestParam String action,
                                      Model model) {
        activityRequestService.addActivityRequest(user.getId(), activityId, action);
        return activityController.getActivitiesPage(model);
    }

    @PostMapping("/activities/request/approve/{id}")
    public String approveActivityRequest(@PathVariable("id") long activityRequestId,
                                         Model model) {
        ActivityRequest activityRequest = activityRequestService
                .findActivityRequestById(activityRequestId);

        if (activityRequest.getStatus() != null) {
            return getActivityRequests(model);
        }

        ActivityRequestAction action = activityRequest.getAction();
        switch (action) {
            case ADD:
                activityRequestService.approveAddActivityRequest(activityRequestId);
                break;
            case REMOVE:
                activityRequestService.approveRemoveActivityRequest(activityRequestId);
                break;
        }

        return getActivityRequests(model);
    }

    @PostMapping("/activities/request/reject/{id}")
    public String rejectActivityRequest(@PathVariable("id") long activityRequestId,
                                        Model model) {
        if (activityRequestService.findActivityRequestById(activityRequestId).getStatus() != null) {
            return getActivityRequests(model);
        }

        activityRequestService.rejectActivityRequest(activityRequestId);
        return getActivityRequests(model);
    }
}
