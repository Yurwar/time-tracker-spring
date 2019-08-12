package com.yurwar.trainingcourse.model.entity;

/**
 * Statuses for activity request
 *
 * @see ActivityRequest
 */
public enum ActivityRequestStatus {
    PENDING("Pending"),
    APPROVED("Approved"),
    REJECTED("Rejected");

    private final String simpleName;

    ActivityRequestStatus(String simpleName) {
        this.simpleName = simpleName;
    }

    @Override
    public String toString() {
        return simpleName;
    }
}
