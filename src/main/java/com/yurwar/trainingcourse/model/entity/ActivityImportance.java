package com.yurwar.trainingcourse.model.entity;

/**
 * Importance levels for activity entity
 *
 * @see Activity
 */
public enum ActivityImportance {
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High"),
    EXTREMELY_HIGH("Extremely high");

    private final String simpleName;

    ActivityImportance(String simpleName) {
        this.simpleName = simpleName;
    }

    @Override
    public String toString() {
        return simpleName;
    }
}
