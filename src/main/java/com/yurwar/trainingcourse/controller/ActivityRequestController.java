package com.yurwar.trainingcourse.controller;

import com.yurwar.trainingcourse.entity.ActivityRequest;
import com.yurwar.trainingcourse.entity.ActivityRequestAction;
import com.yurwar.trainingcourse.entity.ActivityRequestStatus;
import com.yurwar.trainingcourse.entity.User;
import com.yurwar.trainingcourse.service.ActivityRequestService;
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

    public ActivityRequestController(ActivityRequestService activityRequestService) {
        this.activityRequestService = activityRequestService;
    }

    @GetMapping("/activities/request")
    public String getActivityRequests(Model model) {
        model.addAttribute("activityRequests", activityRequestService.findAllRequests());
        return "activity-requests";
    }

    @PostMapping("/activities/request/add/{id}")
    public String makeAddActivityRequest(@AuthenticationPrincipal User user,
                                         @PathVariable("id") long activityId) {
        activityRequestService.makeAddActivityRequest(user.getId(), activityId);
        return "redirect:/activities";
    }

    @PostMapping("/activities/request/complete/{id}")
    public String makeCompleteActivityRequest(@AuthenticationPrincipal User user,
                                              @PathVariable("id") long activityId) {
        activityRequestService.makeCompleteActivityRequest(user.getId(), activityId);
        return "redirect:/activities";
    }

    @PostMapping("/activities/request/approve/{id}")
    public String approveActivityRequest(@PathVariable("id") long activityRequestId) {
        ActivityRequest activityRequest = activityRequestService
                .findActivityRequestById(activityRequestId);

        if (!activityRequest.getStatus().equals(ActivityRequestStatus.PENDING)) {
            return "redirect:/activities/request";
        }

        switch (activityRequest.getAction()) {
            case ADD:
                activityRequestService.approveAddActivityRequest(activityRequestId);
                break;
            case REMOVE:
                activityRequestService.approveCompleteActivityRequest(activityRequestId);
                break;
        }

        return "redirect:/activities/request";
    }

    @PostMapping("/activities/request/reject/{id}")
    public String rejectActivityRequest(@PathVariable("id") long activityRequestId) {
        ActivityRequest activityRequest = activityRequestService.findActivityRequestById(activityRequestId);

        if (!activityRequest.getStatus().equals(ActivityRequestStatus.PENDING)) {
            return "redirect:/activities/request";
        }

        activityRequestService.rejectActivityRequest(activityRequestId);
        return "redirect:/activities/request";
    }
}
