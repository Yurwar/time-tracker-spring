package com.yurwar.trainingcourse.model.entity;

/**
 * Actions for activity request
 *
 * @see ActivityRequest
 */
public enum ActivityRequestAction {
    ADD("Add"),
    COMPLETE("Complete");

    private final String simpleName;

    ActivityRequestAction(String simpleName) {
        this.simpleName = simpleName;
    }

    @Override
    public String toString() {
        return simpleName;
    }
}
