package com.habittracker.services;

import com.habittracker.models.tasks.Task;
import com.habittracker.models.user.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskService {

    public void addTask(String title, String notes) {
        User currentUser = UserService.getCurrentUser();
        if (currentUser == null)
            return;

        List<Task> tasks = DataStore.loadTasks(currentUser.getUsername());
        // Mark incomplete tasks from previous days as struck off
        LocalDate today = LocalDate.now();
        for (Task task : tasks) {
            if (!task.isCompleted() && !task.getDateAssigned().equals(today)) {
                task.setStruckOff(true);
            }
        }
        
        Task newTask = new Task(title, notes);
        newTask.setDateAssigned(today);
        tasks.add(newTask);
        DataStore.saveTasks(currentUser.getUsername(), tasks);
    }

    public List<Task> getTodayTasks() {
        User currentUser = UserService.getCurrentUser();
        if (currentUser == null)
            return new ArrayList<>();
        
        List<Task> allTasks = DataStore.loadTasks(currentUser.getUsername());
        LocalDate today = LocalDate.now();
        
        // Process tasks: mark incomplete tasks from previous days as struck off
        boolean needsSave = false;
        for (Task task : allTasks) {
            if (!task.getDateAssigned().equals(today) && !task.isCompleted() && !task.isStruckOff()) {
                task.setStruckOff(true);
                needsSave = true;
            }
        }
        
        if (needsSave) {
            DataStore.saveTasks(currentUser.getUsername(), allTasks);
        }
        
        // Return only today's tasks
        return allTasks.stream()
            .filter(t -> t.getDateAssigned().equals(today))
            .collect(Collectors.toList());
    }

    public List<Task> getAllTasks() {
        User currentUser = UserService.getCurrentUser();
        if (currentUser == null)
            return new ArrayList<>();
        return DataStore.loadTasks(currentUser.getUsername());
    }

    public void saveState(List<Task> tasks) {
        User currentUser = UserService.getCurrentUser();
        if (currentUser == null)
            return;
        DataStore.saveTasks(currentUser.getUsername(), tasks);
    }

    public TaskCompletionStats getCompletionStats() {
        User currentUser = UserService.getCurrentUser();
        if (currentUser == null)
            return new TaskCompletionStats(0, 0);
        
        List<Task> allTasks = DataStore.loadTasks(currentUser.getUsername());
        LocalDate today = LocalDate.now();
        
        List<Task> todayTasks = allTasks.stream()
            .filter(t -> t.getDateAssigned().equals(today))
            .collect(Collectors.toList());
        
        int completed = (int) todayTasks.stream().filter(Task::isCompleted).count();
        int leftOut = (int) todayTasks.stream().filter(t -> !t.isCompleted()).count();
        
        return new TaskCompletionStats(completed, leftOut);
    }

    public static class TaskCompletionStats {
        private int completed;
        private int leftOut;

        public TaskCompletionStats(int completed, int leftOut) {
            this.completed = completed;
            this.leftOut = leftOut;
        }

        public int getCompleted() {
            return completed;
        }

        public int getLeftOut() {
            return leftOut;
        }
    }
}
