package com.yurwar.trainingcourse.model.entity;

public enum ActivityRequestAction {
    ADD("Add"),
    REMOVE("Remove");

    private final String simpleName;

    ActivityRequestAction(String simpleName) {
        this.simpleName = simpleName;
    }

    @Override
    public String toString() {
        return simpleName;
    }
}
