package com.yurwar.trainingcourse.model.entity;

public enum ActivityStatus {
    PENDING("Pending"),
    ACTIVE("Active"),
    COMPLETED("Completed");

    private String simpleName;

    ActivityStatus(String simpleName) {
        this.simpleName = simpleName;
    }

    @Override
    public String toString() {
        return simpleName;
    }
}
