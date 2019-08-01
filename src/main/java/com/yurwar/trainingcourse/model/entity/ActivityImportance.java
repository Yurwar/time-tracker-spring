package com.yurwar.trainingcourse.model.entity;

public enum ActivityImportance {
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High"),
    EXTREMELY_HIGH("Extremely high");

    private String simpleName;

    ActivityImportance(String simpleName) {
        this.simpleName = simpleName;
    }

    @Override
    public String toString() {
        return simpleName;
    }
}
