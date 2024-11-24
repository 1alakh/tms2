package com.tsm_project.service;

import com.tsm_project.entity.Task;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    Task createNewTask(Task task);
    List<Task> getAllTasks();
    Task getTaskById(String id);
}
