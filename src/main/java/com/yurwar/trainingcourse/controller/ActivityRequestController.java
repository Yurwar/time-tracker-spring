package com.yurwar.trainingcourse.controller;

import com.yurwar.trainingcourse.model.entity.ActivityRequest;
import com.yurwar.trainingcourse.model.entity.ActivityRequestStatus;
import com.yurwar.trainingcourse.model.entity.User;
import com.yurwar.trainingcourse.model.service.ActivityRequestService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Controller that react on activity requests related requests
 *
 * @see com.yurwar.trainingcourse.model.entity.Activity
 */
@Controller
@Log4j2
public class ActivityRequestController {
    private final ActivityRequestService activityRequestService;

    public ActivityRequestController(ActivityRequestService activityRequestService) {
        this.activityRequestService = activityRequestService;
    }

    @GetMapping("/activities/request")
    public String getActivityRequests(Model model,
                                      @PageableDefault(sort = {"id"},
                                              direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("activityRequests", activityRequestService.findAllRequestsPageable(pageable));
        return "activity-requests";
    }

    @GetMapping("/activities/request/add/{id}")
    public String makeAddActivityRequest(@AuthenticationPrincipal User user,
                                         @PathVariable("id") long activityId) {
        activityRequestService.makeAddActivityRequest(user.getId(), activityId);
        return "redirect:/activities";
    }

    @GetMapping("/activities/request/complete/{id}")
    public String makeCompleteActivityRequest(@AuthenticationPrincipal User user,
                                              @PathVariable("id") long activityId) {
        activityRequestService.makeCompleteActivityRequest(user.getId(), activityId);
        return "redirect:/activities";
    }

    @GetMapping("/activities/request/approve/{id}")
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
            case COMPLETE:
                activityRequestService.approveCompleteActivityRequest(activityRequestId);
                break;
        }

        return "redirect:/activities/request";
    }

    @GetMapping("/activities/request/reject/{id}")
    public String rejectActivityRequest(@PathVariable("id") long activityRequestId) {
        ActivityRequest activityRequest = activityRequestService.findActivityRequestById(activityRequestId);

        if (!activityRequest.getStatus().equals(ActivityRequestStatus.PENDING)) {
            return "redirect:/activities/request";
        }

        activityRequestService.rejectActivityRequest(activityRequestId);

        return "redirect:/activities/request";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException e) {
        log.error(e.getMessage());
        return "error/404";
    }
}
