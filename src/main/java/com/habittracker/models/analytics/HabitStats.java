package com.habittracker.models.analytics;

public class HabitStats {
    private String habitName;
    private int completionCount;

    public HabitStats(String habitName, int completionCount) {
        this.habitName = habitName;
        this.completionCount = completionCount;
    }

    public String getHabitName() {
        return habitName;
    }

    public int getCompletionCount() {
        return completionCount;
    }
}
